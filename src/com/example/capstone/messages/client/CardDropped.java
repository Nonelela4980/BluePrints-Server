package com.example.capstone.messages.client;
import com.example.capstone.GameController;
import com.example.capstone.Message;
import com.example.capstone.messages.server.UpdateGameBoard;
/**Sent by a clients when they dropped and confirmed a card*/
public class CardDropped extends Message
{
    private static final long serialVersionUID = 5L;
    int drawable;
    int cardID;
    @Override
    public void applyLogic(Object context)
    {
        GameController.notifyOtherPlayers(new UpdateGameBoard(cardID,drawable));
    }
}
