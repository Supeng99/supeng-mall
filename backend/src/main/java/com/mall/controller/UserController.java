package com.mall.controller;

import com.mall.common.Result;
import com.mall.entity.User;
import com.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<User> register(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userService.register(username, password);
            user.setPassword(null);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(400, e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userService.login(username, password);
            String token = userService.generateToken(user.getId(), user.getUsername());
            user.setPassword(null);
            
            Map<String, Object> data = new HashMap<>();
            data.put("user", user);
            data.put("token", token);
            
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(401, e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return Result.error(401, "未登录");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            String username = userService.extractUsername(token);
            User user = userService.getUserByUsername(username);
            user.setPassword(null);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(401, "无效的token");
        }
    }

    @PutMapping("/update")
    public Result<User> updateUser(@RequestHeader(value = "Authorization", required = false) String token,
                                   @RequestParam(required = false) String nickname,
                                   @RequestParam(required = false) String email,
                                   @RequestParam(required = false) String phone) {
        if (token == null || token.isEmpty()) {
            return Result.error(401, "未登录");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            String username = userService.extractUsername(token);
            User user = userService.updateUserInfo(username, nickname, email, phone);
            user.setPassword(null);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(500, e.getMessage());
        }
    }
}
