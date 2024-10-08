package com.example.bcp.service;

import com.example.bcp.entity.Assistant;
import com.example.bcp.entity.Student;
import com.example.bcp.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AssistantService assistantService;
    @Autowired
    private TeacherService teacherService;

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
}
