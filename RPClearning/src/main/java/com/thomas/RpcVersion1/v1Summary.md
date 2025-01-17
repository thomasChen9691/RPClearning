# RpcVersion1 Summary
### 1. MyRPC Version 1

#### Background

- Reflection
- Dynamic Proxy

#### Questions for this section

- How to make the client request remote methods support multiple types?

- How to make the server return value types diverse?

## Overview
The `RpcVersion1` directory contains an improved implementation of the remote procedure call (RPC) system. The system allows clients to request data on the server over the network and supports multiple method calls and return types. The design is more flexible and general, showing how to implement more complex RPC systems in Java.

## Directory Structure
- `client`: Contains client code that initiates RPC requests.
- `common`: Contains classes common to clients and servers, such as the `User` class and the `RpcRequest` and `RpcResponse` classes.
- `server`: Contains server code that handles incoming RPC requests.
- `service`: Contains service interfaces and their implementations.

## Design Description

### RpcClient.java
- Establish a socket connection to port `8899` on the server `localhost`.
- Use `ClientProxy` dynamic proxy to generate a proxy object of the `UserService` interface.
- Call the proxy object's methods `getUserByUserId` and `insertUserId`, and print the return results.

This design uses the dynamic proxy mechanism to enable the client to flexibly call multiple methods on the server and receive different types of return values.

### ClientProxy.java
- Implement the `InvocationHandler` interface to handle method calls on the proxy object.
- In the `invoke` method, build an `RpcRequest` object, send a request through `IoClient`, and receive an `RpcResponse` response.
- Provide a `getProxy` method to generate a proxy object of the specified interface.

This design uses dynamic proxy and reflection mechanisms to enable the client to dynamically build requests and call server methods at runtime.

### IoClient.java
- Responsible for the underlying communication with the server, sending `RpcRequest` objects and receiving `RpcResponse` objects.
- Establish a socket connection with the server, use `ObjectOutputStream` to send requests, and use `ObjectInputStream` to receive responses.

This design encapsulates the details of network communication in a separate class, making the client code more concise and clear.

### RpcServer.java
- Use `ServerSocket` to listen for incoming connections on port `8899`.
- For each incoming connection, create a new thread to handle client requests.
- Use `ObjectOutputStream` to send data to the client and `ObjectInputStream` to receive data from the client.
- Through the reflection mechanism, call the corresponding service method according to the client request and return the result.

This design allows the server to handle multiple client connections concurrently by creating new threads and dynamically call service methods through the reflection mechanism, which improves the flexibility and scalability of the system.

### UserServiceImpl.java
- Implement the `UserService` interface.
- Provides `getUserByUserId` method to simulate getting user data from the database.
- Provides `insertUserId` method to simulate inserting user data into the database.

This design allows the server to simulate getting and inserting user data from the database without actually connecting to the database.

### RpcRequest.java
- A class that encapsulates client requests, including service interface name, method name, parameter list, and parameter type.
- Uses `Serializable` interface to enable objects to be transmitted over the network.

This design encapsulates all necessary information of the request in one object, which is convenient for transmission between the client and the server.

### RpcResponse.java
- A class that encapsulates the server response, including status code, status information, and specific data.
- Provides static methods `success` and `fail` for generating successful and failed response objects.

This design encapsulates all necessary information of the response in one object, which is convenient for transmission between the client and the server, and provides a unified way to represent the call results.

### User.java
- User class, including the user's ID, username, and gender.
- Use the `Serializable` interface to enable objects to be transmitted over the network.
- Use Lombok annotations to simplify code.

This design enables user objects to be transmitted between the client and the server and simplifies code writing.

## Result
The result of this design is a more flexible and general RPC system, where the client can request multiple methods on the server and receive different types of return values. The server dynamically calls methods through the reflection mechanism and returns results. The system demonstrates the advanced implementation of RPC, including dynamic proxy, reflection, serialization and deserialization of objects, and concurrent processing of client connections.

## Problems solved
- **Flexibility**: Through dynamic proxy and reflection mechanism, the client can dynamically call the server method at runtime without determining the specific method at compile time.
- **Extensibility**: The server can dynamically call methods through the reflection mechanism, support multiple method calls and return types, and facilitate expansion and maintenance.
- **Concurrent processing**: The server concurrently processes multiple client connections by creating new threads, which improves the performance and responsiveness of the system.

## Use of reflection and dynamic proxy
- **Reflection**: In `RpcServer.java`, obtain and call the method requested by the client through the reflection mechanism:
### java
Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
Object invoke = method.invoke(userService, request.getParams());

#### Summary

1. Define a more general message format: Request and Response format, from now on, different methods may be called and various types of data may be returned.

2. Dynamic proxy is used to encapsulate the requests of different service methods,

3. The client is more loosely coupled and no longer bound to a specific Service, host, or port

#### Existing pain points

1. On the server side, we directly bind to the UserService service. What if there are other service interfaces exposed? (Registration of multiple services)

2. Is the performance of the server side too low in the BIO mode?

3. The server side functions are too complex: monitoring, processing. Loose coupling is required