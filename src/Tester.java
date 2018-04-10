
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
    
    private static Scanner sc = new Scanner(System.in);
    
    private Tester() {
        while (true) {
            System.out.println(sc.next().matches("[A-P]{4}-[A-P]{4}"));
        }
    }
    
    public static void main(String[] args) {
        new Tester();
    }
    
    private String getOwnIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
    private String toFancyRoomCode(String IP) {
        String[] tokens = IP.split("\\.");
        String[] codes = new String[4];
        for (int i = 0; i < codes.length; i++) {
            int decimal = Integer.parseInt(tokens[i]);
            String hex = Integer.toHexString(decimal);
            if (hex.length() == 1) hex = "0"+hex;
            codes[i] = hex;
        }
        String roomCode = codes[0]+codes[1]+"-"+codes[2]+codes[3];
        return convertToAlphabets(codes[0]+codes[1]+"-"+codes[2]+codes[3]);
    }
    private String convertToAlphabets(String hex) {
        StringBuilder sb = new StringBuilder();
        for (char ch : hex.toCharArray()) {
            if ('0' <= ch && ch <= '9') {
                sb.append( (char)('G'+(ch-'0')) );
            } else {
                sb.append(Character.toUpperCase(ch));
            }
        }
        return sb.toString();
    }
    /////////
    private String toTraditionalIPAddress(String roomCode) {
        roomCode = convertToHex(roomCode);
        String[] tokens = roomCode.split("-");
        String[] hexes = {
            tokens[0].substring(0, 2),
            tokens[0].substring(2, 4),
            tokens[1].substring(0, 2),
            tokens[1].substring(2, 4)
        };
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexes.length; i++) {
            sb.append(String.format(
                    "%s%d",
                    i > 0 ? "." : "",
                    Integer.parseInt(hexes[i], 16)
            ));
        }
        return sb.toString();
    }
    private String convertToHex(String alphabets) {
        StringBuilder sb = new StringBuilder();
        for (char ch : alphabets.toCharArray()) {
            if (ch >= 'G') {
                sb.append( (char)('0'+(ch-'G')) );
            } else {
                sb.append(Character.toUpperCase(ch));
            }
        }
        return sb.toString();
    }
    
}
