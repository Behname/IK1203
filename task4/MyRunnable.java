import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyRunnable implements Runnable {
    private Socket Client;

    public MyRunnable(Socket socket) {
        this.Client = socket;
    }

    public void run() {
        try {
            StringBuilder Character = new StringBuilder();
            BufferedReader din = new BufferedReader(new InputStreamReader(Client.getInputStream()));
            Client.setSoTimeout(5000);
            DataOutputStream dout = new DataOutputStream(Client.getOutputStream());
            String QtoServer = din.readLine();


            try {
                while (QtoServer != null && QtoServer.length() != 0) {
                    Character.append(QtoServer + "\n");
                }

                String host = "";
                int port1 = 0;
                String query = "";
                String[] string1 = Character.toString().split("\\s|\\?|=|&|=");
                for (int i = 0; i < string1.length; i++) {
                    if (string1[i].equals("hostname")) {
                        host = string1[i + 1];
                    } else if (string1[i].equals("port")) {
                        port1 = Integer.parseInt(string1[i + 1]);
                    }
                    if (string1[i].equals("string")) {
                        query = string1[i + 1];
                    }
                }

                try {
                    String ClientTCP = TCPClient.askServer(host, port1, query);
                    String AnswerFromServer = "HTTP/1.1 200 OK" + "\r\n"
                            + "Content-type: text/plain" + "\r\n"
                            + "Content-length" + ClientTCP.length() + "\r\n\r\n";

                    dout.writeBytes(AnswerFromServer + ClientTCP + "\n");

                    Client.close();

                } catch (java.lang.NumberFormatException e) {
                }

            } catch (java.io.IOException e) {
                System.out.println("Error IOException");
            }

        } catch (java.io.IOException e) {
            System.out.println("Error IOException");
        }
    }

        public static class TCPClient {

            public static String askServer(String hostname, int port, String ToServer) throws IOException {

                if (ToServer == null)
                    return askServer(hostname, port);
                else {

                    Socket Server = new Socket(hostname, port);
                    Server.setSoTimeout(5000);
                    BufferedReader din = new BufferedReader(new InputStreamReader(Server.getInputStream()));
                    DataOutputStream dout = new DataOutputStream(Server.getOutputStream());
                    dout.writeBytes(ToServer + '\n');
                    String QuestionToServer = din.readLine();
                    StringBuilder Answer = new StringBuilder();

                    try {
                        while (QuestionToServer != null) {
                            Answer.append(QuestionToServer);
                            QuestionToServer = din.readLine();
                        }


                    } catch (SocketTimeoutException e) {
                        return Answer.toString();
                    }
                    Server.close();
                    return Answer.toString();
                }
            }

            public static String askServer(String hostname, int port) throws IOException {
                Socket Server = new Socket(hostname, port);
                Server.setSoTimeout(5000);
                BufferedReader din = new BufferedReader(new InputStreamReader(Server.getInputStream()));
                String Character = din.readLine();
                StringBuilder Answer = new StringBuilder();

                try {
                    while (Character != null) {
                        if (Answer.length() > 15000)
                            break;
                        else {
                            Answer.append(Character);
                            Character = din.readLine();
                        }
                    }
                } catch (SocketTimeoutException e) {
                    return Answer.toString();
                }

                Server.close();
                return Answer.toString();
            }
        }
    }