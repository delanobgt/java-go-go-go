package gomultiplayeronline;

import java.io.ObjectInputStream;
import java.net.Socket;
import models.RoomModel;

public class RoomInfoClient {
    
    private String ip;
    private int port;
    private Socket socket;
    private ObjectInputStream ois;
    
    public RoomInfoClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        initSocket();
        initInputStream();
    }
    
    public RoomModel getRoomModel() {
        RoomModel roomModel = null;
        try {
            roomModel = (RoomModel)(ois.readObject());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return roomModel;
    }
    
    private void initSocket() {
        try {
            socket = new Socket(ip, port);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void initInputStream() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
