
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
    
    public static void main(String[] args) {
//        RoomModel roomModel = new RoomModel("Irvin", BoardSize.LARGE, Player.BLACK);
//        RoomInfoServer roomInfoServer = new RoomInfoServer(roomModel);
//        roomInfoServer.startServer();
//        
//        RoomModel rm = new RoomInfoClient(
//                "192.168.187.1", 
//                GoMainFrame.ROOM_INFO_SERVER_PORT
//        ).getRoomModel();
//        System.out.println(rm.getBoardSize()+rm.getRoomOwner());
//        
//        roomInfoServer.stopServer();
        
    }
    
    public static void findAllConnectedIP() {
        int timeout=500;
        int port = 1234;

        try {
            String currentIP = InetAddress.getLocalHost().toString();
            System.out.println(currentIP);
            String subnet = getSubnet(currentIP);
            System.out.println("subnet: " + subnet);

            for (int i=1;i<254;i++){

                String host = subnet + i;
                System.out.println("Checking :" + host);

                if (InetAddress.getByName(host).isReachable(timeout)){
                    System.out.println(host + " is reachable");
                    try {
                        Socket connected = new Socket(subnet, port);
                    }
                    catch (Exception s) {
                        System.out.println(s);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static String getSubnet(String currentIP) {
        int firstSeparator = currentIP.lastIndexOf("/");
        int lastSeparator = currentIP.lastIndexOf(".");
        return currentIP.substring(firstSeparator+1, lastSeparator+1);
    }
}
