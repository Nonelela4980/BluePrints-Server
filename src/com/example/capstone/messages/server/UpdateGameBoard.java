package com.example.capstone.messages.server;
import com.example.capstone.Message;
/**This message is sent to clients to update the game board for all the players*/
public class UpdateGameBoard extends Message {
    private static final long serialVersionUID = 25L;
    int drawable;
    int cardID;

    public UpdateGameBoard(int cardID,int drawable) {
        this.cardID=cardID;
        this.drawable = drawable;
    }
    @Override
    public String toString()
    {
        return String.format("cardID=%s drawable=%s",cardID,drawable);
    }
}
