package Client;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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

        byte[] ubl = ByteBuffer.allocate(4).putInt(u.length()).array();
        byte[] pbl = ByteBuffer.allocate(4).putInt(p.length()).array();

        byte[] header = new byte[9];
        header[0] = (byte) 1;
        for (int i = 1; i < 9; i++) {
            if (i < 5) {
                header[i] = ubl[i - 1];
            } else {
                header[i] = pbl[i - 5];
            }
        }

        String aux = u + p;
        byte[] payload = aux.getBytes();

        byte[] packet = new byte[payload.length + header.length];

        for (int i = 0; i < payload.length + header.length; i++) {
            if (i < header.length) {
                packet[i] = header[i];
            } else {
                packet[i] = payload[i - header.length];
            }
            System.out.println(i + "-" + packet[i]);
        }
        String message = new String(packet);


        message = message.concat("<!end!>");
        System.out.println("!message: " + message + "\ncenas: " + u + "(" + u.length() + ")/" + p + "(" + p.length() + ")");
        String reply = talk(message);

        if (reply.equals("check_1")){
            System.out.println("true true");
            return true;
        }
        System.out.println("oh noooooo");
        return false;
    }

    public boolean registerUSer(String user, String password, String email){

        byte[] ubl = ByteBuffer.allocate(4).putInt(user.length()).array();
        byte[] pbl = ByteBuffer.allocate(4).putInt(password.length()).array();
        byte[] ebl = ByteBuffer.allocate(4).putInt(email.length()).array();

        byte[] header = new byte[13];
        header[0] = (byte) 2;
        for (int i = 1; i < 13; i++) {
            if (i < 5) {
                header[i] = ubl[i - 1];
            } else if(i < 9){
                header[i] = pbl[i - 5];
            } else
                header[i] = ebl[i-9];
        }
        String aux = user + password + email;
        byte[] payload = aux.getBytes();

        byte[] packet = new byte[payload.length + header.length];

        for (int i = 0; i < payload.length + header.length; i++) {
            if (i < header.length) {
                packet[i] = header[i];
            } else {
                packet[i] = payload[i - header.length];
            }
            System.out.println(i + "-" + packet[i]);
        }
        String message = new String(packet);

        System.out.println("message: " + message + "\ncenas: " + user + "(" + user.length() + ")/" + password + "(" + password.length() + ")/"+ email+"("+email.length()+")");

        message = message.concat("<!end!>");
        String reply = talk(message);

        if (reply.equals("check_2")){
            System.out.println("true dat");
            return true;
        }
        System.out.println("shiet");
        return false;
    }

    public String getUserData(){
        byte[] header = {(byte)3};
        String str = new String(header);

        str = str.concat("<!end!>");
        str = talk(str);

        if(str.equals("error: 404"))
            return str;

        System.out.println(str);

        byte[] r = str.getBytes(StandardCharsets.UTF_8);

        byte[] ul = new byte[4];
        for(int i = 1; i<5; i++){
            ul[i-1] = r[i];
            System.out.println(i+"u"+r[i]);
        }

        byte[] el = new byte[4];
        for(int i = 5; i<9; i++){
            el[i-5] = r[i];
            System.out.println(i+"p"+r[i]);
        }

        int ull = ByteBuffer.wrap(ul).getInt();
        int ell = ByteBuffer.wrap(el).getInt();
        System.out.println("n: "+ull+" / " +ell);

        System.out.print("user: ");
        byte[] userb = new byte[ull];
        for(int i = 9; i<9+ull; i++){
            userb[i-9] = r[i];
            System.out.print((char)r[i]);
        }
        System.out.print("\npass: ");
        byte[] emailb = new byte[ell];
        for(int i = 9+ull; i<9+ull+ell; i++){
            emailb[i-(9+ull)]= r[i];
            System.out.print((char)r[i]);
        }


        String user = new String(userb);
        String email = new String (emailb);

        str = user+"--"+email;

        return str;
    }

    public boolean updateProfile(String user, String email, String pass){
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

        message = message.concat("<!end!>");
        String reply = talk(message);

        if (reply.equals("check_4")){
            System.out.println("true dat");
            return true;
        }
        System.out.println("shiet");
        return false;

    }

    public String getFeedAll(int p){
        byte[] type = {(byte)5};
        byte[] page = ByteBuffer.allocate(4).putInt(p).array();
        byte[] header = new byte[type.length + page.length];
        System.arraycopy(type, 0, header, 0, type.length);
        System.arraycopy(page, 0, header, type.length, page.length);
        String str = new String(header);

        str = str.concat("<!end!>");
        System.out.println(str);
        str = talk(str);

        if(str.equals("error: 404"))
            return str;

        String data = new String();

        System.out.println("Decode string");
        byte[] r = str.getBytes(StandardCharsets.UTF_8);
        int total = 1;
        int nVid = r[0];
        System.out.println("nVid: "+nVid);

        for(int j = 0; j<4; j++){

            byte[] t = new byte[4];
            for (int i = total; i < total+4; i++) {
                t[i - total] = r[i];
                System.out.println(i + "u" + r[i]);
            }
            total += 4;
            byte[] a = new byte[4];
            for (int i = total; i < total+4; i++) {
                a[i - total] = r[i];
                System.out.println(i + "p" + r[i]);
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

            str = title+author;
            System.out.println("String"+j+": "+str);

            data = data.concat(str);
            total++;
        }


        System.out.println("sc.data: "+data);
        return data;
    }
}

