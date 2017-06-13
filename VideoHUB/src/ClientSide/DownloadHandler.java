package ClientSide;

import Network.Frame;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * The Project fx_play was created by
 * António Lourenço on 24, May of 2017.
 */
public class DownloadHandler implements Runnable {
    ServerComm sc;
    private String fileName;
    String VidId;
    DataOutputStream dos;
    DataInputStream dis;
    List<Integer> receivedChunks;
    Frame frame = new Frame();


    public DownloadHandler(ServerComm sc, String id) {
        this.VidId = id;
        this.sc = sc;
        System.out.println("\n New Download:\n");

    }

    // @Override
    //Inicia a comunicação com a trama
    public void run() {
        System.out.println("running download");
        int count = 0;


        dos = sc.getDos();
        dis = sc.getDis();
        frame = new Frame(dos,dis);
        String[] aux2 = {""};


        String ack = frame.buildFrame((byte) 15, aux2);

        boolean flag = true;
        String fileContent = "";

        File file = new File("/home/rafael/Documentos/VideoHubVideo/Temp/" + VidId);
        //BufferedOutputStream bos = getBos(file);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Created File");

        String[] foo = {String.valueOf(VidId)};
        String aux = frame.talk(frame.buildFrame((byte)13, foo));
        byte[] auxB = new byte[4015];

        if(aux.charAt(0) == 15) {
            while(flag) {
                try {
                    dos.flush();
                    fos = new FileOutputStream(file, true);
                    dis.read(auxB);
                    ++count;
                    boolean flag2 = true;
                    if(auxB[0] == (byte)16) {
                        System.out.println("Received frame 16");
                        flag2 = false;
                        flag = false;
                    }

                    while(flag2) {
                        if(aux.indexOf("<!end!>") == aux.length() - 7) {
                            flag2 = false;
                        } else {
                            String part = dis.readUTF();
                            aux = aux.concat(part);
                        }
                    }

                    if(flag) {

                        /*List<String> fields = sc.readFrame(aux);
                        fileContent = fields.get(0);//checkFrames(fields, fileContent);

                        byte[] esse = fileContent.getBytes();*/

                        byte[] temp = new byte[4];

                        for(int j = 0; j<4; j++){
                            temp[j] = auxB[j+1];
                        }

                        int size = ByteBuffer.wrap(temp).getInt();
                        temp = new byte[size];
                        for(int j = 0; j < size; ++j) {
                            temp[j] = auxB[j + 5];
                        }

                        System.out.println("writing file: ");
                        fos.write(temp);
                    }

                    byte b = 15;
                    dos.write(b);
                } catch (IOException e) {
                    System.out.println("Download aborted");
                    e.printStackTrace();
                }
            }
        }
        System.out.println("End of download");
        Thread.currentThread().interrupt();
    }

    private String checkFrames(List<String> fields, String s) {
        List<String> temp = new ArrayList<>();
        //List<Integer> nack = new ArrayList<>();

        int i = 1;
        for (String str : fields) {
            if (i % 2 != 0) {
                s = s.concat(str);
            }
        }
        System.out.println("checkFrames: "+s);
        /*i = temp.get(0);
        for(Integer a : temp){
            if(a!=i) {

            }
            else
                receivedChunks.add(a);
            i++;

        }*/

        return s;
    }

    private BufferedOutputStream getBos(File f){
        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f, true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            return bos;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
