package com.thomas.RpcVersion3.server;

// 把RpcServer抽象成接口，以后的服务端实现这个接口即可
public interface RpcServer {
    void start(int port);
    void stop();
}
