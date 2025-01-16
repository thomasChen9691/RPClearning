package com.thomas.RpcVersion0.client;

import com.thomas.RpcVersion0.common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket

import java.io.IOException;
import java.util.Random;

public class RpcClient {
    public static void main(String[] args) {

        try{
            //build socket connection
            Socket socket = new Socket("127.0.0.1", 8899);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //send Id to server;
            objectOutputStream.writeInt(new Random().nextInt());
            objectOutputStream.flush();
            //server search data,return relevant object
            User user = (User) objectInputStream.readObject();
            System.out.println("Server return User" + user);

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("client start fail");
        }


    }
}
