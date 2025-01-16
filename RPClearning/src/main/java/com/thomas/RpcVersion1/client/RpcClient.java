package com.thomas.RpcVersion1.client;

import com.thomas.RpcVersion1.common.User;
import com.thomas.RpcVersion1.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class RpcClient {
    public static void main(String[] args) {
            // Build socket connection to the server at localhost on port 8899
            // 建立Socket连接
            ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
            UserService proxy = clientProxy.getProxy(UserService.class);

            // 服务的方法1
            User userByUserId = proxy.getUserByUserId(10);
            System.out.println("从服务端得到的user为：" + userByUserId);
            // 服务的方法2
            User user = User.builder().userName("张三").id(100).sex(true).build();
            Integer integer = proxy.insertUserId(user);
            System.out.println("向服务端插入数据："+integer);

    }
}