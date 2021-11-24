package com.example.capstone.messages.server;

import com.example.capstone.Message;

public class PlayerLeft extends Message {
    private static final long serialVersionUID = 76L;
    private String username;
    public PlayerLeft(String username) {
        this.username = username;
    }
    @Override
    public String toString()
    {
        return String.format("%s left",username);
    }
}
