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
    @Autowired
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

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String,Object> requestData,HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        String oldPassword = request.getAttribute("password").toString();
        String currentPassword = requestData.get("currentPassword").toString();
        String confirmPassword =requestData.get("confirmPassword").toString();
        String newPassword = requestData.get("newPassword").toString();
        if(!newPassword.equals(confirmPassword)){
            return Result.error("请重新确认密码，新密码与确认密码不一致");
        }else{
            try{
                if (!oldPassword.equals(currentPassword)){
                    return Result.error("请重新输入密码");
                }
                if(username.startsWith("T")){
                    teacherService.updateTeacherPassword(newPassword,username);
                } else if (username.startsWith("S")) {
                    studentService.updateStudentPassword(newPassword,username);
                }else{
                    assistantService.updateAssistantPassword(newPassword,username);
                }
            }catch (Exception e){
                return Result.error("密码修改失败，请重试");
            }

        }

        return Result.success("密码修改成功");
    }

    //获取用户头像路径
    @GetMapping("/getPicture")
    public Result getPicture(HttpServletRequest request){
        String userNo = request.getAttribute("username").toString();
        if (userNo.startsWith("S")){
            Student s = studentService.selectByStudentNo(userNo);
            String url = s.getPicturePath();
            return Result.success(url);
        } else if (userNo.startsWith("T")) {
            Teacher t = teacherService.selectByTeacherNo(userNo);
            String url = t.getPicturePath();
            return Result.success(url);
        }else {
            Assistant a = assistantService.selectByAssistantNo(userNo);
            String url = a.getPicturePath();
            return Result.success(url);
        }

    }

    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam MultipartFile file,HttpServletRequest request) { // 用户类型参数

//        @RequestParam("file") MultipartFile file,
//        @RequestParam("userNo") String userNo, // 假设每个用户都有唯一编号
//        @RequestParam("userType") String userType)

        String userNo = request.getAttribute("username").toString();
        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        try {
            // 调用 uploadFile 方法上传文件并获取文件名
            String savedFileName = courseResourceController.uploadFile(file);
            // 生成文件的 URL
            String url = "http://" + ip + ":" + port + "/file/download/" + savedFileName;

            // 根据用户类型更新头像路径
            if (userNo.startsWith("T")) {
                teacherService.updateTeacherPicturePath(url, userNo);
            } else if (userNo.startsWith("S")) {
                studentService.updateStudentPicturePath(url,userNo);
            } else if (userNo.startsWith("A")) {
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
        String username = request.getAttribute("username").toString();
        if (username.startsWith("S")){
            Student s = studentService.selectByStudentNo(username);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("userNo",s.getStudentNo());
            responseData.put("userName",s.getStudentName());
            responseData.put("email",s.getStudentEmail());
            responseData.put("identity","学生");
            String classNo = s.getClassNo();
            String classDept = classService.selectByClassNo(classNo).getClassDepartment();
            responseData.put("dept",classDept);
            return Result.success(responseData);
        } else if (username.startsWith("T")) {
            Teacher t = teacherService.selectByTeacherNo(username);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("userNo",t.getTeacherNo());
            responseData.put("userName",t.getTeacherName());
            responseData.put("email",t.getTeacherEmail());
            responseData.put("identity","教师");
            responseData.put("dept",t.getDepartment());
            return Result.success(responseData);
        }else {
            Assistant a = assistantService.selectByAssistantNo(username);
            Map<String,Object> responseData = new HashMap<>();
            responseData.put("userNo",a.getAssistantNo());
            responseData.put("userName",studentService.selectByStudentNo(a.getAssistantNo()).getStudentName());
            responseData.put("email",studentService.selectByStudentNo(a.getAssistantNo()).getStudentEmail());
            responseData.put("identity","助教");
            return Result.success();
        }


    }
}