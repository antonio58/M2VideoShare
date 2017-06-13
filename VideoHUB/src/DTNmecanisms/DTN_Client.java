package DTNmecanisms;

import ClientSide.ServerComm;
import Network.Frame;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by fernando on 07-06-2017.
 *
 * This Class is for all peers that lost streaming connection with main server. Also used for main server to communicate with DTN server Nodes.
 */
public class DTN_Client {
    private ServerComm serverComm = new ServerComm();
    private Frame frame = new Frame();

    public DTN_Client() throws IOException {
        ArrayList<String> obu;
        System.out.printf("entrou no dtn node");
        Boolean connection = false;
        while(!connection) {
            obu = DiscoverOBU();
            System.out.println("List OBU: " + obu);

            int i = 0;
            while (!connection || i == obu.size()) {
                connection = Connection(obu.get(i), 3333);
                System.out.println("OBU a ver: " + obu.get(i) + "conectado: " + connection);
                i++;
            }
        }
    }

    public String[] readFile() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/home/luisf99/Documentos/UniversidadeMinho/ProjetodeTelecomunicacoesInformatica2/Videos/parametros.txt"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return line.split(" ");
    }

    private Boolean Connection(String host, int port) throws IOException {
        if(serverComm.connectToServer(host, port)) {
            frame = new Frame(serverComm.getDos(), serverComm.getDis());
            return true;
        }else{
            System.out.println("Can't connect do DTN node, try network search again");
            return false;
        }
    }

    /**TICKETs*/

    //ticket for streaming resume
    public boolean resumeStreaming(String videoId, String chunk){
        String[] fields = {"resume", videoId, chunk};
        String ticket = frame.buildFrame((byte)1, fields);

        String reply = frame.talk(ticket);

        if (reply.equals("check_resume")){
            System.out.println("Resuming video streaming");
            return true;
        }
        //System.out.println("");
        return false;
    }

    private ArrayList<String> DiscoverOBU() {
        ArrayList<String> obu = new ArrayList<>();
        int timeout = 1000;
        for (int i = 1; i < 255; i++) {
            String host = "2001:690:2280:82a::" + i;
            try {
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    obu.add(host);
                }
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        return obu;
    }
    

    /**TICKETS for main SERVER*/
    //Sends to DTN node server pending video stream, for posterior retransmission to a lost node
    public boolean sendPendingVideo(){
        //store chunks in temp DB of DTN server Node
        return true;
    }




}
