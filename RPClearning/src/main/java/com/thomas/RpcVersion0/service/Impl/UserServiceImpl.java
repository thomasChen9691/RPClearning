package com.thomas.RpcVersion0.service.Impl;

import com.thomas.RpcVersion0.common.User;
import com.thomas.RpcVersion0.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {
        System.out.println("client search" + id + "user");
        //random select from database
        Random random = new Random();
        User user = User.builder().userName(UUID.randomUUID().toString())
                .id(id)
                .sex(random.nextBoolean()).build();
        return user;
    }
}
