package gomultiplayeronline;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import main.GoMainFrame;
import models.RoomModel;

public class RoomInfoServer {
    
    private ServerSocket server = null;
    private RoomModel roomModel;
    
    public RoomInfoServer(RoomModel roomModel) {
        this.roomModel = roomModel;
    }
    
    private Thread listeningThread = new Thread(() -> {
        try {
            while (true) {
                Socket socket = server.accept();
                new ServingThread(socket, roomModel).start();
            }   
        } catch (Exception ex) {
            System.out.println(ex);
        }
    });
    
    public void startServer() {
        if (server == null) {
            try {
                server = new ServerSocket(GoMainFrame.ROOM_INFO_SERVER_PORT);
                listeningThread.start();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
 
    public void stopServer() {
        try {
            server.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}

class ServingThread extends Thread {
    
    private Socket socket;
    private ObjectOutputStream oos;
    private RoomModel roomModel;
    
    public ServingThread(Socket socket, RoomModel roomModel) {
        this.socket = socket;
        this.roomModel = roomModel;
    }

    @Override
    public void run() {
        try {            
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(roomModel);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                oos.close();
                socket.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
