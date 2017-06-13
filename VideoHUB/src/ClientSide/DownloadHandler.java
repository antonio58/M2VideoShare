package ClientSide;

import DTNmecanisms.DTN_Client;
import Network.Frame;
import TESTS.TEST_DTNclient;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
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
    String parameters1 = "";

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

        this.sc = new ServerComm();
        try {
            sc.connectToServer("::1", 3333);
        } catch (IOException e) {}
        frame = new Frame(sc.getDos(),sc.getDis());

        dos = sc.getDos();
        dis = sc.getDis();
        frame = new Frame(dos,dis);
        String[] aux2 = {""};


        String ack = frame.buildFrame((byte) 15, aux2);

        boolean flag = true;
        String fileContent = "";

        File file = new File("/home/luisf99/Documentos/UniversidadeMinho/ProjetodeTelecomunicacoesInformatica2/Videos/" + VidId);
        //BufferedOutputStream bos = getBos(file);
        FileOutputStream fos;
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
                    boolean flag2 = true;
                    if(auxB[0] == (byte)16) {
                        System.out.println("Received frame 16");
                        flag2 = false;
                        flag = false;
                    }

                    while(flag2) {
                        if(aux.indexOf("<!end!>") == aux.length() - 7) {
                            ++count;
                            int trama = count - 2;
                            parameters1 = VidId + " " + trama;
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
                    try {
                        sc.closeSocket();
                        flag = false;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    File parameters = new File("/home/luisf99/Documentos/UniversidadeMinho/ProjetodeTelecomunicacoesInformatica2/Videos/parametros.txt");
                    FileOutputStream param;
                    try {
                        byte[] fields = parameters1.getBytes();
                        param = new FileOutputStream(parameters);
                        try {
                            param.write(fields);
                            DTN_Client dtn_client = new DTN_Client();
                            String[] args = dtn_client.readFile();
                            System.out.println("Parametros: "+Arrays.toString(args));
                            dtn_client.resumeStreaming(args[0], args[1]);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
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
