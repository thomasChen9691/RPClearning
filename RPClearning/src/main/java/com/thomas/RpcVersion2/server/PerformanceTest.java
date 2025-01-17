package com.thomas.RpcVersion2.server;

import com.thomas.RpcVersion2.service.Impl.UserServiceImpl;
import com.thomas.RpcVersion2.service.Impl.BlogServiceImpl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class PerformanceTest {
    public static void main(String[] args) throws InterruptedException {
        // Initialize services
        UserServiceImpl userService = new UserServiceImpl();
        BlogServiceImpl blogService = new BlogServiceImpl();
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        // Test SimpleRpcServer
        System.out.println("Testing SimpleRpcServer...");
        SimpleRpcServer simpleRpcServer = new SimpleRpcServer(serviceProvider);
        new Thread(() -> simpleRpcServer.start(8899)).start();
        long simpleRpcServerTime = testServerPerformance(8899);
        System.out.println("SimpleRpcServer time: " + simpleRpcServerTime + " ms");

        // Test ThreadPoolRpcServer
        System.out.println("Testing ThreadPoolRpcServer...");
        ThreadPoolRpcServer threadPoolRpcServer = new ThreadPoolRpcServer(serviceProvider);
        new Thread(() -> threadPoolRpcServer.start(8898)).start();
        long threadPoolRpcServerTime = testServerPerformance(8898);
        System.out.println("ThreadPoolRpcServer time: " + threadPoolRpcServerTime + " ms");

        // Exit JVM to stop servers
        System.exit(0);
    }

    private static long testServerPerformance(int port) throws InterruptedException {
        int numRequests = 1000; // Number of requests to send
        CountDownLatch latch = new CountDownLatch(numRequests);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numRequests; i++) {
            new Thread(() -> {
                try {
                    Socket socket = new Socket("127.0.0.1", port);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                    // Send a request (e.g., a random integer ID)
                    oos.writeInt(123);
                    oos.flush();

                    // Receive a response
                    Object response = ois.readObject();

                    ois.close();
                    oos.close();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await(); // Wait for all requests to complete
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}