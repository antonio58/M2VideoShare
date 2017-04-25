package ServerSide;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by fernando on 01-04-2017.
 */

public class ServerClientHandler implements Runnable {
    private Socket socketClient;
    private int clientNumber;
    private String response;

    public ServerClientHandler(Socket socketClient, int clientNumber) {
        this.socketClient = socketClient;
        this.clientNumber = clientNumber;
        System.out.println("\n New Client:\n"
                + socketClient.getInetAddress() + " || " + socketClient.getPort()+"\n");

    }

    // @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(
                    socketClient.getOutputStream());
            DataInputStream dis = new DataInputStream(
                    socketClient.getInputStream());

            dos.writeUTF("Welcome Client number " + clientNumber);
            dos.flush();
            while(true) {
                dos.writeUTF("\nWrite something");
                dos.flush();
                response = dis.readUTF();
                System.out.println("Message Received: " + response);

                dos.writeUTF("Message Received");
                dos.flush();
            }  } catch (IOException e) {
            e.printStackTrace();
        }

    }
}