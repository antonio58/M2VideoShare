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

    private String talk(String message){
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

    private String buildFrame(byte type, String[] fields){

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
        /*

        byte[] ubl = ByteBuffer.allocate(4).putInt(user.length()).array();
        byte[] pbl = ByteBuffer.allocate(4).putInt(pass.length()).array();
        byte[] ebl = ByteBuffer.allocate(4).putInt(email.length()).array();

        byte[] header = new byte[13];
        header[0] = (byte) 4;
        for (int i = 1; i < 13; i++) {
            if (i < 5) {
                header[i] = ubl[i - 1];
            } else if(i < 9){
                header[i] = pbl[i - 5];
            } else
                header[i] = ebl[i-9];
        }
        String aux = user + pass + email;
        byte[] payload = aux.getBytes();

        byte[] packet = new byte[payload.length + header.length];

        for (int i = 0; i < payload.length + header.length; i++) {
            if (i < header.length) {
                packet[i] = header[i];
            } else {
                packet[i] = payload[i - header.length];
            }
        }
        String message = new String(packet);

        message = message.concat("<!end!>");*/

        String reply = talk(frame);

        if (reply.equals("check_4")){
            System.out.println("true dat");
            return true;
        }
        System.out.println("shiet");
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
        /*
        String data = new String();

        System.out.println("Decode string");
        byte[] r = str.getBytes(StandardCharsets.UTF_8);
        int total = 5;
        /*int nVid = r[0];
        System.out.println("nVid: "+nVid);*/
/*
        byte[] bnVid = new byte[4];
        for(int i = 0; i<4; i++){
            bnVid[i] = r[i];
        }

        int nVid = ByteBuffer.wrap(bnVid).getInt();
        System.out.println("nVid: "+nVid);

        for(int j = 0; j<nVid; j++){

            byte[] t = new byte[4];
            for (int i = total; i < total+4; i++) {
                t[i - total] = r[i];
                //System.out.println(i + "u" + r[i]);
            }
            total += 4;
            byte[] a = new byte[4];
            for (int i = total; i < total+4; i++) {
                a[i - total] = r[i];
                //System.out.println(i + "p" + r[i]);
            }

            int tl = ByteBuffer.wrap(t).getInt();
            int al = ByteBuffer.wrap(a).getInt();
            total += 4;

            System.out.println("n: " + tl + " / " + al);

            System.out.print("title: ");
            byte[] tb = new byte[tl];
            for (int i = total; i < total + tl; i++) {
                tb[i - total] = r[i];
                System.out.print((char) r[i]);
            }
            total += tl;
            System.out.println("\nauthor: ");
            byte[] ab = new byte[al];
            for (int i = total; i < total + al; i++) {
                ab[i - (total)] = r[i];
                System.out.print((char) r[i]);
            }
            total += al;


            String title = new String(tb);
            String author = new String(ab);

            str = title+"</split/>"+author;
            System.out.println("String"+j+": "+str);

            data = data.concat(str+"!:split:!");
            total++;
        }


        System.out.println("sc.data: "+data);
        return data;*/
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

