package ClientSide;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizador on 20/04/2017.
 */
public class ServerComm {

    int port = 3333;
    Socket socket = null;
    String host = "::1";
    String outp;
    DataOutputStream dos;
    DataInputStream dis;

    public ServerComm(){
        int port = 3333;
        Socket socket = null;
        String host = "::1";
        dos = new DataOutputStream(null);
        dis = new DataInputStream(null);
    }

    public ServerComm(int port, Socket socket, String host, String outp, DataOutputStream dos, DataInputStream dis) {
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

    public boolean connectToServer() throws IOException {
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

    public boolean checkLogin(String u, String p) throws UnsupportedEncodingException {

        String[] fields = {u,p};
        String message = buildFrame((byte)1, fields);

        System.out.println("Frame: "+message);

        String reply = talk(message);

        if (reply.equals("check_1")){
            System.out.println("true true");
            return true;
        }
        System.out.println("oh noooooo");
        return false;
    }

    public boolean registerUSer(String user, String password, String email){

        String[] fields = {user,password,email};
        String message = buildFrame((byte)2, fields);

        String reply = talk(message);

        if (reply.equals("check_2")){
            System.out.println("true dat");
            return true;
        }
        System.out.println("shiet");
        return false;
    }

    public String getUserData(){

        String[] aux = new String[0];
        String str = buildFrame((byte)3, aux);
        str = talk(str);

        if(str.equals("error: 404"))
            return str;

        System.out.println("getUserData201: "+str);

        List<String> fields = readFrame(str);

        String user = fields.get(0);//new String(userb);
        String email = fields.get(1);//new String (emailb);

        str = user+"--"+email;

        return str;
    }

    public boolean updateProfile(String user, String email, String pass){

        String[] fields = {user,pass,email};
        String frame = buildFrame((byte)4, fields);

        String reply = talk(frame);

        if (reply.equals("check_4")){
            System.out.println("Profile updated");
            return true;
        }
        //System.out.println("");
        return false;

    }

    public String getFeed(int p, byte feedType){

        String[] fields = {String.valueOf(p)};
        String str = buildFrame(feedType, fields);

        str = talk(str);

        if(str.equals("error: 404"))
            return str;
        if(str.equals("No videos"))
            return str;

        List<String> fieldList = readFrame(str);
        String info = "";
        int c = 0;
        for(String s : fieldList){
            System.out.println("field: "+s);
            info = info.concat(s);
            if(c%2 == 0)
                info = info.concat("</split/>");
            else
                info = info.concat("!:split:!");
            c++;
        }

        return info;
    }

    public String getSearchResults(String query){

        System.out.println("query1: "+query);

        String[] engodo = {query};
        String str = buildFrame((byte)7, engodo);
        str = talk(str);

        if(str.equals("error: 404"))
            return str;
        if(str.equals("No videos"))
            return str;

        List<String> fieldList = readFrame(str);
        String info = "";
        int c = 0;
        for(String s : fieldList){
            info = info.concat(s);
            if(c%2 == 0)
                info = info.concat("</split/>");
            else
                info = info.concat("!:split:!");
            c++;
        }

        return info;
    }
}

