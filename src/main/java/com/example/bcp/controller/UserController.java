package com.example.bcp.controller;

import com.example.bcp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.bcp.entity.Result;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

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
        boolean result = userService.updatePassword(userType, currentPassword, newPassword, userNo);
        if (result) {
            System.out.println("密码修改成功");
            return Result.success();
        } else {
            System.out.println("当前密码错误或用户类型无效");
            return Result.error("当前密码错误或用户类型无效");
        }
    }
}
