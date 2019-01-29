import java.net.*;
import java.io.*;

public class HTTPAsk {

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
                String[] Asplit = ServerString.split("GET /ask?hostname=");
                String[] Bsplit = ServerString.split("GET /ask?hostname=time.nist.gov&port=");
                String[] A2split= Asplit[1].split("&port=13 HTTP/1.1");
                String[] B2split = Bsplit[1].split(" HTTP/1.1");
                TCPClient.askServer(A2split[0], B2split[0]);
                csocket.setSoTimeout(5000);

                try {
                    while (ServerString != null) {
                        ModifiedString.append(ServerString + "\n");
                        ServerString = inFromClient.readLine();
                    }


                    // String[] SArray = ModifiedString.toString().split("hostname=");
                    //System.out.println(SArray[0]);
                    //System.out.println(ModifiedString.toString());
                    //ModifiedString.toString.split();
                    // Split();
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

