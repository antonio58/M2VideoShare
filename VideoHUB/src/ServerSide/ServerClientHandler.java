package ServerSide;

import Models.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Created by fernando on 01-04-2017.
 */

public class ServerClientHandler implements Runnable {
    private Socket socketClient;
    private int clientNumber;
    private String response;

    public ServerClientHandler(Socket socketClient, int clientNumber) {
        this.socketClient = socketClient;
        this.clientNumber = clientNumber;
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
/*
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
                        String dataFA = getFeed(response, true);
                        dos.writeUTF(dataFA);
                        break;

                    case 6:
                        String dataFS = getFeed(response, false);
                        dos.writeUTF(dataFS);
                        break;

                    case 7:
                        String results = getSearchResults(response);
                        dos.writeUTF(results);
                        break;
*/
                }



                //dos.writeUTF("Message Received");
                dos.flush();
            }  } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public int checkUser(String s){
        //User Frame
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

        String username = new String(userb);
        String password = new String(passb);
        int id = 0;


        /********************/
        //Pesquisar em por utilizador utilizando o nome e verificar se a pass é igual
        //retorna o id
        System.out.println("user: "+username+" / "+password);
        User user = new User();
        user.setName(username);
        user.setHashPassword(password);
        UserTasks userTasks = new UserTasks(user);
        userTasks.addUser();

        System.out.println("404");
        return id;
    }
}