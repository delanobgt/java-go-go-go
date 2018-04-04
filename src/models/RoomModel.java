package models;

import enums.BoardSize;
import enums.Player;
import java.io.Serializable;

public class RoomModel implements Serializable {
    
    private String roomOwnerName;
    private BoardSize boardSize;
    private Player playerType;

    public RoomModel(String roomOwnerName, BoardSize boardSize, Player playerType) {
        this.roomOwnerName = roomOwnerName;
        this.boardSize = boardSize;
        this.playerType = playerType;
    }

    public String getRoomOwnerName() {
        return roomOwnerName;
    }
    public BoardSize getBoardSize() {
        return boardSize;
    }
    public Player getPlayerType() {
        return playerType;
    }
    
    public String toString() {
        return String.format("<html> Owner: %s<br> Owner Player Type: %s<br> Board Size: %d<br></html>",
                roomOwnerName,
                playerType.toString(),
                boardSize.size()
        );
    }
}
