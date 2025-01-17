package com.thomas.RpcVersion2.server;

import com.thomas.RpcVersion0.service.Impl.BlogServiceImpl;
import com.thomas.RpcVersion2.service.Impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class TestServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        BlogServiceImpl blogService = new BlogServiceImpl();
        /*
        Map<String, Object> serviceProvider = new HashMap<>();
        // 暴露两个服务接口， 即在RPCServer中加一个HashMap
        serviceProvider.put("com.thomas.RpcVersion0.service.UserService", userService);
        serviceProvider.put("com.thomas.RpcVersion0.service.BlogService", blogService);*/
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        //RpcServer rpcServer = new SimpleRpcServer(serviceProvider);
        RpcServer rpcServer = new ThreadPoolRpcServer(serviceProvider);
        rpcServer.start(8899);


    }
}
