package com.example.capstone;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private final int PORT_NUMBER=5000;
    int counter;
    public Server() {
        this.counter = 0;
    }
    /**Starts the connection*/
    public void start(){
        try {
            serverSocket=new ServerSocket(5000);
            System.out.println("Server started on "+ InetAddress.getLocalHost().getHostAddress());
            while (!serverSocket.isClosed()){
                Socket clientSocket=serverSocket.accept();
                counter++;
                System.out.println("Client#"+counter+ " is connected from: "+clientSocket.getInetAddress());
                //handles mul
                ClientHandler clientHandler=new ClientHandler(clientSocket);
            }
        }catch (IOException e){
            closeServer();
            System.out.println("Error: "+e.getMessage());
        }
    }
    //close nmmu.wrpv302.capstone.client.server
    public void closeServer(){
        try
        {
            if(serverSocket!=null)
                serverSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
