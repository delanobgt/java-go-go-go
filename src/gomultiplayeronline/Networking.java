package gomultiplayeronline;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Networking {

    public static String getFancyRoomCode(String namePrefix) {
        String IP = getOwnIPAddress(namePrefix);
        if (IP == null) return null;
        return toFancyRoomCode(IP);
    }

    public static String getOwnIPAddress(String namePrefix) {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                String name = ni.getName();
                if (name.startsWith(namePrefix)) {
                    Enumeration<InetAddress> ias = ni.getInetAddresses();
                    String candidateIP = ias.nextElement().getHostAddress();
                    if (candidateIP.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
                        System.out.println(candidateIP);
                        return candidateIP;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
        return null;
    }

    private static String toFancyRoomCode(String IP) {
        String[] tokens = IP.split("\\.");
        String[] codes = new String[4];
        for (int i = 0; i < codes.length; i++) {
            int decimal = Integer.parseInt(tokens[i]);
            String hex = Integer.toHexString(decimal);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            codes[i] = hex;
        }
        String roomCode = codes[0] + codes[1] + "-" + codes[2] + codes[3];
        return convertToAlphabets(codes[0] + codes[1] + "-" + codes[2] + codes[3]);
    }

    private static String convertToAlphabets(String hex) {
        StringBuilder sb = new StringBuilder();
        for (char ch : hex.toCharArray()) {
            if ('0' <= ch && ch <= '9') {
                sb.append((char) ('G' + (ch - '0')));
            } else {
                sb.append(Character.toUpperCase(ch));
            }
        }
        return sb.toString();
    }

    public static String toTraditionalIPAddress(String roomCode) {
        if (roomCode.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
            return roomCode;
        }
        roomCode = roomCode.toUpperCase();
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

    private static String convertToHex(String alphabets) {
        StringBuilder sb = new StringBuilder();
        for (char ch : alphabets.toCharArray()) {
            if (ch >= 'G') {
                sb.append((char) ('0' + (ch - 'G')));
            } else {
                sb.append(Character.toUpperCase(ch));
            }
        }
        return sb.toString();
    }
}
