package com.example.capstone;
import com.example.capstone.messages.server.*;
import com.example.capstone.models.cards.Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**This class acts as a broker, it handles the communication between the server and a client
 * Responsible for sending messages from a client to other clients
 * */
public class GameController
{
    private static final ReentrantLock lock=new ReentrantLock();
    private static List<ClientHandler> clientHandlers=new ArrayList<>(); //stores the list of connected players
    private Assignment[][] board=new Assignment[3][5];
    /**This method is responsible for adding a new client
     * @param clientHandler is a new client connecting*/
    public static void addClient(ClientHandler clientHandler)
    {
        lock.lock();
        clientHandlers.add(clientHandler);
        notifyOtherPlayers(new JoinedGame(clientHandler.username));
        checkGamePlay();
        lock.unlock();
    }

    //places the assignment card on the board(machine)
    public static void placeAssignmentCard(Assignment assignment,int row,int col){

    }
    //selects any player to be the starting player
    public static void selectStartingPlayer(){
       clientHandlers.stream().findAny().ifPresent(clientHandler->clientHandler.isPlaying=true);
        List<ClientHandler> cur=clientHandlers.stream().filter(clientHandler -> clientHandler.isPlaying=true).collect(Collectors.toList());
        ClientHandler playing=cur.get(0) ;
       notifyOtherPlayers(new PlayerStarting(playing.username));
    }
    //Sends a message to other clients when a new client joins
    public static void notifyOtherPlayers(Message message){
        clientHandlers.forEach(clientHandler -> clientHandler.sendMessage(message));
    }

    public static void changePlayer(){
        if(clientHandlers.get(0).isPlaying)
        {
            ClientHandler cur=clientHandlers.get(1);
            cur.isPlaying=true;
            clientHandlers.set(1,cur);
            clientHandlers.forEach(clientHandler -> {
                if(!clientHandler.username.equals(cur.username)){
                    clientHandler.isPlaying=false;
                }
            });
            notifyOtherPlayers(new ChangeTurn(cur.username));
        }

        else if(clientHandlers.get(1).isPlaying){
            ClientHandler cur=clientHandlers.get(2);
            cur.isPlaying=true;
            clientHandlers.set(2,cur);
            clientHandlers.forEach(clientHandler -> {
                if(!clientHandler.username.equals(cur.username)){
                    clientHandler.isPlaying=false;
                }
            });
            notifyOtherPlayers(new ChangeTurn(cur.username));
        }
      /**  else if(clientHandlers.get(2).isPlaying){
            ClientHandler cur=clientHandlers.get(3);
            cur.isPlaying=true;
            clientHandlers.set(3,cur);
            clientHandlers.forEach(clientHandler -> {
                if(!clientHandler.username.equals(cur.username)){
                    clientHandler.isPlaying=false;
                }
            });
        }
        else {
            //back to one again
            ClientHandler cur=clientHandlers.get(1);
            cur.isPlaying=true;
            clientHandlers.set(1,cur);
            clientHandlers.forEach(clientHandler -> {
                if(!clientHandler.username.equals(cur.username)){
                    clientHandler.isPlaying=false;
                }
            });
        }*/
    }

    public static void sendNumberOfPlayersOnline()
    {
        clientHandlers.forEach(clientHandler -> clientHandler.sendMessage(new SendPlayerCount(clientHandlers.size())));
        System.out.println("Players online= "+clientHandlers.size());
    }
    //Removes a player from a list of the list of clients,
    public static void leaveGame(ClientHandler clientHandler){
        lock.lock();
        clientHandler.disconnect();
        clientHandlers.remove(clientHandler);
        notifyOtherPlayers(new PlayerLeft(clientHandler.username));
        lock.unlock();
    }
    /**Checks if the game is ready to start,if its ready it sends a start message to the client application to automatically start game*/
    private static void checkGamePlay()
    {
        if(clientHandlers.size()==4){
            try{
                //sendMessage to tell game will be starting
                Thread.sleep(20000); //delay by 20 seconds to start the game
                notifyOtherPlayers(new StartPlay()); //notify client application to start game
                Thread.sleep(10000); //delay by 10 seconds
                selectStartingPlayer(); //select the starting player
            }catch (Exception e){
                System.out.println("Error in thread sleep"+ e.getMessage());
            }
        }
    }
}
