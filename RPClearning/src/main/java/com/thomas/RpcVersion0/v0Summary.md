# RpcVersion0 Summary

#### **Background knowledge**

- Java basics
- Java socket programming introduction
- The project is built using Maven, and only the lombok package is introduced for the time being

#### Questions in this section

- **What is RPC, and how to complete an RPC?**

The **simplest** process of an RPC is that the client **calls** a method on the server, and the server returns the return value of the execution method to the client. Next, I will use an example of fetching data from a database to simulate a complete process of the RPC process.

**Assume** that there is a service like the following:

Server:

1. There is a User table
2. UserServiceImpl implements the UserService interface
3. There is only one function in UserService for the time being: getUserByUserId(Integer id)

Client:Pass an ID to the server, and the server queries the User object and returns it to the client

## Overview
The `RpcVersion0` directory contains a simple implementation of a Remote Procedure Call (RPC) system. This system allows a client to request data from a server over a network. The design is straightforward and serves as a basic example of how RPC can be implemented in Java.

## Directory Structure
- `client`: Contains the client-side code that initiates the RPC request.
- `common`: Contains common classes used by both the client and server, such as the `User` class.
- `server`: Contains the server-side code that handles incoming RPC requests.
- `service`: Contains the service interface and its implementation.

## Design Explanation

### RpcClient.java
- Establishes a socket connection to the server at `localhost` on port `8899`.
- Uses `ObjectOutputStream` to send a random integer ID to the server.
- Uses `ObjectInputStream` to receive a `User` object from the server.
- Prints the received `User` object.

This design allows the client to send a request to the server and receive a response in the form of a `User` object. The use of `ObjectOutputStream` and `ObjectInputStream` ensures that complex objects can be easily serialized and deserialized over the network.

### RpcServer.java
- Listens for incoming connections on port `8899` using a `ServerSocket`.
- For each incoming connection, creates a new thread to handle the client request.
- Uses `ObjectOutputStream` to send data to the client and `ObjectInputStream` to receive data from the client.

This design allows the server to handle multiple client connections concurrently by creating a new thread for each connection. The use of `ObjectOutputStream` and `ObjectInputStream` ensures that complex objects can be easily serialized and deserialized over the network.

### UserServiceImpl.java
- Implements the `UserService` interface.
- Provides a method `getUserByUserId` that simulates fetching user data from a database.
- Generates a `User` object with random values and returns it.

This design allows the server to simulate fetching user data from a database without actually connecting to a database. The use of random values ensures that each request returns a unique `User` object.

## Result
The result of this design is a simple RPC system where a client can request user data from a server. The server responds with a `User` object containing random values. This system demonstrates the basic principles of RPC, including network communication, serialization, and deserialization of objects, and concurrent handling of client connections.

## Conclusion
The `RpcVersion0` directory provides a basic example of an RPC system in Java. The design is simple yet effective, demonstrating key concepts such as socket communication, threading, and object serialization. This example can serve as a foundation for more complex RPC systems.

This example implements a remote procedure call between the client and the server with less than 100 lines of code. It is very suitable for getting started. Of course, it is extremely imperfect, and even the message format is not unified. We will gradually improve it in the next version update.

The biggest pain point of this RPC:
You can only call the only method of the server Service. What if there are two methods that need to be called? (Reuest needs abstraction)
The return value only supports User objects. What if you need to pass a string or a Dog, String object (Response needs abstraction)
The client is not universal enough, the host, port, and the calling method are all specific (need abstraction)
#### Summary:

This example uses less than 100 lines of code to implement a remote procedure call between the client and the server. It is very suitable for getting started. Of course, it is **extremely imperfect**, and even the message format is not unified. We will gradually improve it in the next version update.

#### The biggest pain points of this RPC:

1. Only the unique method of the server Service can be called. What if there are two methods that need to be called? (Reuest needs to be abstracted)

2. The return value only supports User objects. What if a string or a Dog, String object needs to be passed (Response needs to be abstracted)

3. The client is not universal enough. The host, port, and the calling method are all specific (need to be abstracted)