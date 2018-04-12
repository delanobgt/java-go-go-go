import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;
 
 
public class Tester {
   
    public static void main(String[] args) throws Exception {
       
        Enumeration<NetworkInterface> itr = NetworkInterface.getNetworkInterfaces();
       
        while (itr.hasMoreElements()) {
           
            System.out.println("---START---");
           
            NetworkInterface e = itr.nextElement();
            System.out.println(e.getDisplayName());
            System.out.println(e.getName());
            Enumeration<InetAddress> ia = e.getInetAddresses();
           
            while (ia.hasMoreElements()) {
                InetAddress cur = ia.nextElement();
                System.out.println(";p;" + cur.getHostAddress());
            }
           
            System.out.println("----END----");System.out.println("");System.out.println("");
           
        }
    }
   
}