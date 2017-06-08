package TESTS;

import Models.Comment;
import Models.Video;
import ServerSide.ChunkTasks;
import ServerSide.VideoTasks;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fernando on 24-04-2017.
 * Tests on Video Class
 */
public class TEST_VIDEOS {
    private static Video video = new Video();
    private static ArrayList<Comment> comments = new ArrayList<>();
    private static ArrayList<String> tags = new ArrayList<>();
    private static ArrayList<String> likes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        comments = populateComments();
        likes = populateLikes();
        tags = populateTags(1);
        video = populateVideoModel("58f9f7a91fb2bf7c46abe5d4", "Animais","20:40", tags,"BBC Vida Selvagem episodio x.25", false, "404", comments, likes, "58f9f7a91fb2bf7c46abe5d4");
        VideoTasks videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        tags = populateTags(3);
        video = populateVideoModel("58f9f7a91fb2bf7c46abe5d4", "Viagens","4:20", tags,"Amazing trip to Panoias", false, "123", comments, likes, "58f9f7a91fb2bf7c46abe5d4");
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        tags = populateTags(2);
        ChunkTasks chunkTasks = new ChunkTasks();
        chunkTasks.fileToChunks("/home/rafael/Documentos/videos/MyHorseisAmazing.mp4");
        video = populateVideoModel("58f9f7a91fb2bf7c46abe5d4", "Metal","4:20", tags,"Obsura - Anticosmic Overload", false, "666", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        videoTasks.getVideoID();

        for (Comment comment : comments) { //for each
            videoTasks.addVideoComment(comment);
        }

        VideoTasks videotasks = new VideoTasks();
        videotasks.getVideoByIndex(new ObjectId("59393d67d2bccd7dc421cee5"));
    }

    private static Video populateVideoModel(String author, String Cat, String duration, ArrayList<String> tags, String name, boolean bool, String views, ArrayList<Comment> comments, ArrayList<String> likes, String filedId){
        Video video = new Video();
        video.setAuthor(author);
        video.setCategory(Cat);
        video.setDuration(duration);
        video.setTags(tags);
        video.setName(name);
        video.setPremium(bool);
        video.setViews(views);
        video.setFiles_id(filedId);
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

    private static ArrayList<String> populateTags(int i){
        ArrayList<String> tags = new ArrayList<>();
        switch (i) {
            case 1:
                tags.add("Gato");
                tags.add("Tigre");
                tags.add("Jacaré");
                tags.add("Dinossauro");
                tags.add("Armadilho");
                break;
            case 2:
                tags.add("Metal");
                tags.add("Obscura");
                tags.add("Cosmogenesis");
                break;
            case 3:
                tags.add("Corridas");
                tags.add("Cars");
                tags.add("Formula 1");

        }
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
