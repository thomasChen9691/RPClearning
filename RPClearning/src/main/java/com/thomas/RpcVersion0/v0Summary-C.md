# RpcVersion0 摘要

## 概述
`RpcVersion0` 目录包含远程过程调用 (RPC) 系统的简单实现。此系统允许客户端通过网络从服务器请求数据。设计简单明了，是使用 Java 实现 RPC 的基本示例。

## 目录结构
- `client`：包含发起 RPC 请求的客户端代码。
- `common`：包含客户端和服务器使用的公共类，例如 `User` 类。
- `server`：包含处理传入 RPC 请求的服务器端代码。
- `service`：包含服务接口及其实现。

## 设计说明

### RpcClient.java
- 在端口 `8899` 上的 `localhost` 处建立与服务器的套接字连接。
- 使用 `ObjectOutputStream` 向服务器发送随机整数 ID。
- 使用 `ObjectInputStream` 从服务器接收 `User` 对象。
- 打印接收到的 `User` 对象。

此设计允许客户端向服务器发送请求并以 `User` 对象的形式接收响应。使用 `ObjectOutputStream` 和 `ObjectInputStream` 可确保复杂对象能够通过网络轻松序列化和反序列化。

### RpcServer.java
- 使用 `ServerSocket` 监听端口 `8899` 上的传入连接。
- 对于每个传入连接，创建一个新线程来处理客户端请求。
- 使用 `ObjectOutputStream` 向客户端发送数据并使用 `ObjectInputStream` 从客户端接收数据。

此设计允许服务器通过为每个连接创建一个新线程来同时处理多个客户端连接。使用 `ObjectOutputStream` 和 `ObjectInputStream` 可确保复杂对象能够通过网络轻松序列化和反序列化。

### UserServiceImpl.java
- 实现 `UserService` 接口。
- 提供方法 `getUserByUserId`，模拟从数据库获取用户数据。
- 生成一个带有随机值的 `User` 对象并返回。

此设计允许服务器模拟从数据库获取用户数据，而无需实际连接到数据库。使用随机值可确保每个请求都返回唯一的 `User` 对象。

## 结果
此设计的结果是一个简单 RPC 系统，客户端可以从服务器请求用户数据。服务器以包含随机值的 `User` 对象进行响应。该系统演示了 RPC 的基本原理，包括网络通信、对象的序列化和反序列化以及客户端连接的并发处理。

## 结论
`RpcVersion0` 目录提供了 Java 中 RPC 系统的基本示例。该设计简单而有效，演示了套接字通信、线程和对象序列化等关键概念。此示例可作为更复杂的 RPC 系统的基础。

这个例子以不到百行的代码，实现了客户端与服务端的一个远程过程调用，非常适合上手，当然它是及其不完善的，甚至连消息格式都没有统一，我们将在接下来的版本更新中逐渐完善它。

此RPC的最大痛点：
只能调用服务端Service唯一确定的方法，如果有两个方法需要调用呢?（Reuest需要抽象）
返回值只支持User对象，如果需要传一个字符串或者一个Dog，String对象呢（Response需要抽象）
客户端不够通用，host，port， 与调用的方法都特定（需要抽象）

