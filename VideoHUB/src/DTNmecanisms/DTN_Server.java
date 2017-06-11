package DTNmecanisms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fernando on 07-06-2017.
 *
 * TODO Decidir se esta atividade é iniciada logo ou à receção de um aviso por parte do servidor
 */
public class DTN_Server {

    public DTN_Server(){}

    //Received "DTNetwork peer loss, his chunks are going for your by DTN orders, as soon as there is a connected mate, retransmit as fast as you can ;)"
    public void activateNode(){
        final String TAG = "DTNode";
        int clientNumber = 0;

        System.out.println(TAG + " server running...");
        try {
            ServerSocket socketServer = new ServerSocket(3333);
            System.out.println("\nWaiting for lost peers to connect");
            while (true) {
                Socket socket = socketServer.accept();

                //wait for lost peer connection. Retransmit strain of stream data
                DTN_ClientHandler query = new DTN_ClientHandler(socket, clientNumber);
                Thread client = new Thread(query);
                client.start();
                clientNumber++;
            }
        } catch (IOException e) {
            System.out.println("DTN Server Error");
        }
    }
}
