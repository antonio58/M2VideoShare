package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * The Project fx_play was created by
 * António Lourenço on 24, May of 2017.
 */
public class DownloadHandler implements Runnable {
    ServerComm sc;
    private String fileName;
    int type;
    int VidId;
    DataOutputStream dos;
    DataInputStream dis;


    public DownloadHandler(ServerComm sc, int id, int type, String filename) {
        this.type = type;
        this.VidId = id;
        this.sc = sc;
        this.fileName = filename;
        System.out.println("\n New Download:\n");

    }

    // @Override
    //Inicia a comunicação com a trama
    public void run() {

        File 

        dos = sc.getDos();
        dis = sc.getDis();

        String aux = String.valueOf(this.VidId);
        String[] aux2 = {aux};

        String ack = sc.buildFrame((byte)15, null);

        boolean flag = true;


        while(flag) {
            try {
                dos.writeUTF(aux);
                //while(reply.equals("")) {
                aux = dis.readUTF();
                //}
            } catch (IOException e) {
                e.printStackTrace();
            }


        }





    }
}
