package DTNmecanisms;

import ClientSide.ServerComm;
import Network.Frame;
import ServerSide.ChunkTasks;
import org.bson.types.ObjectId;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by fernando on 07-06-2017.
 * Establishes lost connection with node that lost direct connection with main server. Tries to handle pending video streams by retransmitting as soon as a client connects
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
                    case 14:
                        try {
                            if(deliveryUpdate(response)){
                                dos.writeUTF("check_delivery");
                            }
                            else {
                                dos.writeUTF("failed delivery update");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
                dos.flush();


            }
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    /** client node TICKETS */
    //resumes streaming to connected client
    private boolean resumeStream(String response){
        //System.out.println("resumeStream frame: " + response);
        List<String> fields = frame.readFrame(response);

        for(int i = 0; i<fields.size(); i++) {
            System.out.println(TAG + " resumeStream field: " + fields.get(i));
        }

        //search for asked video and given chunk, and start writing data to client socket, similar to sendVideo() in ServerClientHandler
        return true;
    }

    /**Answers to Main server TICKETS**/
    //TODO Only in answer to VideoHub main server tickets
    private boolean storeVideoChunks(String response) throws IOException {
        List<String> fields = frame.readFrame(response);

        ObjectId videoId = new ObjectId("");
        ChunkTasks chunkTasks = new ChunkTasks(videoId);
        chunkTasks.fileToChunks("");
        //store to remote temp DB
        return true;
    }

    //TODO clear buffer of said streamId only the main server sets this or by timeout or finished transfer
    private boolean clearBufferOfStream(String s) {
        //clear mongoDB entry for a given video
        return true;
    }

    //TODO clear buffer of all streams
    private boolean clearBuffer(String s){
        //clear all entries
        return true;
    }

    //as soon as client ACKs conclusion of the stream, .this updates Main Server with the conclusion (to posterior Delivery Database table update)
    private boolean deliveryUpdate(String s) throws InterruptedException {
        //receives update from DTN client
        List<String> fields = frame.readFrame(s);

        String[] text = {"success"};
        String aux = frame.buildFrame((byte)1, text);

        boolean flag =true;

        while(flag) {
            ServerComm sc = new ServerComm();
            try {
                sc.connectToServer("2001:690:2280:827:200:ff:feaa:e"/*"::1"*/, 3333);
                if(sc.deliveryStateUpdate(new ObjectId(fields.get(0)), fields.get(1))){
                    flag = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Thread.sleep(10000);
            }
        }
        String reply = frame.talk(aux);

        if (reply.equals("check_4")){
            System.out.println("Delivery updated");
            return true;
        }
        return false;
    }

}
