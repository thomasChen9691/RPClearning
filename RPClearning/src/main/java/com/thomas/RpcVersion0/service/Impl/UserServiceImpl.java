package com.thomas.RpcVersion0.service.Impl;

import com.thomas.RpcVersion0.common.User;
import com.thomas.RpcVersion0.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {
        // Log the user ID being searched for
        System.out.println("client search" + id + "user");
        
        // Simulate fetching user data from a database with random values
        // 模拟从数据库中取用户的行为
        Random random = new Random();
        User user = User.builder()
                // Generate a random username using UUID
                .userName(UUID.randomUUID().toString())
                // Set the user ID to the provided ID
                .id(id)
                // Randomly assign a gender (true for male, false for female)
                .sex(random.nextBoolean())
                .build();
        
        // Return the created User object
        return user;
    }
}