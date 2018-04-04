
import java.io.IOException;
import java.net.InetAddress;

public class Ping {

    public static void main(String[] args) throws IOException {

        // Fetch local host
        InetAddress localhost = InetAddress.getLocalHost();

        // IPv4 usage
        byte[] ip = localhost.getAddress();

        // looping
        for (int i = 1; i <= 254; i++) {
            ip[3] = (byte) i;
            InetAddress address = InetAddress.getByAddress(ip);
            if (address.isReachable(1000)) {
                System.out.println(address + " - Pinging... Pinging");
            } else if (!address.getHostAddress().equals(address.getHostName())) {
                System.out.println(address + " - DNS lookup known..");
            } else {
                System.out.println(address + " - the host address and the host name are same");
            }
        }
    }
}
