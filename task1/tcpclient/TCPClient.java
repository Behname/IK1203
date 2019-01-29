package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {

     public static String askServer(String hostname, int port, String ToServer) throws  IOException {

         if (ToServer == null)
             return askServer(hostname,port);
         else {

             Socket S = new Socket(hostname, port);
             S.setSoTimeout(11050);
             BufferedReader InB = new BufferedReader(new InputStreamReader(S.getInputStream()));
             DataOutputStream outToServer = new DataOutputStream(S.getOutputStream());
             outToServer.writeBytes(ToServer + '\n');
             String modifiedToServer = InB.readLine();
             StringBuilder modified = new StringBuilder();

             try {


                 while (modifiedToServer != null) {
                     modified.append(modifiedToServer);
                     modifiedToServer = InB.readLine();
                 }


             }
             catch(java.net.SocketTimeoutException e){
                 return modified.toString();
             }
                S.close();
                return modified.toString();
         }
    }

    public static String askServer(String hostname, int port) throws  IOException {
        Socket S = new Socket(hostname,port);
        S.setSoTimeout(250);
        BufferedReader InB = new BufferedReader(new InputStreamReader(S.getInputStream()));
        String line = InB.readLine();
        StringBuilder modified = new StringBuilder();

        try {
            while (line != null) {
                if(modified.length() > 19799)
                    break;
                else {
                    modified.append(line);
                    line = InB.readLine();
                }
            }
        }
        catch(java.net.SocketTimeoutException e){
            return modified.toString();
        }

        S.close();
        return modified.toString();
    }
}