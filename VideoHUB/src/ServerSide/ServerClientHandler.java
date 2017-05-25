package ServerSide;

import Models.Channel;
import Models.Playlist;
import Models.User;
import Models.Video;
import org.bson.types.ObjectId;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonio on 01-04-2017.
 */

public class ServerClientHandler implements Runnable {
    private Socket socketClient;
    private int clientNumber;
    private String response;
    private static ArrayList<ObjectId> watchlist = new ArrayList<>();
    private static ArrayList<ObjectId> seenList = new ArrayList<>();
    private static ArrayList<Channel> channelList = new ArrayList<>();
    private static ArrayList<Playlist> playList = new ArrayList<>();
    private ArrayList<ObjectId> tempIDs = new ArrayList<>();
    private UserTasks userTasks = new UserTasks();
    private VideoTasks videoTasks = new VideoTasks();
    private User self = new User();


    public ServerClientHandler(Socket socketClient, int clientNumber) {
        this.socketClient = socketClient;
        this.clientNumber = clientNumber;
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

                switch (b) {
                    case 1:
                        if (checkUser(response))
                            dos.writeUTF("check_1");
                        else
                            dos.writeUTF("Failed Login");
                        break;

                    case 2:
                        try {
                            if (registerUser(response))
                                dos.writeUTF("check_2");
                            else
                                dos.writeUTF("Failed Register");
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 3:
                        String str = getUserData();
                        dos.writeUTF(str);
                        break;

                    case 4:
                        if (updateProfile(response, userTasks.getUserID()))
                            dos.writeUTF("check_4");
                        else
                            dos.writeUTF("ups...");
                        break;

                    case 5:
                        String dataFA = getFeed(response);
                        dos.writeUTF(dataFA);
                        break;

                    case 6:
                        String dataFS = getFeed(response);
                        dos.writeUTF(dataFS);
                        break;

                    case 7:
                        String results = getSearchResults(response);
                        dos.writeUTF(results);
                        break;

                }


                //dos.writeUTF("Message Received");
                dos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Leitura e construção de frames
    private List<String> readFrame(String s) {

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
    public boolean checkUser(String s) {

        List<String> fields = readFrame(s);

        String username = fields.get(0);
        String password = fields.get(1);
        int id = 0;
        //Pesquisar em por utilizador utilizando o nome e verificar se a pass é igual
        //retorna o id
        System.out.println("user: " + username + " / " + password);

        User user = new User();
        user.setEmail(username);
        user.setHashPassword(password);

        userTasks = new UserTasks(user);

        if(userTasks.checkUser()){
            self = userTasks.user;
            return true;

        }
        return false;
    }

    //Registar utilizador
    private boolean registerUser(String s) throws NoSuchAlgorithmException {

        List<String> fields = readFrame(s);

        /**************/
        //Strings com os valores minimos para criar um novo utilizador
        String username = fields.get(0);
        String password = fields.get(1);
        String email = fields.get(2);

        System.out.println("\nuser: " + username + " / " + password + " / " + email);

        //String pHash = DataHandle.getSHA1Hex(password);

        //Adiciona o novo utilizador
        User user = new User();
        user.setPremium(false);
        user.setName(username);
        user.setEmail(email);
        user.setChannels(channelList);
        user.setWatchedlist(seenList);
        user.setWatchlist(watchlist);
        user.setPlaylists(playList);
        user.setHashPassword(password);
        userTasks = new UserTasks(user);
        userTasks.addUser();
        user.set_id(userTasks.getUserID());

        System.out.println("updated");
        return true;

    }

    //Obter informações para página de profile
    private String getUserData() {
        userTasks.getUserID();
        userTasks.getUserInfo();

        System.out.println("getUserData(): self: " + self.toString());
        //Obter nome e email do user
        String userN = self.getName();
        String email = self.getEmail();

        System.out.println("getUserData(): self: " + self.toString());
        String[] fields = {userN, email};
        String message = buildFrame((byte) 0, fields);

        return message;
    }

    //Actualizar Perfil
    private boolean updateProfile(String s, ObjectId id) {
        List<String> fields = readFrame(s);

        //Valores para actualizar no perfil em que verifica se têm conteúdo ou vieram vazios
        //Ou seja no utilizador logado alteram-se os dados que passarem nos ifs
        String userN = fields.get(0);
        String password = fields.get(1);
        String email = fields.get(2);

        if (!userN.equals(""))
            self.setName(userN);
            userTasks.updateUser("_id", self.get_id(), "name", self.getName());


        if (!password.equals(""))
            self.setHashPassword(password);
            userTasks.updateUser("_id", self.get_id(), "hash", self.getHashPassword());


        if (!email.equals(""))
            self.setEmail(email);
            userTasks.updateUser("_id", self.get_id(), "email", self.getEmail());


        System.out.println("\nuser: " + userN + " / " + password + " / " + email);


        return true;
    }

    //Obter informação para fazer o feed. Boolean all identifica o tipo de feed
    public String getFeed(String s) {
        List<String> auxList = readFrame(s);

        int VideoIndexStart = (Integer.parseInt(auxList.get(0)) - 1) * 10;

        //substituir videos por numero de videos na base de dados
        int nVideos = videoTasks.getNumberOfVideos();
        //System.out.println("getFeed() nVideos size: " + nVideos);

        if (nVideos < 1 || nVideos < VideoIndexStart) {
            String[] auxArray = {"No videos"};
            return buildFrame((byte) 0, auxArray);
        }

        tempIDs = videoTasks.getIds();
        //System.out.println("getFeed() tempID size: " + tempIDs.size() + " " + videoTasks.getIds().size());

        List<String> fieldList = new ArrayList<>();


        //popular array 'temp' com os videos
        for(int i = VideoIndexStart; i < nVideos; i++){
            //fetches video
            Video v = videoTasks.getVideoByIndex(tempIDs.get(i));
            fieldList.add(v.getName());
            fieldList.add(v.getAuthor());
            //System.out.println("Name/Author: "+v.getName()+"/"+v.getAuthor());
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
        //System.out.println("(449)data: " + data);
        String[] keywords = data.split(" ");
        tempIDs = videoTasks.getIds();
        ArrayList<Video> allVideos = new ArrayList<>();

        for (int i = 0 ; i < tempIDs.size(); i++) {
            //System.out.println("getSearchResults() tempID size: " + tempIDs.size());
            allVideos.add(videoTasks.getVideoByIndex(tempIDs.get(i)));
        }
        //Pesquisa na base de dados videos com a ocorrência das keywords no titulo, autor e tags
        //No fim há uma lista de videos
        ArrayList<Video> temp = new ArrayList<>();

        for (Video v : allVideos) {
            String title = v.getName().toLowerCase();
            String author = v.getAuthor().toLowerCase();
            //String tags = v.getTags().toString().toLowerCase();
            //System.out.println("getSearchResults() : " + v.toString());
            for (String s : keywords) {
                s = s.toLowerCase();
                if (!temp.contains(v) && (title.contains(s) || author.contains(s)))
                    temp.add(v);
            }
        }

        if (temp.isEmpty())
            return "No videos";

        //Adiciona a uma List<String> os parametros dos videos
        auxList = new ArrayList<>();
        for (Video v : temp) {
            auxList.add(v.getName());
            auxList.add(v.getAuthor());
        }

        String[] fieldArray = new String[auxList.size()];
        auxList.toArray(fieldArray);

        return buildFrame((byte) 0, fieldArray);

    }
}