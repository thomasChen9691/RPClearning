package com.thomas.RpcVersion0.server;

import com.thomas.RpcVersion0.service.Impl.UserServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;

public class RpcServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        try{
            ServerSocket serverSocket = new ServerSocket(8899);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
