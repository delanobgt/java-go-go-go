package gomultiplayeronline;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameSocket {

    private Socket socket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    
    public GameSocket(Socket socket) {
        this.socket = socket;
        initStreams();
    }
    
    public GameSocket(String IP, int port) {
        try {
            this.socket = new Socket(IP, port);
            initStreams();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void initStreams() {
        try {
            this.os = new ObjectOutputStream(socket.getOutputStream());
            this.is = new ObjectInputStream(socket.getInputStream());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public boolean send(Object obj) {
        try {
            os.writeObject(obj);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
    
    public Object receive() {
        try {
            return is.readObject();
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public void close() {
        try {
            is.close();
            os.close();
            socket.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
