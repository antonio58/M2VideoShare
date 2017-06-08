package DTNmecanisms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 07-06-2017.
 */
public class DTN_Client {
    int port = 3333;
    Socket socket = null;
    String host = "::1";
    String outp;
    DataOutputStream dos;
    DataInputStream dis;

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

    public String talk(String message){
        String reply = "";
        try {
            dos.writeUTF(message);
            //while(reply.equals("")) {
            reply = dis.readUTF();
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("message: "+message);
        System.out.println("reply: "+reply);

        return reply;
    }

    public String buildFrame(byte type, String[] fields){

        int nFields = fields.length;
        int headerPointer = 1;
        byte[] header = new byte[(nFields*4)+5];

        header[0] = type;

        byte[] nFb = ByteBuffer.allocate(4).putInt(nFields).array();
        for(int i = 0; i<4; i++){
            header[headerPointer] = nFb[i];
            headerPointer++;
        }

        for(int i = 0; i < nFields; i++){
            String str_aux = fields[i];
            byte[] fieldLength = ByteBuffer.allocate(4).putInt(str_aux.length()).array();
            for(int j = 0; j < 4; j++){
                header[headerPointer] = fieldLength[j];
                headerPointer++;
            }
        }

        String frame = new String(header);

        for(int i = 0; i < nFields; i++){
            frame = frame.concat(fields[i]);
        }

        frame = frame.concat("<!end!>");

        return frame;
    }

    public List<String> readFrame(String s){
        List<String> fields = new ArrayList<>();
        List<Integer> sizeFields = new ArrayList<>();

        //Turns frame into byte array
        byte[] frameB = s.getBytes(StandardCharsets.UTF_8);
        int framePointer = 1;

        //Gets integer representing the number of fields in the frame
        byte[] numFieldsB = new byte[4];
        for(int i = 0; i<4; i++){
            numFieldsB[i] = frameB[framePointer];
            framePointer++;
        }
        int numFields = ByteBuffer.wrap(numFieldsB).getInt();
        System.out.println("readFreame133: "+numFields);

        //Gets the size of each field in the frame
        for(int i=0; i < numFields; i++){
            byte[] aux = new byte[4];
            for(int k = 0; k < 4; k++){
                aux[k] = frameB[framePointer];
                framePointer++;
            }
            int esse = ByteBuffer.wrap(aux).getInt();
            System.out.println("Fields Size "+i+": "+esse);
            sizeFields.add(esse);

        }


        //Gets frame fields
        for(int i : sizeFields){
            byte[] aux = new byte[i];
            for(int j = 0; j<i; j++){
                aux[j] = frameB[framePointer];
                framePointer++;
            }
            String temp = new String(aux);
            fields.add(temp);
        }

        System.out.println("(readFrame)Fields: ");
        for(String str : fields)
            System.out.println(str);

        return fields;
    }

    //connects to DTN server. TODO icmp flood for available DTN Servers!
    public boolean connectToDTNServer() throws IOException {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        //BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        dos = new DataOutputStream(
                socket.getOutputStream());
        dis = new DataInputStream(
                socket.getInputStream());
        outp = dis.readUTF();
        System.out.println(outp);
        return true;
    }

    //sends streaming resume request to DTN server
    public boolean resumeStreaming(){
        String[] fields = {"resume"};
        String frame = buildFrame((byte)4, fields);

        String reply = talk(frame);

        if (reply.equals("check_resume")){
            System.out.println("Profile updated");
            return true;
        }
        //System.out.println("");
        return false;


    }
}
