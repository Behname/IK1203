import java.net.*;
import java.io.*;

public class ConcHTTPAsk {
    public static void main(String[] args) throws IOException {
        try {
            int port = Integer.parseInt(args[0]);
            ServerSocket Server = new ServerSocket(port);

            try {
                while (true) {
                    Socket Client = Server.accept();
                    MyRunnable socketX = new MyRunnable(Client);
                    new Thread(socketX).start();
                }
            } catch (java.io.IOException e) {
            }
        } catch (java.io.IOException e) {
        }
    }
}

