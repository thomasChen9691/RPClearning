# RpcVersion1 摘要
### 1.MyRPC版本1

#### 背景知识

- 反射
- 动态代理

#### 本节问题

- 如何使客户端请求远程方法支持多种?

- 如何使服务端返回值的类型多样?
## 概述
`RpcVersion1` 目录包含一个改进的远程过程调用（RPC）系统的实现。该系统允许客户端通过网络请求服务器上的数据，并支持多种方法调用和返回类型。设计更加灵活和通用，展示了如何在 Java 中实现更复杂的 RPC 系统。

## 目录结构
- `client`: 包含发起 RPC 请求的客户端代码。
- `common`: 包含客户端和服务器共用的类，例如 `User` 类和 `RpcRequest`、`RpcResponse` 类。
- `server`: 包含处理传入 RPC 请求的服务器代码。
- `service`: 包含服务接口及其实现。

## 设计说明

### RpcClient.java
- 建立到服务器 `localhost` 上端口 `8899` 的套接字连接。
- 使用 `ClientProxy` 动态代理生成 `UserService` 接口的代理对象。
- 调用代理对象的方法 `getUserByUserId` 和 `insertUserId`，并打印返回结果。

这种设计通过动态代理机制，使客户端可以灵活地调用服务端的多种方法，并接收不同类型的返回值。

### ClientProxy.java
- 实现 `InvocationHandler` 接口，用于处理代理对象的方法调用。
- 在 `invoke` 方法中，构建 `RpcRequest` 对象，并通过 `IoClient` 发送请求，接收 `RpcResponse` 响应。
- 提供 `getProxy` 方法，生成指定接口的代理对象。

这种设计通过动态代理和反射机制，使客户端可以在运行时动态构建请求并调用服务端的方法。

### IoClient.java
- 负责底层与服务端的通信，发送 `RpcRequest` 对象，接收 `RpcResponse` 对象。
- 建立与服务器的套接字连接，使用 `ObjectOutputStream` 发送请求，使用 `ObjectInputStream` 接收响应。

这种设计将网络通信的细节封装在一个独立的类中，使客户端代码更加简洁和清晰。

### RpcServer.java
- 使用 `ServerSocket` 监听端口 `8899` 上的传入连接。
- 对于每个传入连接，创建一个新线程来处理客户端请求。
- 使用 `ObjectOutputStream` 向客户端发送数据，使用 `ObjectInputStream` 从客户端接收数据。
- 通过反射机制，根据客户端请求调用相应的服务方法，并返回结果。

这种设计允许服务器通过创建新线程并发处理多个客户端连接，并通过反射机制动态调用服务方法，提高了系统的灵活性和可扩展性。

### UserServiceImpl.java
- 实现 `UserService` 接口。
- 提供 `getUserByUserId` 方法，模拟从数据库中获取用户数据。
- 提供 `insertUserId` 方法，模拟向数据库中插入用户数据。

这种设计允许服务器模拟从数据库中获取和插入用户数据，而无需实际连接到数据库。

### RpcRequest.java
- 封装客户端请求的类，包含服务接口名、方法名、参数列表和参数类型。
- 使用 `Serializable` 接口，使对象可以通过网络传输。

这种设计将请求的所有必要信息封装在一个对象中，便于在客户端和服务器之间传输。

### RpcResponse.java
- 封装服务器响应的类，包含状态码、状态信息和具体数据。
- 提供静态方法 `success` 和 `fail`，用于生成成功和失败的响应对象。

这种设计将响应的所有必要信息封装在一个对象中，便于在客户端和服务器之间传输，并提供统一的方式表示调用结果。

### User.java
- 用户类，包含用户的 ID、用户名和性别。
- 使用 `Serializable` 接口，使对象可以通过网络传输。
- 使用 Lombok 注解简化代码。

这种设计使用户对象可以在客户端和服务器之间传输，并简化了代码的编写。

## 结果
这种设计的结果是一个更加灵活和通用的 RPC 系统，客户端可以请求服务器上的多种方法，并接收不同类型的返回值。服务器通过反射机制动态调用方法，并返回结果。该系统展示了 RPC 的高级实现，包括动态代理、反射、对象的序列化和反序列化，以及客户端连接的并发处理。

## 解决的问题
- **灵活性**：通过动态代理和反射机制，客户端可以在运行时动态调用服务端的方法，而无需在编译时确定具体的方法。
- **可扩展性**：服务器可以通过反射机制动态调用方法，支持多种方法调用和返回类型，便于扩展和维护。
- **并发处理**：服务器通过创建新线程并发处理多个客户端连接，提高了系统的性能和响应速度。

## 反射和动态代理的使用
- **反射**：在 `RpcServer.java` 中，通过反射机制获取并调用客户端请求的方法：
  ```java
  Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
  Object invoke = method.invoke(userService, request.getParams());