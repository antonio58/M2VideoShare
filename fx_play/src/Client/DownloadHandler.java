package Client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;

/**
 * The Project fx_play was created by
 * António Lourenço on 24, May of 2017.
 */
public class DownloadHandler implements Runnable {
    ServerComm sc;
    private String fileName;
    int VidId;
    DataOutputStream dos;
    DataInputStream dis;
    List<Integer> receivedChunks;


    public DownloadHandler(ServerComm sc, int id, String filename) {
        this.VidId = id;
        this.sc = sc;
        this.fileName = filename;
        System.out.println("\n New Download:\n");

    }

    // @Override
    //Inicia a comunicação com a trama
    public void run() {
        System.out.println("running download");
        int count = 0;

        /*try {
            InputStream is = sc.getSocket().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fos = new FileOutputStream("/home/mangas/Documents/VideoHub/"+fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        dos = sc.getDos();
        dis = sc.getDis();

        /*String aux = String.valueOf(this.VidId);*/
        String[] aux2 = {""};


        String ack = sc.buildFrame((byte) 15, aux2);

        boolean flag = true;
        String fileContent = "";

        File file = new File("/home/mangas/Documents/VideoHub/Temp" + fileName);
        //BufferedOutputStream bos = getBos(file);
        FileOutputStream fos;
        System.out.println("Created File");

        String[] foo = {String.valueOf(VidId)};
        String aux = sc.talk(sc.buildFrame((byte)13, foo));
        //byte[] auxB = new byte[8001];

        if (aux.charAt(0) == (byte) 15)
            while (flag) {
                try {
                    fos = new FileOutputStream(file,true);
                    aux = dis.readUTF();
                    //dis.read(auxB);
                    count++;
                    System.out.println("Frame number: "+count);
                    boolean flag2 = true;
                    if (aux.charAt(0) == (byte) 16) {
                        System.out.println("Received frame 16");
                        flag = flag2 = false;
                    }

                    while (flag2) {
                        if (aux.indexOf("<!end!>") == aux.length() - 7) {
                            flag2 = false;
                        } else {
                            String part = dis.readUTF();
                            aux = aux.concat(part);
                        }
                    }
                    if(flag) {

                        List<String> fields = sc.readFrame(aux);
                        fileContent = fields.get(0);//checkFrames(fields, fileContent);

                        byte[] esse = fileContent.getBytes();

                        System.out.println("writing file: ");
                        fos.write(esse);
                    }
                    byte b = 15;
                    dos.write(b);

                    //while(reply.equals("")) {
                    //}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("End of download");




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
