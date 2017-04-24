package ServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fernando on 01-04-2017.
 */


public class Server {
    public static void main(String[] args) {
        final String TAG = "Class-Server";
        int clientNumber = 0;

        UserQueriesVideoHub queries = new UserQueriesVideoHub();
        System.out.println("\nServer Online "+ TAG);
        try {
            ServerSocket socketServer = new ServerSocket(3333);
            while (true) {
                Socket socket = socketServer.accept();

                ServerClientHandler query = new ServerClientHandler(socket, clientNumber);
                Thread client = new Thread(query);
                client.start();
                clientNumber++;
            }
        } catch (IOException e) {
            System.out.println("Server Error");
        }
    }
}
