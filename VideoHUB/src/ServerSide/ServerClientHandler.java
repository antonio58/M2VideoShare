package ServerSide;

import Models.Channel;
import Models.Playlist;
import Models.User;
import Models.Video;
import Network.Frame;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private MongoSide mMongo = new MongoSide();
    private MongoCollection<Document> collection = null;
    private DataOutputStream dos;
    private DataInputStream dis;
    private Frame frame;


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
            dos = new DataOutputStream(
                    socketClient.getOutputStream());
            dis = new DataInputStream(
                    socketClient.getInputStream());
            frame = new Frame(dos, dis);
            dos.writeUTF("Welcome Client number " + clientNumber);
            dos.flush();
            while (true) {
                dos.flush();
                response = "";
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

                    case 8:
                        String info = getVideoInfo(response);
                        dos.writeUTF(info);
                        break;

                    case 13:
                        sendVideo(response);
                        break;
                }


                //dos.writeUTF("Message Received");
                dos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void send(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Funções de tratamento de dados de comunicação

    //Login
    public boolean checkUser(String s) {

        List<String> fields = frame.readFrame(s);

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

        if (userTasks.checkUser()) {
            self = userTasks.user;
            return true;

        }
        return false;
    }

    //Registar utilizador
    private boolean registerUser(String s) throws NoSuchAlgorithmException {

        List<String> fields = frame.readFrame(s);

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

        //System.out.println("getUserData(): self: " + self.toString());
        //Obter nome e email do user
        String userN = self.getName();
        String email = self.getEmail();

        //System.out.println("getUserData(): self: " + self.toString());
        String[] fields = {userN, email};
        String message = frame.buildFrame((byte) 0, fields);

        return message;
    }

    //Actualizar Perfil
    private boolean updateProfile(String s, ObjectId id) {
        List<String> fields = frame.readFrame(s);

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


        //System.out.println("\nuser: " + userN + " / " + password + " / " + email);


        return true;
    }

    //Obter informação para fazer o feed. Boolean all identifica o tipo de feed
    public String getFeed(String s) {
        List<String> auxList = frame.readFrame(s);

        int VideoIndexStart = (Integer.parseInt(auxList.get(0)) - 1) * 10;

        //substituir videos por numero de videos na base de dados
        int nVideos = videoTasks.getNumberOfVideos();
        //System.out.println("getFeed() nVideos size: " + nVideos);

        if (nVideos < 1 || nVideos < VideoIndexStart) {
            String[] auxArray = {"No videos"};
            return frame.buildFrame((byte) 0, auxArray);
        }

        tempIDs = videoTasks.getIds();
        //System.out.println("getFeed() tempID size: " + tempIDs.size() + " " + videoTasks.getIds().size());

        List<String> fieldList = new ArrayList<>();


        //popular array 'temp' com os videos
        for (int i = VideoIndexStart; i < nVideos; i++) {
            //fetches video
            Video v = videoTasks.getVideoByIndex(tempIDs.get(i));
            collection = mMongo.getCollection("users");
//            Bson filter = Filters.eq("_id", new ObjectId(v.getAuthor()));
            /* Results fica com todos os chunks que correspondem a este video. */
            List<Document> results = collection.aggregate(Arrays.asList(/*Aggregates.match(filter),*/ Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("name", "$name")))))).into(new ArrayList<>());
            String name = results.get(0).getString("name");

            fieldList.add(v.getName());
            fieldList.add(name);
            fieldList.add(v.get_id().toString());
            //System.out.println("Name/Author: "+v.getName()+"/"+v.getAuthor());
        }

        String[] fieldArray = new String[fieldList.size()];
        fieldList.toArray(fieldArray);
        //System.out.println("TRAMA:\n"+fieldList.toString());

        return frame.buildFrame((byte) 0, fieldArray);
    }

    //Pesquisa
    private String getSearchResults(String query) {

        System.out.println("getSearchResults.query: " + query);
        List<String> auxList = frame.readFrame(query);
        String data = auxList.get(0);
        //System.out.println("(449)data: " + data);
        String[] keywords = data.split(" ");
        tempIDs = videoTasks.getIds();
        ArrayList<Video> allVideos = new ArrayList<>();

        for (int i = 0; i < tempIDs.size(); i++) {
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
            auxList.add(v.get_id().toString());
        }

        String[] fieldArray = new String[auxList.size()];
        auxList.toArray(fieldArray);

        return frame.buildFrame((byte) 0, fieldArray);

    }

    //Obter a info do video
    private String getVideoInfo(String s)   {
        List<String> fields = frame.readFrame(s);
        //System.out.println("video info: "+fields.toString());
        ObjectId id = new ObjectId(fields.get(0));
        Video vid = videoTasks.getVideoByIndex(id);
        String[] info = {vid.getName(), vid.getAuthor(),vid.get_id().toHexString(), vid.getCategory(), vid.getViews(), /*vid.getTags().toString(), /*vid.getCommentList().toString(), vid.getDuration()*/};

        return frame.buildFrame((byte) 0, info);
    }

    private void sendVideo(String vid) throws IOException {

        Binary aux;

        String[] aux2 = {""};
        String ack = frame.buildFrame((byte) 15, aux2);
        send(ack);

        System.out.println("Getting video");

        List<String> foo = frame.readFrame(vid);
        String id = foo.get(0);

        //id: 5938084ad2bccd1992a35bf5
        collection = mMongo.getCollection("videos");
        Bson filter0 = Filters.eq("_id", new ObjectId(id));
        /* Results fica com todos os chunks que correspondem a este video. */
        List<Document> results0 = collection.aggregate(Arrays.asList(Aggregates.match(filter0), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("files_id", "$files_id")))))).into(new ArrayList<>());
        String files_id = results0.get(0).get("files_id").toString();



        collection = mMongo.getCollection("fs.chunks");
        Bson filter = Filters.eq("files_id", new ObjectId(files_id));
        List<Document> results = collection.aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("data", "$data")))))).into(new ArrayList<>());

        System.out.println("Sending video");

        for(int i = 0; i < results.size(); i++) {
            aux = (Binary)results.get(i).get("data");
            byte[] data = aux.getData();
            byte[] auxByte = new byte[10];
            String end = "<!end!>";

            byte[] frame = new byte[data.length+5+end.length()];
            frame[0] = (byte)14;

            byte[] dataSize = ByteBuffer.allocate(4).putInt(data.length).array();
            for(int j = 1; j < 5 ; j++){
                frame[j] = dataSize[j-1];
            }
            //String auxStr = aux;

            for(int j= 0; j<data.length; j++){
                frame[j+5] = data[j];
            }
            byte[] endB = end.getBytes();
            for(int j = 0; j<endB.length; j++ ){
                frame[data.length+5+j] = endB[j];
            }

            do {
                dos.write(frame);
                dis.read(auxByte);
                dos.flush();
            }
            while(auxByte[0]!=15);

        }
        byte b = (byte)16;
        dos.write(b);

        System.out.println("File sent");
    }
}
