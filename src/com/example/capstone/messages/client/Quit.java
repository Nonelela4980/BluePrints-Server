package com.example.capstone.messages.client;

import com.example.capstone.ClientHandler;
import com.example.capstone.GameController;
import com.example.capstone.Message;

public class Quit extends Message<ClientHandler> {
    private static final long serialVersionUID = 80L;
    @Override
    public void applyLogic(ClientHandler clientHandler)
    {
        GameController.leaveGame(clientHandler);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
