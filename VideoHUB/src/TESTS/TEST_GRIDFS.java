package TESTS;

import Models.Comment;
import Models.Video;
import ServerSide.MongoSide;

import ServerSide.VideoTasks;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.Document;


import java.io.*;
import java.util.*;

/**
 * Created by rafael on 25-05-2017.
 */

public class TEST_GRIDFS {
    private MongoSide mMongo = new MongoSide();
    private MongoCollection<Document> collection = null;
    private static ArrayList<Comment> comments = new ArrayList<>();
    private static ArrayList<String> tags = new ArrayList<>();
    private static ArrayList<String> likes = new ArrayList<>();

    public void test() throws IOException {
//        collection = mMongo.getCollection("fs.chunks");
//        //TODO: É preciso alterar aqui o ObjectId que corresponde ao video
//        Bson filter = Filters.eq("files_id", new ObjectId("5937c430d2bccd4c8f30b750"));
//        /* Results fica com todos os chunks que correspondem a este video. */
//        List<Document> results = collection.aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("data", "$data")))))).into(new ArrayList<>());
//        //Escolhe o ficheiro para qual serão escritos os chunks.
//        FileOutputStream fos = new FileOutputStream(new File("abc.mp4"),true);
//        Binary aux;
//
//        //Saca os chunks e escreve no ficheiro
//        for(int i = 0; i < results.size(); i++) {
//            aux = (Binary)results.get(i).get("data");
//            fos.write(aux.getData());
//        }
//
//        fos.close();
//        System.out.println(results.size());

        saveFile("/home/rafael/Documentos/videos/MyHorseisAmazing.mp4");
    }




    public void saveFile(String path) throws IOException {
        tags = populateTags();
        comments = populateComments();
        likes = populateLikes();

        MongoClient client = new MongoClient();
        File fich = new File(path);
        String filename = path.substring(path.lastIndexOf("/")+1, path.length());
        GridFS gridFS = new GridFS(client.getDB("VideoHub"));
        GridFSInputFile in = gridFS.createFile(fich);
        in.setChunkSize(4000);
        in.setFilename(filename);
        in.save();
        String id = String.valueOf(in.get("_id"));
        System.out.println("Devia imprimir : 59383564d2bccd472d54af20, foi? "+id);
        Video video =  populateVideoModel("592719ebd2bccd463dbc7060", "Animais","20:40", tags,id, path, false, "404", comments, likes);
        VideoTasks videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

    }

    private static Video populateVideoModel(String author, String Cat, String duration, ArrayList<String> tags,String id, String name, boolean bool, String views, ArrayList<Comment> comments, ArrayList<String> likes){
        Video video = new Video();
        video.setAuthor(author);
        video.setCategory(Cat);
        video.setDuration(duration);
        video.setTags(tags);
        video.setFiles_id(id);
        video.setName(name);
        video.setPremium(bool);
        video.setViews(views);
        video.setCommentList(comments);
        video.setLikes(likes);

        return video;
    }

    private static ArrayList<Comment> populateComments(){
        Comment comment = new Comment("Já notaram que muitos dos casos adultos são pessoal hospitalar. Porque não se imunizam estes profissionais. Centros clínicos são hotspots de infeções. Será q estão vacinados mas não imunizados? Medicina preventiva é muito mais que cocktails de vacinas. Há demasiados buracos nas teorias de vacinação. E infelizmente há sempre pessoas cujo sistema imunitário por si só não combate infeções adequadamente, e surgem fatalidades. Grande infelicidade.", new Date(), "58dfaff4779fe878e07abf6a");
        Comment comment2 = new Comment("Livres, justas e transparentes?! Tal como todas as anteriores, suponho. Qual é coisa qual é ela que antes de o ser já o era? Claro que é o plebiscito angolano! Livres, justas e transparentes... Por decreto e antecipação: e há lá coisa mais estúpida que o socialismo elevado à Africanidade, sim há! O socialismo elevado ao Bolivarismo. O socialismo(s) é sempre um expoente, o problema é a base ser sempre menor que 1...\n" +
                "É o socialismo estúpido(s)!", new Date(), "58dfaff4779fe878e07abf6a");
        Comment comment3 = new Comment("Nada de novo. O problema da banca é apresentado por todas as instituições internacionais, que dizem que o mesmo deveria ter sido resolvido durante a vigência da troika.", new Date(),  "58dfaff4779fe878e07abf6c");
        Comment comment4 = new Comment("Portugal fez um belo trabalho ao nível do seu povo e classe mais pobre, já o mesmo não se pode dizer da classe privilegiada que além dos roubos ainda mais não fizeram que continuar a esconder e a roubar..logo é difícil para os mesmos de sempre pagarem seja o que for....porque esta crise feita por corruptos labregos e porcos....não teria passado de um susto senão tivessem roubado tanto...agora da forma e do modos operandi que foi feito..Portugal corre sérios riscos...continua frágil...lógico...eles continuam ca dentro...ninguém os matou nem correram com eles...então? estamos a espera do quê? de um novo golpe? eles não param de roubar...está no ADN deles...por isso são porcos nojentos..", new Date(), "58dfaff4779fe878e07abf6c");

        comments.add(comment);
        comments.add(comment2);
        comments.add(comment3);
        comments.add(comment4);

        return comments;
    }

    private static ArrayList<String> populateTags(){
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Gato");
        tags.add("Tigre");
        tags.add("Jacaré");
        tags.add("Dinossauro");
        tags.add("Armadilho");
        return tags;
    }

    private static ArrayList<String> populateLikes(){
        ArrayList<String> likes = new ArrayList<>();
        likes.add("58dfaff4779fe878e07abf6a");
        likes.add("58dfaff4779fe878e07abf6b");
        likes.add("58dfaff4779fe878e07abf6c");
        likes.add("58dfaff4779fe878e07abf6d");
        return likes;
    }
}