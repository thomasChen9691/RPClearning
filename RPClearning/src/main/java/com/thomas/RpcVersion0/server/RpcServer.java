package com.thomas.RpcVersion0.server;

import com.thomas.RpcVersion0.common.User;
import com.thomas.RpcVersion0.service.Impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer {
    public static void main(String[] args) throws IOException {
        UserServiceImpl userService = new UserServiceImpl();
        try {
            // Create a ServerSocket to listen on port 8899
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("Server start");
        
            // Use a blocking I/O method to listen for incoming socket connections
            // BIO的方式监听Socket
            while (true) {
                // Accept an incoming connection from a client
                Socket socket = serverSocket.accept();
        
                // Create a new thread to handle the client connection
                // 开启一个线程去处理
                new Thread(() -> {
                    try {
                        // Create an ObjectOutputStream to send data to the client
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        // Create an ObjectInputStream to receive data from the client
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        //read id from client
                        // 读取客户端传过来的id
                        Integer id = ois.readInt();
                        User userByUserId = userService.getUserByUserId(id);
                        //write User to client
                        // 写入User对象给客户端
                        oos.writeObject(userByUserId);
                        oos.flush();// Additional code to handle the client request would go here
        
                    } catch (IOException e) {
                        // Print stack trace for debugging purposes
                        e.printStackTrace();
                        // Inform that there was an error reading data
                        System.out.println("IO read data wrong");
                    }
                }).start(); // Start the thread to handle the client connection
            }
        } catch (IOException e) {
            // Print stack trace for debugging purposes
            e.printStackTrace();
            // Inform that the server failed to start
            System.out.println("Server start fail");
        }
    }
}
