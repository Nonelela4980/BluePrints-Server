package com.example.capstone.messages.client;

import com.example.capstone.ClientHandler;
import com.example.capstone.Message;

public class SetUsername extends Message<ClientHandler> {
    private static final long serialVersionUID = 73L;

    public String username;

    @Override
    public String toString() {
        return String.format("Username('%s')", username);
    }
    @Override
    public void applyLogic(ClientHandler clientHandler)
    {   //set the username of the player
        clientHandler.username=username;

    }
}
