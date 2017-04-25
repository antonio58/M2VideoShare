package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by fernando on 01-04-2017.
 */

public class ServerClientHandler implements Runnable {
    private Socket socketClient;
    private int clientNumber;
    private String response;
    private ArrayList<User> users = new ArrayList<>();
    ArrayList<Video> videos = new ArrayList<>();


    public ServerClientHandler(Socket socketClient, int clientNumber, ArrayList<User> users, ArrayList<Video> videos) {
        this.socketClient = socketClient;
        this.clientNumber = clientNumber;
        this.users = users;
        this.videos = videos;
        System.out.println("\n New Client:\n"
                + socketClient.getInetAddress() + " || " + socketClient.getPort()+"\n");

    }

    // @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(
                    socketClient.getOutputStream());
            DataInputStream dis = new DataInputStream(
                    socketClient.getInputStream());

            dos.writeUTF("Welcome Client number " + clientNumber);
            int currentUserId = 0;
            dos.flush();
            while(true) {
                //dos.writeUTF("\nWrite something");
                dos.flush();

                response = new String();
                boolean flag = true;
                while(flag) {
                    String part = dis.readUTF();
                    System.out.println("(Client " + clientNumber + ") Message Received: " + part);
                    response = response.concat(part);
                    if(part.indexOf("<!end!>") == part.length()-7){
                        flag = false;
                    }
                }
                char b = response.charAt(0);
                System.out.print("Frame type: "+b);

                switch (b){
                    case 1:
                        currentUserId = checkUser(response);
                        if(currentUserId != 0)
                            dos.writeUTF("check_1");
                        else
                            dos.writeUTF("nonono");
                        break;

                    case 2:
                        if(registerUser(response))
                            dos.writeUTF("check_2");
                        else
                            dos.writeUTF("ooooohhhh");
                        break;

                    case (byte)3:
                        String str = getUserData(currentUserId);
                        dos.writeUTF(str);
                        break;

                    case 4:
                        if(updateProfile(response, currentUserId))
                            dos.writeUTF("check_4");
                        else
                            dos.writeUTF("ups...");
                        break;

                    case 5:
                        String dataFA = getFeedAll(response);
                        dos.writeUTF(dataFA);
                        break;

                }



                //dos.writeUTF("Message Received");
                dos.flush();
            }  } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int checkUser(String s){

        byte[] r = s.getBytes(StandardCharsets.UTF_8);

        byte[] ul = new byte[4];
        for(int i = 1; i<5; i++){
            ul[i-1] = r[i];
            System.out.println(i+"_"+r[i]);
        }

        byte[] pl = new byte[4];
        for(int i = 5; i<9; i++){
            pl[i-5] = r[i];
            System.out.println(i+"_"+r[i]);
        }

        int ull = ByteBuffer.wrap(ul).getInt();
        int pll = ByteBuffer.wrap(pl).getInt();
        System.out.println("n: "+ull+" / "+pll);

        byte[] userb = new byte[ull];
        for(int i = 9; i<9+ull; i++){
            userb[i-9] = r[i];
        }
        byte[] passb = new byte[pll];
        for(int i = 9+ull; i<9+ull+pll; i++){
            passb[i-(9+ull)]= r[i];
        }

        String user = new String(userb);
        String password = new String(passb);
        int id = 0;

        System.out.println("user: "+user+" / "+password);
        for(User u : users){
            if(u.getName().equals(user))
                if(u.checkPassword(password)){
                    id = u.getId();
                    System.out.println("checked");
                    return id;
                }
        }

        System.out.println("404");
        return id;
    }

    private boolean registerUser(String s){

        byte[] r = s.getBytes(StandardCharsets.UTF_8);

        byte[] ul = new byte[4];
        for(int i = 1; i<5; i++){
            ul[i-1] = r[i];
            System.out.println(i+"u"+r[i]);
        }

        byte[] pl = new byte[4];
        for(int i = 5; i<9; i++){
            pl[i-5] = r[i];
            System.out.println(i+"p"+r[i]);
        }
        byte[] el = new byte[4];
        for(int i= 9; i<13; i++ ){
            el[i-9] = r[i];
            System.out.println(i+"e"+r[i]);
        }

        int ull = ByteBuffer.wrap(ul).getInt();
        int pll = ByteBuffer.wrap(pl).getInt();
        int ell = ByteBuffer.wrap(el).getInt();
        System.out.println("n: "+ull+" / "+pll+" / " +ell);

        System.out.print("user: ");
        byte[] userb = new byte[ull];
        for(int i = 13; i<13+ull; i++){
            userb[i-13] = r[i];
            System.out.print((char)r[i]);
        }
        System.out.print("\npass: ");
        byte[] passb = new byte[pll];
        for(int i = 13+ull; i<13+ull+pll; i++){
            passb[i-(13+ull)]= r[i];
            System.out.print((char)r[i]);
        }
        System.out.print("\nemail: ");
        byte[] emailb = new byte[ell];
        for(int i = 13+ull+pll; i<13+ull+pll+ell; i++){
            byte aux = r[i];
            emailb[i-(13+ull+pll)] = aux;
            System.out.print((char)r[i]);
        }


        String user = new String(userb);
        String password = new String(passb);
        String email = new String (emailb);

        System.out.println("\nuser: "+user+" / "+password+" / "+email);

        User u = new User(users.size()+1, user, email, password, Collections.emptyList());
        users.add(u);

        System.out.println("updated");
        return true;

    }

    private String getUserData(int id){
        User user = new User();
        boolean flag = false;
        System.out.println("id: "+id);
        for(User u : this.users){
            if(u.getId()==id) {
                user = u;
                flag = true;
            }
        }

        if(!flag)   return "error: 404";

        String userN = user.getName();
        String email = user.getEmail();

        byte[] ubl = ByteBuffer.allocate(4).putInt(userN.length()).array();
        byte[] ebl = ByteBuffer.allocate(4).putInt(email.length()).array();

        byte[] header = new byte[9];
        header[0] = (byte) 0;
        for (int i = 1; i < 9; i++) {
            if (i < 5) {
                header[i] = ubl[i - 1];
            } else
                header[i] = ebl[i-5];
        }
        String aux = userN + email;
        byte[] payload = aux.getBytes();

        byte[] packet = new byte[payload.length + header.length];

        for (int i = 0; i < packet.length; i++) {
            if (i < header.length) {
                packet[i] = header[i];
            } else {
                packet[i] = payload[i - header.length];
            }
            System.out.println(i + "-" + packet[i]);
        }
        String message = new String(packet);

        return message;
    }

    private boolean updateProfile(String s, int id){
        User user = new User();
        boolean flag = false;

        for(User u : this.users){
            if(u.getId()==id) {
                user = u;
                flag = true;
            }
        }

        if(!flag)   return flag;

        byte[] r = s.getBytes(StandardCharsets.UTF_8);

        byte[] ul = new byte[4];
        for(int i = 1; i<5; i++){
            ul[i-1] = r[i];
            System.out.println(i+"u"+r[i]);
        }

        byte[] pl = new byte[4];
        for(int i = 5; i<9; i++){
            pl[i-5] = r[i];
            System.out.println(i+"p"+r[i]);
        }
        byte[] el = new byte[4];
        for(int i= 9; i<13; i++ ){
            el[i-9] = r[i];
            System.out.println(i+"e"+r[i]);
        }

        int ull = ByteBuffer.wrap(ul).getInt();
        int pll = ByteBuffer.wrap(pl).getInt();
        int ell = ByteBuffer.wrap(el).getInt();
        System.out.println("n: "+ull+" / "+pll+" / " +ell);

        System.out.print("user: ");
        byte[] userb = new byte[ull];
        for(int i = 13; i<13+ull; i++){
            userb[i-13] = r[i];
            System.out.print((char)r[i]);
        }
        System.out.print("\npass: ");
        byte[] passb = new byte[pll];
        for(int i = 13+ull; i<13+ull+pll; i++){
            passb[i-(13+ull)]= r[i];
            System.out.print((char)r[i]);
        }
        System.out.print("\nemail: ");
        byte[] emailb = new byte[ell];
        for(int i = 13+ull+pll; i<13+ull+pll+ell; i++){
            byte aux = r[i];
            emailb[i-(13+ull+pll)] = aux;
            System.out.print((char)r[i]);
        }


        String userN = new String(userb);
        String password = new String(passb);
        String email = new String (emailb);

        if(!userN.equals(""))
            user.setName(userN);

        if(!password.equals(""))
            user.setPassword(password);

        if(!email.equals(""))
            user.setEmail(email);

        System.out.println("\nuser: "+userN+" / "+password+" / "+email);

        System.out.println("registered");

        return flag;
    }

    public String getFeedAll(String s){
        byte[] r = s.getBytes(StandardCharsets.UTF_8);

        r[0] = (byte)videos.size();
        byte[] page = new byte[4];
        for(int i = 1; i<5; i++){
            page[i-1] = r[i];
            System.out.println(i+"u"+r[i]);
        }

        String message = new String();
        for(Video v : videos){
            String title = v.getTitle();
            String author = v.getAuthor();

            byte[] tb = ByteBuffer.allocate(4).putInt(title.length()+"</split/>".getBytes().length).array();
            byte[] ab = ByteBuffer.allocate(4).putInt(author.length()+"!:split:!".getBytes().length).array();

            byte[] header = new byte[9];
            header[0] = (byte) 1;
            for (int i = 1; i < 9; i++) {
                if (i < 5) {
                    header[i] = tb[i - 1];
                } else {
                    System.out.println(i + "a" + r[i]);
                    header[i] = ab[i - 5];
                }
            }
            String aux = title+ "</split/>" + author;
            byte[] payload = aux.getBytes();

            byte[] packet = new byte[payload.length + header.length];

            for (int i = 0; i < packet.length; i++) {
                if (i < header.length) {
                    packet[i] = header[i];
                } else {
                    packet[i] = payload[i - header.length];
                }
                System.out.println(i + "-" + packet[i]);
            }
            String part = new String(packet);

            message = message.concat(part+"!:split:!");
        }


        return message;
    }
}