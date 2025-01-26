package com.thomas.RpcVersion3.NettyStudy;

import com.thomas.RpcVersion3.server.ServiceProvider;

// 定义一个接口
interface HelloService {
    void sayHello();
}

// 定义另一个接口
interface GoodbyeService {
    void sayGoodbye();
}

// 实现上述两个接口的服务类
class HelloGoodbyeServiceImpl implements HelloService, GoodbyeService {
    @Override
    public void sayHello() {
        System.out.println("Hello!");
    }

    @Override
    public void sayGoodbye() {
        System.out.println("Goodbye!");
    }
}

// ServiceProvider

public class ExampleServiceProvider {
    public static void main(String[] args) {
        // 创建 ServiceProvider 对象
        ServiceProvider serviceProvider = new ServiceProvider();

        // 创建服务实现对象
        HelloGoodbyeServiceImpl serviceImpl = new HelloGoodbyeServiceImpl();

        // 向 ServiceProvider 注册服务
        serviceProvider.provideServiceInterface(serviceImpl);

        // 根据接口名获取服务实现对象
        HelloService helloService = (HelloService) serviceProvider.getService(HelloService.class.getName());
        GoodbyeService goodbyeService = (GoodbyeService) serviceProvider.getService(GoodbyeService.class.getName());

        // 调用服务方法
        helloService.sayHello();
        goodbyeService.sayGoodbye();
    }
}

