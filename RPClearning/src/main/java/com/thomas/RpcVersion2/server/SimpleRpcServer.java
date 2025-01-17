package com.thomas.RpcVersion2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * 这个实现类代表着java原始的BIO监听模式，来一个任务，就new一个线程去处理
 * 处理任务的工作见WorkThread中
 */
public class SimpleRpcServer implements RpcServer{
    // 存着服务接口名-> service对象的map
    private ServiceProvider serviceProvider;
    private boolean running = false;
    private ServerSocket serverSocket;
    public SimpleRpcServer(ServiceProvider serviceProvider){
        this.serviceProvider = serviceProvider;
    }
    @Override
    public void start(int port) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("server start");
            while (true){
                Socket socket = serverSocket.accept();
                // 开启一个新线程去处理
                new Thread(new WorkThread(socket,serviceProvider)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }

    @Override
    public void stop() {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("服务端已关闭");
    }
}
