package com.thomas.RpcVersion2.service;

import com.thomas.RpcVersion2.common.User;

public interface UserService {

    //client use this interface to achieve server class
    // 客户端通过这个接口调用服务端的实现类
    User getUserByUserId(Integer id);
    // 给这个服务增加一个功能
    Integer insertUserId(User user);
}
