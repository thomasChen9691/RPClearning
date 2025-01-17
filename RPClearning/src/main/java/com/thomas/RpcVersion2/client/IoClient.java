package com.thomas.RpcVersion2.client;

import com.thomas.RpcVersion2.common.RpcRequest;
import com.thomas.RpcVersion2.common.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IoClient {
    // 这里负责底层与服务端的通信，发送的Request，接受的是Response对象
    // 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
    // 这里的request是封装好的（上层进行封装），不同的service需要进行不同的封装， 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
    public static RpcResponse sendRequest(String host, int port, RpcRequest request){
        try {
            // 建立与服务器的Socket连接
            Socket socket = new Socket(host, port);

            // 创建对象输出流，用于发送请求对象
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // 创建对象输入流，用于接收响应对象
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // 打印请求对象（用于调试）
            System.out.println(request);
            // 发送请求对象到服务器
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            // 从服务器接收响应对象
            RpcResponse response = (RpcResponse) objectInputStream.readObject();

            // 返回响应对象
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // 打印异常信息
            return null;
        }
    }
}
