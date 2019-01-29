import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main(String[] args) throws IOException{
        try {
            int port = Integer.parseInt(args[0]);
            ServerSocket ssocket = new ServerSocket(port);

            while (true) {
                StringBuilder ModifiedString = new StringBuilder();
                Socket csocket = ssocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
                DataOutputStream outToServer = new DataOutputStream(csocket.getOutputStream());
                String ServerString = inFromClient.readLine();
                csocket.setSoTimeout(5000);

                try {
                    while (ServerString != null) {
                        ModifiedString.append(ServerString + "\n");
                        ServerString = inFromClient.readLine();
                    }
                }
                catch(java.net.SocketTimeoutException e){

                }

                String echo = "HTTP/1.1 200 OK" + "\r\n"
                        + "Content-type: text/plain" + "\r\n"
                        + "Content-length" + ModifiedString.length() + "\r\n\r\n";

                outToServer.writeBytes(echo + ModifiedString.toString() + "\n");
                csocket.close();

            }

        }

        catch(java.io.IOException e){
            System.out.println("Error IOException");
        }

    }
}

