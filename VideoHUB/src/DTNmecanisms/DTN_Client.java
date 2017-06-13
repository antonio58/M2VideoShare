package DTNmecanisms;

import ClientSide.ServerComm;
import Network.Frame;

import java.io.IOException;
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
    private ArrayList<String> obu = new ArrayList<>();

    public DTN_Client(){
    }

    public DTN_Client(String host, int port) throws IOException {
        if(serverComm.connectToServer(host, port)) {
            frame = new Frame(serverComm.getDos(), serverComm.getDis());
        }else{
            System.out.println("Can't connect do DTN node, try network search again");
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

    public ArrayList DiscoverOBU() {
        int timeout = 1000;
        for (int i = 1; i < 255; i++) {
            String host = "2001:690:2280:82a::" + i;
            try {
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    obu.add(host);
                }
            } catch (IOException e) {
                e.printStackTrace();
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
