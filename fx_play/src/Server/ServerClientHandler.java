package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by fernando on 01-04-2017.
 */

public class ServerClientHandler implements Runnable {
    private Socket socketClient;
    private int clientNumber;
    private String response;
    private User self = new User();
    private ArrayList<User> users = new ArrayList<>();
    ArrayList<Video> videos = new ArrayList<>();


    public ServerClientHandler(Socket socketClient, int clientNumber, ArrayList<User> users, ArrayList<Video> videos) {
        this.socketClient = socketClient;
        this.clientNumber = clientNumber;
        this.users = users;
        this.videos = videos;
        System.out.println("\n New Client:\n"
                + socketClient.getInetAddress() + " || " + socketClient.getPort() + "\n");

    }

    // @Override
    //Inicia a comunicação com a trama
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(
                    socketClient.getOutputStream());
            DataInputStream dis = new DataInputStream(
                    socketClient.getInputStream());

            dos.writeUTF("Welcome Client number " + clientNumber);
            int currentUserId = 0;
            dos.flush();
            while (true) {
                //dos.writeUTF("\nWrite something");
                dos.flush();

                response = new String();
                boolean flag = true;
                while (flag) {
                    String part = dis.readUTF();
                    System.out.println("(Client " + clientNumber + ") Message Received: " + part);
                    response = response.concat(part);
                    if (part.indexOf("<!end!>") == part.length() - 7) {
                        flag = false;
                    }
                }
                char b = response.charAt(0);
                System.out.print("Frame type: " + b);

                switch (b) {
                    case 1:
                        currentUserId = checkUser(response);
                        //readFrame(response);
                        if (currentUserId != 0)
                            dos.writeUTF("check_1");
                        else
                            dos.writeUTF("nonono");
                        break;

                    case 2:
                        if (registerUser(response))
                            dos.writeUTF("check_2");
                        else
                            dos.writeUTF("ooooohhhh");
                        break;

                    case (byte) 3:
                        String str = getUserData(currentUserId);
                        dos.writeUTF(str);
                        break;

                    case 4:
                        if (updateProfile(response, currentUserId))
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

                    /*case 8:
                        String info = getVideoInfo(response);
                        dos.writeUTF(info);
                        break;*/

                }


                //dos.writeUTF("Message Received");
                dos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Leitura e construção de frames
    public List<String> readFrame(String s) {

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
        System.out.println("readFreame133: " + numFields);

        //Obtém o tamanho de cada campo
        for (int i = 0; i < numFields; i++) {
            byte[] aux = new byte[4];
            for (int k = 0; k < 4; k++) {
                aux[k] = frameB[framePointer];
                framePointer++;
            }
            int esse = ByteBuffer.wrap(aux).getInt();
            System.out.println("Fields Size " + i + ": " + esse);
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

        System.out.println("(readFrame)Fields: ");
        for (String str : fields)
            System.out.println(str);

        return fields;
    }

    private String buildFrame(byte type, String[] fields) {


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


    //Funções de tratamento de dados de comunicação

    //Login
    public int checkUser(String s) {

        List<String> fields = readFrame(s);

        String user = fields.get(0);
        String password = fields.get(1);
        int id = 0;

        /********************/
        //Pesquisar em por utilizador utilizando o nome e verificar se a pass é igual
        //retorna o id
        System.out.println("user: " + user + " / " + password);
        for (User u : users) {
            if (u.getName().equals(user))
                if (u.checkPassword(password)) {
                    id = u.getId();
                    this.self = u;
                    System.out.println("checked");
                    return id;
                }
        }

        System.out.println("404");
        return id;
    }

    //Registar utilizador
    private boolean registerUser(String s) {

        List<String> fields = readFrame(s);

        /**************/
        //Strings com os valores minimos para criar um novo utilizador
        String user = fields.get(0);
        String password = fields.get(1);
        String email = fields.get(2);

        System.out.println("\nuser: " + user + " / " + password + " / " + email);

        //Adiciona o novo utilizador
        User u = new User(users.size() + 1, user, email, password, Collections.emptyList());
        users.add(u);

        System.out.println("updated");
        return true;

    }

    //Obter informações para página de profile
    private String getUserData(int id) {
        User user = new User();

        //Verifica se o utilizador existe
        boolean flag = false;
        System.out.println("id: " + id);
        for (User u : this.users) {
            if (u.getId() == id) {
                user = u;
                flag = true;
            }
        }

        if (!flag) return "error: 404";


        //Obter nome e email do user
        String userN = user.getName();
        String email = user.getEmail();

        String[] fields = {userN, email};
        String message = buildFrame((byte) 0, fields);
        /*

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
        String message = new String(packet);*/

        return message;
    }

    //Actualizar Perfil
    private boolean updateProfile(String s, int id) {
        User user = new User();
        boolean flag = false;
        //Verifica se o utilizador existe
        for (User u : this.users) {
            if (u.getId() == id) {
                user = u;
                flag = true;
            }
        }

        if (!flag) return flag;

        List<String> fields = readFrame(s);

        //Valores para actualizar no perfil em que verifica se têm conteúdo ou vieram vazios
        String userN = fields.get(0);
        String password = fields.get(1);
        String email = fields.get(2);

        if (!userN.equals(""))
            user.setName(userN);

        if (!password.equals(""))
            user.setPassword(password);

        if (!email.equals(""))
            user.setEmail(email);

        System.out.println("\nuser: " + userN + " / " + password + " / " + email);

        System.out.println("registered");

        return flag;
    }


    //Obter informação para fazer o feed. Boolean all identifica o tipo de feed
    public String getFeed(String s, boolean all) {

        List<String> auxList = readFrame(s);

        int VideoIndexStart = (Integer.parseInt(auxList.get(0)) - 1) * 10;
        if (videos.size() < 1 || videos.size() < VideoIndexStart) {
            String[] auxArray = {"No videos"};
            return buildFrame((byte) 0, auxArray);
        }

        //Se for do tipo Subscribed vai buscar uma lista dos videos nos canais do utilizador
        ArrayList<Video> temp = new ArrayList<>();
        if (!all) {
            List<String> chan = self.getChannels();
            for (Video v : videos) {
                List<String> tag = v.getTags();
                for (String t : tag)
                    for (String c : chan)
                        if (t.equals(c) && !temp.contains(v)) {
                            temp.add(v);
                        }
            }
        } else
            //Se for all vai uma lista com todos os videos
            temp = videos;

        List<String> fieldList = new ArrayList<>();

        int i= 0 , j = 0;
        for (Video v : temp) {
            if(i<VideoIndexStart)
                i++;
            else if(j<10){
                fieldList.add(v.getTitle());
                fieldList.add(v.getAuthor());
                fieldList.add(String.valueOf(v.getId()));
                j++;
            }
        }

        String[] fieldArray = new String[fieldList.size()];
        fieldList.toArray(fieldArray);

        return buildFrame((byte) 0, fieldArray);

    }

    //Pesquisa
    private String getSearchResults(String query) {

        System.out.println("getSearchResults.query: " + query);

        List<String> auxList = readFrame(query);
        String data = auxList.get(0);

        System.out.println("(449)data: "+data);

        String[] keywords = data.split(" ");


        //Pesquisa na base de dados videos com a ocorrência das keywords no titulo, autor e tags
        //No fim há uma lista de videos
        ArrayList<Video> temp = new ArrayList<>();

        for (Video v : videos) {
            String title = v.getTitle().toLowerCase();
            String author = v.getAuthor().toLowerCase();
            String tags = v.getTags().toString().toLowerCase();
            for (String s : keywords) {
                s = s.toLowerCase();
                if (!temp.contains(v) && (title.contains(s) || tags.contains(s) || author.contains(s)))
                    temp.add(v);
            }
        }

        if(temp.isEmpty())
            return "No videos";

        auxList = new ArrayList<>();
        for(Video v : temp){
            auxList.add(v.getTitle());
            auxList.add(v.getAuthor());
            auxList.add(String.valueOf(v.getId()));

        }

        String[] fieldArray = new String[auxList.size()];
        auxList.toArray(fieldArray);

        return buildFrame((byte) 0, fieldArray);

    }

    /*private String getVideoInfo(String data){

        List<String> aux = readFrame(data);

        int id = Integer.parseInt(aux.get(0));
        Video vid = new Video();
        for(Video v : videos){
            if(v.getId()==id){
                vid = v;
                break;
            }
        }
        String[] fields = {vid.getAuthor(),vid.getTitle(),vid.getTags().toString()};


        return buildFrame((byte)0, fields);
    }*/
}