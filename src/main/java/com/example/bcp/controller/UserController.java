package com.example.bcp.controller;

import com.example.bcp.entity.Assistant;
import com.example.bcp.entity.Student;
import com.example.bcp.entity.Teacher;
import com.example.bcp.service.AssistantService;
import com.example.bcp.service.ClassService;
import com.example.bcp.service.StudentService;
import com.example.bcp.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.example.bcp.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private CourseResourceController courseResourceController;

    @Autowired
    private StudentService studentService;
    @Autowired
    private AssistantService assistantService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClassService classService;

    @Value("${ip:localhost}")
    String ip;
    @Value("${server.port}")
    String port;

    public boolean updatePassword(String userType, String currentPassword, String newPassword, String userNo) {
        // 验证密码
        boolean isValid = verifyCurrentPassword(userType, currentPassword, userNo);
        if (!isValid) {
            return false;
        }

        switch (userType) {
            case "student":
                studentService.updateStudentPassword(newPassword, userNo);
                break;
            case "assistant":
                assistantService.updateAssistantPassword(newPassword, userNo);
                break;
            case "teacher":
                teacherService.updateTeacherPassword(newPassword, userNo);
                break;
            default:
                return false;
        }
        return true;
    }

    private boolean verifyCurrentPassword(String userType, String currentPassword, String userNo) {
        switch (userType) {
            case "student":
                Student student = studentService.selectByStudentNo(userNo);
                if (student == null) {
                    return false;
                }
                String studentPassword = student.getPassword(); // 获取学生的密码
                if (!studentPassword.equals(currentPassword)) {
                    return false;
                }
                break;
            case "assistant":
                Assistant assistant = assistantService.selectByAssistantNo(userNo);
                if (assistant == null) {
                    return false;
                }
                String assistantPassword = assistant.getPassword(); // 获取学生的密码
                if (!assistantPassword.equals(currentPassword)) {
                    return false;
                }
                break;
            case "teacher":
                Teacher teacher = teacherService.selectByTeacherNo(userNo);
                if (teacher == null) {
                    return false;
                }
                String teacherPassword = teacher.getPassword(); // 获取学生的密码
                if (!teacherPassword.equals(currentPassword)) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }


    @PostMapping("/{userType}/updatePassword")
    public Result updatePassword(@PathVariable String userType,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 @RequestParam String userNo) {
        // 确认新密码与确认密码一致
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("新密码与确认密码不一致");
            return Result.error("新密码与确认密码不一致");
        }

        // 调用UserService更新密码
        boolean result = updatePassword(userType, currentPassword, newPassword, userNo);
        if (result) {
            System.out.println("密码修改成功");
            return Result.success();
        } else {
            System.out.println("当前密码错误或用户类型无效");
            return Result.error("当前密码错误或用户类型无效");
        }
    }

    @PostMapping("/updateAvatar")
    public Result updateAvatar(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userNo") String userNo, // 假设每个用户都有唯一编号
            @RequestParam("userType") String userType) { // 用户类型参数

        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        try {
            // 调用 uploadFile 方法上传文件并获取文件名
            String savedFileName = courseResourceController.uploadFile(file);
            // 生成文件的 URL
            String url = "http://" + ip + ":" + port + "/file/download/" + savedFileName;

            // 根据用户类型更新头像路径
            if ("teacher".equalsIgnoreCase(userType)) {
                teacherService.updateTeacherPicturePath(url, userNo);
            } else if ("student".equalsIgnoreCase(userType)) {
                studentService.updateStudentPicturePath(url,userNo);
            } else if ("assistant".equalsIgnoreCase(userType)) {
                assistantService.updateAssistantPicturePath(url,userNo);
            } else {
                return Result.error("用户类型不合法");
            }

            return Result.success(url);  // 返回成功信息和头像 URL
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("头像上传失败");
        }
    }

    @GetMapping(value = "/userInfo")
    public Result getUserInfo(HttpServletRequest request){
        String studentNo = request.getAttribute("username").toString();
        Student s = studentService.selectByStudentNo(studentNo);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("studentNo",s.getStudentNo());
        responseData.put("studentName",s.getStudentName());
        responseData.put("email",s.getStudentEmail());
        String classNo = s.getClassNo();
        String classDept = classService.selectByClassNo(classNo).getClassDepartment();
        responseData.put("dept",classDept);
        return Result.success(responseData);
    }
}