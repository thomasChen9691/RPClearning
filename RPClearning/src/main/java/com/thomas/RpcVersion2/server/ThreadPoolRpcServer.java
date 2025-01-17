package com.thomas.RpcVersion2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池版服务端的实现
 */
public class ThreadPoolRpcServer implements RpcServer{
    private final ThreadPoolExecutor threadPoolExecutor;
    private ServiceProvider serviceProvider;

    public ThreadPoolRpcServer( ServiceProvider serviceProvider) {
        threadPoolExecutor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(), // 核心线程数
                1000, // 最大线程数
                60, // 空闲线程存活时间
                TimeUnit.SECONDS, // 时间单位
                new ArrayBlockingQueue<>(100) // 阻塞队列
        );
        this.serviceProvider = serviceProvider;
    }
    public ThreadPoolRpcServer(ServiceProvider serviceProvider, int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue){

        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.serviceProvider = serviceProvider;
    }
    @Override
    public void start(int port) {
        System.out.println("线程池版服务端启动了");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                threadPoolExecutor.execute(new WorkThread(socket,serviceProvider));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    // 优雅关闭线程池
    public void stop() {
        System.out.println("正在关闭线程池...");
        threadPoolExecutor.shutdown();
        try {
            if (!threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPoolExecutor.shutdownNow();
                if (!threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("线程池未能关闭");
                }
            }
        } catch (InterruptedException ie) {
            threadPoolExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("线程池已关闭");
    }
}
