
import enums.BoardSize;
import enums.Player;
import gomultiplayeronline.RoomInfoClient;
import gomultiplayeronline.RoomInfoServer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import main.GoMainFrame;
import models.RoomModel;


public class Tester {
    
    public static void main(String[] args) throws Exception {
        RoomModel roomModel = new RoomModel("Oomomommmo", BoardSize.LARGE, Player.BLACK);
        RoomInfoServer roomInfoServer = new RoomInfoServer(roomModel);
        roomInfoServer.startServer(); 
        
        RoomModel rm = new RoomInfoClient(
                "192.168.187.1", 
                GoMainFrame.ROOM_INFO_SERVER_PORT
        ).getRoomModel();
        System.out.println(rm.getBoardSize()+rm.getRoomOwnerName());
        
//        roomInfoServer.stopServer();
    }
}
