package com.thomas.RpcVersion0.service;

import com.thomas.RpcVersion0.common.User;

public interface UserService {

    //client use this interface to achieve server class
    User getUserByUserId(Integer id);
}
