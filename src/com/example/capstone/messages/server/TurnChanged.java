package com.example.capstone.messages.server;
public class TurnChanged {
    private static final long serialVersionUID = 21L;
    public boolean isTurn;
    public String  username;
    @Override
    public String toString(){
        return String.format("%s's turn",username);
    }
}
