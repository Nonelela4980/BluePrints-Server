package com.example.capstone;

import com.example.capstone.messages.client.Quit;
import com.example.capstone.messages.server.JoinedGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ClientHandler {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    BlockingQueue<Message> outgoingMessages = new LinkedBlockingDeque<>();
    public String username;
    ReadingThread readThread;
    WriteThread writeThread;
    public boolean isPlaying=false;
    public ClientHandler(Socket socket)
    {
        this.socket=socket;
        readThread=new ReadingThread();
        readThread.start();
        GameController.addClient(this);
        System.out.println(new JoinedGame(username));
    }

    //Client read thread which is responsible for reading messages from the client
    public class ReadingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Reading thread started running");
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(socket.getInputStream());

                writeThread = new WriteThread();
                writeThread.start();
                Message message;
                do {
                    message = (Message) in.readObject();
                    message.applyLogic(ClientHandler.this);
                    System.out.println(username + "------>" + message);
                } while (message.getClass() != Quit.class);
            } catch (Exception e) {
                System.out.println("ReadingThread error:" + e.getMessage() + "for" + username);
                e.printStackTrace();
            }
        }
    }
    //client write thread which is responsible for writing messages to the thread
    public class WriteThread extends Thread {
        @Override
        public void run() {
            System.out.println("Writing thread started running");
            writeThread = this;

            try {
                while (!interrupted()) {
                    Message message = outgoingMessages.take();
                    out.writeObject(message);
                    out.flush();
                }
            } catch (Exception e) {
                System.out.println("WriteThread error:" + e.getMessage() + "for" + username);
                e.printStackTrace();
            }
        }
    }

    public void disconnect(){
        try {
            socket.close();
            writeThread.interrupt();
            readThread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Server sends a message to a client
    public void sendMessage(Message message) {
        try {
            outgoingMessages.put(message);
        } catch (InterruptedException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
