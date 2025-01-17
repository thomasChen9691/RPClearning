package com.thomas.RpcVersion2.client;

import com.thomas.RpcVersion2.common.Blog;
import com.thomas.RpcVersion2.common.User;
import com.thomas.RpcVersion2.service.BlogService;
import com.thomas.RpcVersion2.service.UserService;

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

            BlogService blogService = clientProxy.getProxy(BlogService.class);
            Blog blogById = blogService.getBlogById(10000);
            System.out.println("从服务端得到的blog为：" + blogById);

    }
}