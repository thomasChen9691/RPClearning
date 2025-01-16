package com.thomas.RpcVersion0.client;

import com.thomas.RpcVersion0.common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.util.Random;

public class RpcClient {
    public static void main(String[] args) {

        try {
            // Build socket connection to the server at localhost on port 8899
            // 建立Socket连接
            Socket socket = new Socket("127.0.0.1", 8899);
            // Create an ObjectOutputStream to send data to the server
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // Create an ObjectInputStream to receive data from the server
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            
            // Send a random integer ID to the server
            // 开启一个线程去处理
            objectOutputStream.writeInt(new Random().nextInt());
            // Flush the stream to ensure the data is sent immediately
            objectOutputStream.flush();
            
            // Receive a User object from the server
            // 开启一个线程去处理
            User user = (User) objectInputStream.readObject();
            // Print the received User object
            System.out.println("Server return User" + user);

        } catch (IOException | ClassNotFoundException e) {
            // Print stack trace for debugging purposes
            e.printStackTrace();
            // Inform that the client failed to start
            System.out.println("client start fail");
        }
    }
}