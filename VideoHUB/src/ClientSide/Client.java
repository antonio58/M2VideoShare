package ClientSide;

import java.io.*;
import java.net.Socket;
import java.io.IOException;

/**
 * Created by fernando on 01-04-2017.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        int port = 3333;
        Socket socket = null;
        String host = "127.0.0.1";
        String outp;

        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream dos = new DataOutputStream(
                socket.getOutputStream());
        DataInputStream dis = new DataInputStream(
                socket.getInputStream());
        outp = dis.readUTF();
        System.out.println(outp);
        while (true) {

            outp = dis.readUTF();
            System.out.println(outp);
            String inputLine = bufferReader.readLine();
            dos.writeUTF(inputLine);
            dos.flush();
            outp = dis.readUTF();
            System.out.println(outp);

        }
    }
}
