package DTNmecanisms;

import Network.Frame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by fernando on 07-06-2017.
 */
public class DTN_Client {
    private int port = 3333;
    private Socket socket = null;
    private String host = "::1";
    private String outp;
    private DataOutputStream dos;
    private DataInputStream dis;
    private Frame frame = new Frame();

    public DTN_Client(){
        int port = 3333;
        Socket socket = null;
        String host = "::1";
        dos = new DataOutputStream(null);
        dis = new DataInputStream(null);
    }

    public DTN_Client(int port, Socket socket, String host, String outp, DataOutputStream dos, DataInputStream dis) {
        this.port = port;
        this.socket = socket;
        this.host = host;
        this.outp = outp;
        this.dos = dos;
        this.dis = dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public DataInputStream getDis() {
        return dis;
    }


    //connects to DTN server. TODO icmp flood for available DTN Servers!
    public boolean connectToDTNServer() throws IOException {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        dos = new DataOutputStream(
                socket.getOutputStream());
        dis = new DataInputStream(
                socket.getInputStream());
        frame = new Frame(dos, dis);
                outp = dis.readUTF();
        System.out.println(outp);
        return true;
    }

    /**TICKETs*/
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
}
