# RpcVersion2 Summary

## Overview
The `RpcVersion2` directory contains an improved implementation of a remote procedure call (RPC) system. The system allows clients to request data from a server over a network and supports a variety of method calls and return types. The design is more flexible and general, showing how to implement more complex RPC systems in Java.

## Directory Structure
- `client`: Contains client code that initiates RPC requests.
- `common`: Contains classes common to both client and server, such as the `User` class and the `RpcRequest`, `RpcResponse` classes.
- `server`: Contains server code that handles incoming RPC requests.
- `service`: Contains the service interface and its implementation.

## Design Notes

### RpcClient.java
- Establishes a socket connection to the server `localhost` on port `8899`.
- Generates proxy objects for the `UserService` and `BlogService` interfaces using the `ClientProxy` dynamic proxy.
- Call the proxy object's methods `getUserByUserId`, `insertUserId`, and `getBlogById`, and print the return results.

This design uses a dynamic proxy mechanism to allow the client to flexibly call multiple methods on the server and receive different types of return values.

### ClientProxy.java
- Implement the `InvocationHandler` interface to handle method calls on proxy objects.
- In the `invoke` method, build an `RpcRequest` object, send a request through `IoClient`, and receive an `RpcResponse` response.
- Provide a `getProxy` method to generate a proxy object of the specified interface.

This design uses a dynamic proxy and reflection mechanism to allow the client to dynamically build requests and call server methods at runtime.

### IoClient.java
- Responsible for the underlying communication with the server, sending an `RpcRequest` object and receiving an `RpcResponse` object.
- Establish a socket connection with the server, use `ObjectOutputStream` to send requests, and use `ObjectInputStream` to receive responses.

This design encapsulates the details of network communication in a separate class, making the client code more concise and clear.

### RpcServer.java
- Use `ServerSocket` to listen for incoming connections on port `8899`.
- For each incoming connection, create a new thread to handle client requests.
- Use `ObjectOutputStream` to send data to the client, and use `ObjectInputStream` to receive data from the client.
- Through the reflection mechanism, call the corresponding service method according to the client request and return the result.

This design allows the server to handle multiple client connections concurrently by creating new threads and dynamically call service methods through the reflection mechanism, which improves the flexibility and scalability of the system.

### WorkThread.java
- Responsible for parsing the `RpcRequest` request from the client, executing the corresponding service method, and encapsulating the result into `RpcResponse` and returning it to the client.
- Get the interface name, method name and parameters from `RpcRequest`, and call the service method through the reflection mechanism.

This design enables the server to dynamically parse and execute client requests, improving the flexibility of the system.

### UserService.java
- Define the user service interface, including `getUserByUserId` and `insertUserId` methods.
- The client calls the implementation class of the server through this interface.

This design defines the service through the interface, making the calls between the client and the server more standardized and unified.

### UserServiceImpl.java
- Implement the `UserService` interface.
- Provide the `getUserByUserId` method to simulate getting user data from the database.
- Provide the `insertUserId` method to simulate inserting user data into the database.

This design allows the server to simulate getting and inserting user data from the database without actually connecting to the database.

### BlogService.java
- Define the blog service interface, including the `getBlogById` method.
- The client calls the implementation class of the server through this interface.

This design defines the service through the interface, making the calls between the client and the server more standardized and unified.

### BlogServiceImpl.java
- Implement the `BlogService` interface.
- Provide the `getBlogById` method to simulate getting blog data from the database.

This design allows the server to simulate getting blog data from the database without actually connecting to the database.

### RpcRequest.java
- A class that encapsulates client requests, including the service interface name, method name, parameter list, and parameter type.
- Use the `Serializable` interface so that the object can be transmitted over the network.

This design encapsulates all the necessary information of the request in one object, which is easy to transmit between the client and the server.

### RpcResponse.java
- A class that encapsulates the server response, including the status code, status information, and specific data.
- Provide static methods `success` and `fail` to generate successful and failed response objects.

This design encapsulates all necessary information of the response in an object, which is convenient for transmission between the client and the server, and provides a unified way to represent the call results.

### TestServer.java
- Initialize and start the server, expose the `UserService` and `BlogService` interfaces.
- Use `ServiceProvider` to register the service interface, and start `RpcServer` to listen on port `8899`.

This design enables the client to call the server method over the network by registering the service interface and starting the server.

## Result
The result of this design is a more flexible and general RPC system, where the client can request multiple methods on the server and receive different types of return values. The server dynamically calls methods through the reflection mechanism and returns results. The system demonstrates the advanced implementation of RPC, including dynamic proxy, reflection, serialization and deserialization of objects, and concurrent handling of client connections.

## Problem solved
- **Flexibility**: Through dynamic proxy and reflection mechanism, the client can dynamically call the server method at runtime without determining the specific method at compile time.
- **Scalability**: The server can dynamically call methods through the reflection mechanism, supporting multiple method calls and return types, which is easy to expand and maintain.
- **Concurrent processing**: The server concurrently processes multiple client connections by creating new threads, improving the performance and response speed of the system.

## Use of reflection and dynamic proxy
- **Reflection**: In `WorkThread.java`, obtain and call the method requested by the client through the reflection mechanism:
```java
Method method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
Object result = method.invoke(service, request.getParams());