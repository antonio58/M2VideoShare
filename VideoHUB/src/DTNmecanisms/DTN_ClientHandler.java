package DTNmecanisms;

import Network.Frame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by fernando on 07-06-2017.
 */

public class DTN_ClientHandler implements Runnable {
    private static String TAG = "DTN_ClientHandler";
    private Socket socketClient;
    private int clientNumber;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String response;
    private Frame frame;

    public DTN_ClientHandler(Socket socketClient, int clientNumber) {
        this.socketClient = socketClient;
        this.clientNumber = clientNumber;
        System.out.println("\n New Client:\n"
                + socketClient.getInetAddress() + " || " + socketClient.getPort() + "\n");

    }

    @Override
    //Inicia a comunicação com o cliente, recebe confirmação de que era o cliente perdido (e algumas credenciais ?? - segurança), inicia a retransmissão do vídeo interrompido
    //também poderá acertar com o cliente o último chunk recebido com sucesso;
    public void run() {
        try {
            dos = new DataOutputStream(
                    socketClient.getOutputStream());
            dis = new DataInputStream(
                    socketClient.getInputStream());
            frame = new Frame(dos,dis);
            dos.writeUTF("Welcome Lost Peer number " + clientNumber);
            dos.flush();
            while (true) {
                dos.flush();
                response = "";
                boolean flag = true;
                while (flag) {
                    String part = dis.readUTF();
                    System.out.println("Welcome Lost Peer number " + clientNumber + " \n->Message Received: " + part);
                    response = response.concat(part);
                    //a mensagem terá de ser um pedido por parte do cliente e confirmar o reatar do streaming
                    if (part.indexOf("<!end!>") == part.length() - 7) {
                        flag = false;
                    }
                }
                //dos.writeUTF("Message Received");
                char b = response.charAt(0);

                switch (b) {
                    case 1:
                        if (resumeStream(response))
                            dos.writeUTF("check_resume");
                        else
                            dos.writeUTF("failed resume");
                        break;
                    case 2:
                        if (clearBufferOfStream(response))
                            dos.writeUTF("clear_stream");
                        else
                            dos.writeUTF("failed buffer cleanse");
                        break;
                    case 3:
                        if (clearBuffer(response))
                            dos.writeUTF("clear_streams");
                        else
                            dos.writeUTF("failed buffer cleanse");
                        break;
                }
                dos.flush();


            }
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    //resume Stream Ticket handling
    private boolean resumeStream(String response){
        //System.out.println("resumeStream frame: " + response);
        List<String> fields = frame.readFrame(response);

        for(int i = 0; i<fields.size(); i++) {
            System.out.println(TAG + " resumeStream field: " + fields.get(i));
        }

        //search for asked video, and start writing data to client socket
        return true;
    }

    //clear buffer of said streamId only the main server sets this or by timeout or streaming
    private boolean clearBufferOfStream(String response) {
        return true;
    }

    //clear buffer of all streams
    private boolean clearBuffer(String response){return true;}
}
