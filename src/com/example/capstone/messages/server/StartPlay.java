package com.example.capstone.messages.server;
import com.example.capstone.Message;
public class StartPlay extends Message
{
    private static final long serialVersionUID = 2L;
    private String text="start game";
    @Override
    public String toString() {
        return text;
    }
}
