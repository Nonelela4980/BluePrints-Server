package com.example.capstone;

import com.example.capstone.messages.server.JoinedGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private static List<ClientHandler> clientHandlers=new ArrayList<>();
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    //private BlockingQue
    private String username;
    public ClientHandler(Socket socket,String username) {
        try{
            this.socket = socket;
            this.username=username;
            out=new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
            //showOnlinePlayers();
            clientHandlers.add(this);
            sendMessage(new JoinedGame(username));
            System.out.println(new JoinedGame(username));
            //sendMessage(this.username+" entered the game");
        }catch (IOException e)
        {
            System.out.println("Error:"+e.getMessage());
        }
    }
    private void showOnlinePlayers(){
        for(ClientHandler cur:clientHandlers){
            if(cur.username!=this.username){
                //sendMessage(cur.username+" entered the game");
            }
        }
    }
    
    @Override
    public void run() {
        Message messageClient;
        while (true){
            try {
                messageClient= (Message) in.readObject();
                sendMessage(messageClient);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error: "+e.getMessage());
                break;
            }
        }
    }
    public void sendMessage(Message message) {
        //send nmmu.wrpv302.capstone.client.server.Message to each and every nmmu.wrpv302.capstone.client
        for(ClientHandler clientHandler:clientHandlers){
            if(clientHandler!=null){
                try{
                    clientHandler.out.writeObject(message);
                    clientHandler.out.flush();
                    //if(!clientHandler.username.equals(username)){
                    // }
                }catch (IOException e){
                    System.out.println("Error:"+e.getMessage());
                }
            }
        }
    }
}
