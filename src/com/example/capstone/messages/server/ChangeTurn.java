package com.example.capstone.messages.server;
import com.example.capstone.Message;
//Sends the username of the person playing
public class ChangeTurn extends Message {
    private static final long serialVersionUID = 11L;
    String username;
    public ChangeTurn(String username) {
        this.username = username;
    }
    @Override
    public String toString(){
        return username;
    }
}
