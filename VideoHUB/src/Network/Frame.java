package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antónio on 10-06-2017.
 */
public class Frame {
    private DataOutputStream dos;
    private DataInputStream dis;

    public Frame() {
    }

    public Frame(DataOutputStream dos, DataInputStream dis) {
        this.dos = dos;
        this.dis = dis;
    }

    //Leitura e construção de frames
    public List<String> readFrame(String s) {
        System.out.println("readframe AQUIII: " + s);
        List<String> fields = new ArrayList<>();
        List<Integer> sizeFields = new ArrayList<>();
        //Transforma a trama num array de bytes
        byte[] frameB = s.getBytes(StandardCharsets.UTF_8);
        int framePointer = 1;

        //Obtém o número de campos na trama
        byte[] numFieldsB = new byte[4];
        for (int i = 0; i < 4; i++) {
            numFieldsB[i] = frameB[framePointer];
            framePointer++;
        }
        int numFields = ByteBuffer.wrap(numFieldsB).getInt();
        //System.out.println("readFreame133: " + numFields);

        //Obtém o tamanho de cada campo
        for (int i = 0; i < numFields; i++) {
            byte[] aux = new byte[4];
            for (int k = 0; k < 4; k++) {
                aux[k] = frameB[framePointer];
                framePointer++;
            }
            int esse = ByteBuffer.wrap(aux).getInt();
            //System.out.println("Fields Size " + i + ": " + esse);
            sizeFields.add(esse);

        }


        //Obtém os campos
        for (int i : sizeFields) {
            byte[] aux = new byte[i];
            for (int j = 0; j < i; j++) {
                aux[j] = frameB[framePointer];
                framePointer++;
            }
            String temp = new String(aux);
            fields.add(temp);
        }

        //System.out.println("(readFrame)Fields: ");
        for (String str : fields)
            System.out.println(str);

        return fields;
    }

    public String buildFrame(byte type, String[] fields) {


        int nFields = fields.length;
        int headerPointer = 1;
        byte[] header = new byte[(nFields * 4) + 5];

        header[0] = type;

        //Coloca o numero de campos
        byte[] nFb = ByteBuffer.allocate(4).putInt(nFields).array();
        for (int i = 0; i < 4; i++) {
            header[headerPointer] = nFb[i];
            headerPointer++;
        }

        //Coloca o tamanho de cada campo
        for (int i = 0; i < nFields; i++) {
            String str_aux = fields[i];
            byte[] fieldLength = ByteBuffer.allocate(4).putInt(str_aux.length()).array();
            for (int j = 0; j < 4; j++) {
                header[headerPointer] = fieldLength[j];
                headerPointer++;
            }
        }

        //Coloca os campos
        String frame = new String(header);
        for (int i = 0; i < nFields; i++) {
            frame = frame.concat(fields[i]);
        }

        //Termina a trama com um palavra de controlo
        frame = frame.concat("<!end!>");

        return frame;
    }

    public String talk(String message) {
        String reply = "";
        System.out.println("talk: "+message);
        try {
            dos.writeUTF(message);
            //while(reply.equals("")) {
            reply = dis.readUTF();
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("message: " + message);
        System.out.println("reply: " + reply);

        return reply;
    }
}
