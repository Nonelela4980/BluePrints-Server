package com.example.capstone;
import com.example.capstone.messages.server.JoinedGame;
import com.example.capstone.messages.server.PlayerLeft;
import com.example.capstone.messages.server.SendPlayerCount;
import com.example.capstone.messages.server.StartPlay;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
/**This class acts as a broker, it handles the communication between the server and a client
 * Responsible for sending messages from a client to other clients
 * */
public class GameController
{
    private static final ReentrantLock lock=new ReentrantLock();
    private static List<ClientHandler> clientHandlers=new ArrayList<>(); //stores the list of connected players
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
    //Sends a message to other clients when a new client joins
    public static void notifyOtherPlayers(Message message){
        clientHandlers.forEach(clientHandler -> clientHandler.sendMessage(message));
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
        if(clientHandlers.size()==2){
            try{
                //sendMessage to tell game will be starting
                Thread.sleep(20000); //delay by 20 seconds to start the game
                notifyOtherPlayers(new StartPlay()); //notify client application to start game
            }catch (Exception e){
                System.out.println("Error in thread sleep"+ e.getMessage());
            }
        }
    }
}
