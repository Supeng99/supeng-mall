package com.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.entity.User;

public interface UserService extends IService<User> {
    User register(String username, String password);
    User login(String username, String password);
    String generateToken(Long userId, String username);
    String extractUsername(String token);
    User getUserByUsername(String username);
    User updateUserInfo(String username, String nickname, String email, String phone);
}
