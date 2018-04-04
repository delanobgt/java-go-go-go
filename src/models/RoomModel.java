package models;

import enums.BoardSize;
import enums.Player;
import java.io.Serializable;

public class RoomModel implements Serializable {
    
    private String roomOwner;
    private BoardSize boardSize;
    private Player playerType;

    public RoomModel(String roomOwner, BoardSize boardSize, Player playerType) {
        this.roomOwner = roomOwner;
        this.boardSize = boardSize;
        this.playerType = playerType;
    }

    public String getRoomOwner() {
        return roomOwner;
    }
    public BoardSize getBoardSize() {
        return boardSize;
    }
    public Player getPlayerType() {
        return playerType;
    }
}
