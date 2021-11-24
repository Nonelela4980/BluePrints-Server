package com.example.capstone.messages.server;
import com.example.capstone.Message;
/**Used when the server sends the number of players on the lobby*/
public class SendPlayerCount extends Message {
    private static final long serialVersionUID=55L;
    int count;
    public SendPlayerCount(int player_count)
    {
        this.count=player_count;
    }
    @Override
    public String toString() {
        return String.format("%s",count);
    }
}
