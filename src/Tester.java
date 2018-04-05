
import enums.BoardSize;
import enums.Player;
import gomultiplayeronline.RoomInfoClient;
import gomultiplayeronline.RoomInfoServer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import main.GoMainFrame;
import models.RoomModel;


public class Tester {
    
    public static void main(String[] args) throws Exception {
//        Socket socket = new Socket("169.254.70.163", 6667);
//        System.out.println(socket);

        System.out.println(InetAddress.getLocalHost().toString());
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        ServerSocket server = new ServerSocket(6667);
        Socket client = server.accept();
        System.out.println(client);

    }
}
