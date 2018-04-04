package gomultiplayeronline;

import java.net.ServerSocket;
import java.net.Socket;
import main.GoMainFrame;

public class GameServer {
    
    private ServerSocket server;
    
    public GameServer() {
        try {
            server = new ServerSocket(GoMainFrame.GAME_SERVER_PORT);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public Socket waitForConnection() {
        try {
            return server.accept();
        } catch (Exception ex) {
            return null;
        } finally {
            try {
                server.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
