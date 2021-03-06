package ClientSide;

import Network.Frame;
import org.bson.types.ObjectId;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Utilizador on 20/04/2017.
 */
public class ServerComm {

    private int port = 3333;
    private Socket socket = null;
    private String host = "::1";
    private String outp;
    private DataOutputStream dos;
    private DataInputStream dis;
    private Frame frame;

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
            reply = dis.readUTF();
        } catch (IOException e) {
            //TODO ACTIVATE DTN CLIENT NODE HERE in DownloadHandler
            e.printStackTrace();
        }
        System.out.println("message: "+message + " reply: "+reply);

        return reply;
    }


    public boolean connectToServer(String host, int port) throws IOException {
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

    public boolean checkLogin(String u, String p) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pHash = DataHandler.getSHA1Hex(p);

        String[] fields = {u,pHash};
        String message = frame.buildFrame((byte)1, fields);

        //System.out.println("Frame: "+message);

        String reply = talk(message);

        if (reply.equals("check_1")){
            System.out.println("true true");
            return true;
        }
        System.out.println("oh noooooo");
        return false;
    }

    public boolean registerUSer(String user, String password, String email) throws NoSuchAlgorithmException {

        String pHash = DataHandler.getSHA1Hex(password);

        String[] fields = {user,pHash,email};
        String message = frame.buildFrame((byte)2, fields);

        String reply = talk(message);

        if (reply.equals("check_2")){
            System.out.println("true dat");
            return true;
        }
        return false;
    }

    public String getUserData(){

        String[] aux = new String[0];
        String str = frame.buildFrame((byte)3, aux);
        str = talk(str);

        if(str.equals("error: 404"))
            return str;

        System.out.println("getUserData201: "+str);

        List<String> fields = frame.readFrame(str);

        String user = fields.get(0);//new String(userb);
        String email = fields.get(1);//new String (emailb);

        str = user+"--"+email;

        return str;
    }

    public boolean updateProfile(String user, String email, String pass){

        String[] fields = {user,pass,email};
        String aux = frame.buildFrame((byte)4, fields);

        String reply = talk(aux);

        if (reply.equals("check_4")){
            System.out.println("Profile updated");
            return true;
        }
        return false;

    }

    public String getFeed(int p, byte feedType){

        String[] fields = {String.valueOf(p)};
        String str = frame.buildFrame(feedType, fields);

        str = talk(str);

        if(str.equals("error: 404"))
            return str;
        if(str.equals("No videos"))
            return str;

        List<String> fieldList = frame.readFrame(str);
        String info = "";
        int c = 1;
        for(String s : fieldList){
            System.out.println("field: "+s);
            info = info.concat(s);
            if(c%3 != 0)
                info = info.concat("</split/>");
            else
                info = info.concat("!:split:!");
            c++;
        }

        return info;
    }

    public void closeSocket() throws IOException {
        this.socket.close();
    }

    public String getSearchResults(String query){

        System.out.println("query1: "+query);

        String[] engodo = {query};
        String str = frame.buildFrame((byte)7, engodo);
        str = talk(str);

        if(str.equals("error: 404"))
            return str;
        if(str.equals("No videos"))
            return str;

        List<String> fieldList = frame.readFrame(str);
        String info = "";
        int c = 1;
        for(String s : fieldList){
            info = info.concat(s);
            if(c%3 != 0)
                info = info.concat("</split/>");
            else
                info = info.concat("!:split:!");
            c++;
        }

        return info;
    }

    public List<String> getVideoInfo(String id){
        String aux = id;//String.valueOf(this.id);
        String[] aux2 = {aux};
        System.out.println("handlePlay: "+aux);
        aux = frame.buildFrame((byte)8, aux2);
        aux = talk(aux);

        return frame.readFrame(aux);
    }

    public boolean deliveryStateUpdate(ObjectId videoId, String state) throws UnsupportedEncodingException {

        String[] fields = {videoId.toHexString(), state};
        String message = frame.buildFrame((byte)14, fields);

        String reply = talk(message);

        if (reply.equals("check_14")){
            System.out.println("check_14");
            return true;
        }
        System.out.println("oh no");
        return false;
    }

    //receives ACK from DTN node of streaming successful conclusion
    public boolean streamConcluded(){
        //updates delivery table and terminates all related pending tasks
        if(deliveryEntryUpdate("state", "completed")) {
            return true;
        } else
            return false;
    }

    //updates database delivery entry with received parameters from the DTN server Node
    public boolean deliveryEntryUpdate(String parameter1, String value){
        String[] fields = {parameter1, value};
        String ticket = frame.buildFrame((byte)1, fields);

        String reply = frame.talk(ticket);
        if (reply.equals("check_delivery")){
            System.out.println("Delivery task update success");
            return true;
        }
        return false;
    }


}

