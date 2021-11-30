package com.example.capstone.messages.server;

import com.example.capstone.Message;

public class PlayerStarting extends Message
{
    private static final long serialVersionUID = 18L;
    public String username;
    public PlayerStarting(String username) {
        this.username = username;
    }
    @Override
    public String toString(){
        return String.format("%s is starting the game",username);
    }
}
