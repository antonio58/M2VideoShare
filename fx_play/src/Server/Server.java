package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fernando on 01-04-2017.
 */


public class Server {
    private final String TAG = "Class-Server";

    public static void main(String[] args) {
        int clientNumber = 0;

        //********************************************************
        List<String> chan = Arrays.asList("Cats", "Fails", "Funny");
        User u = new User(1, "esse","esse@email.com", "pass", chan);

        List<String> chan2 = Arrays.asList("Video Games", "Sport", "Music");
        User u2 = new User(2, "aquele","aquele@email.com", "pass", chan2);
        //--------------------------------------------------------
        List<String> tag = Arrays.asList("Cats", "Music", "Funny");
        LocalDateTime d = LocalDateTime.of(1994,5,9,1,1);
        Video v = new Video(1, "1: Cats playing piano","phatguy", tag, d);

        List<String> tag2 = Arrays.asList("Sport", "Fails", "Funny");
        LocalDateTime d2 = LocalDateTime.of(2005,2,3,4,5);
        Video v2 = new Video(2, "2: Boxer get punched in the balls","just4lulz", tag2, d2);

        List<String> tag3 = Arrays.asList("Video Games", "Music");
        LocalDateTime d3 = LocalDateTime.of(2017,2,16,0,0);
        Video v3 = new Video(3, "3: Guitar Hero crazy pro mode","gamer4life", tag3, d3);

        List<String> tag4 = Arrays.asList("Fails", "Funny");
        LocalDateTime d4 = LocalDateTime.now();
        Video v4 = new Video(4, "4: Best Fails on live TV!","paparazi", tag4, d4);
        //*********************************************************

        ArrayList<User> users = new ArrayList<>();
        users.add(u); users.add(u2);

        System.out.println("Server Online");
        try {
            ServerSocket socketServer = new ServerSocket(3333);
            while (true) {
                Socket socket = socketServer.accept();

                ServerClientHandler query = new ServerClientHandler(socket, clientNumber, users);
                Thread client = new Thread(query);
                client.start();
                clientNumber++;
            }
        } catch (IOException e) {
            System.out.println("Server Error");
        }
    }
}
