import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private static List<ClientHandler> clientHandlers=new ArrayList<>();
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;
    public ClientHandler(Socket socket,String username) {
        try{
            this.socket = socket;
            this.username=username;
            out=new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
            clientHandlers.add(this);
            sendMessage("SERVER>>>"+this.username+" Entered the game");
            sendMessage("Waiting for other players...");
        }catch (IOException e){
            System.out.println("Error :"+e.getMessage());
        }
    }

    @Override
    public void run() {
        String messageClient;
        while (true){
            try {
                messageClient= (String) in.readObject();
                sendMessage(messageClient);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error: "+e.getMessage());
                break;
            }
        }
    }
    public void sendMessage(String message) {
        //send Message to each and every client
        for(ClientHandler clientHandler:clientHandlers){
            if(clientHandler!=null){
                try{
                    clientHandler.out.writeObject(message);
                    clientHandler.out.flush();
                    System.out.println("Send to clients");
                    //if(!clientHandler.username.equals(username)){
                    // }
                }catch (IOException e){
                    System.out.println("Error:"+e.getMessage());
                }
            }
        }
    }
}
