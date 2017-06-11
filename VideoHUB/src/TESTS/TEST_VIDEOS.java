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
    private static VideoTasks videoTasks = new VideoTasks();

    public static void main(String[] args) throws IOException {
        comments = populateComments();
        likes = populateLikes();

        ChunkTasks chunkTasks = new ChunkTasks();
        chunkTasks.fileToChunks("/home/rafael/Transferências/Prodigy - Smack My Bitch Up (Official Video).mp4");
        tags = populateTags(10);
        video = populateVideoModel("593abc17d2bccd1293c93580", "Music","4:37", tags,"Prodigy - Smack My Bitch Up", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Björk - Army Of Me.mp4");
        tags = populateTags(10);
        video = populateVideoModel("593abc17d2bccd1293c93580", "Music","4:33", tags,"Bjork - Army Of Me", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Johnny Cash - Hurt HD 720p.mp4");
        tags = populateTags(10);
        video = populateVideoModel("593abc17d2bccd1293c93580", "Music","3:49", tags,"Johnny Cash - Hurt", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/House of Cards  Season 5 Official Trailer.mp4");
        tags = populateTags(3);
        video = populateVideoModel("593abdebd2bccd1613534c02", "TV","1:37", tags,"House of Cards Season 5 Trailer", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/RED FANG - Wires (Official Music Video).mp4");
        tags = populateTags(10);
        video = populateVideoModel("593abd11d2bccd152e38862f", "Music","5:55", tags,"RED FANG - Wires", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Salvador Sobral - Amar Pelos Dois.mp4");
        tags = populateTags(8);
        video = populateVideoModel("593abc17d2bccd1293c93580", "Music","3:04", tags,"Salvador Sobral - Amar Pelos Dois", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/WESTWORLD Season 2 Teaser Trailer.mp4");
        tags = populateTags(1);
        video = populateVideoModel("593abc17d2bccd1293c93580", "TV","0:34", tags,"WESTWORLD Season 2 Teaser Trailer", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Westworld Trailer.mp4");
        tags = populateTags(1);
        video = populateVideoModel("593abc17d2bccd1293c93580", "TV","2:10", tags,"Westworld Trailer", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Universidade do Minho - O futuro a partir daqui!.mp4");
        tags = populateTags(9);
        video = populateVideoModel("593abdebd2bccd1613534c02", "Education","4:35", tags,"Universidade do Minho - O futuro a partir daqui!", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Pikachu on Acid.mp4");
        tags = populateTags(7);
        video = populateVideoModel("593abd11d2bccd152e38862f", "Comedy","3:28", tags,"Pikachu on Acid", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Reggae Shark.mp4");
        tags = populateTags(7);
        video = populateVideoModel("593abd11d2bccd152e38862f", "Comedy","2:53", tags,"Reggae Shark", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Portugal Desabafo de um Campeão Europeu Guilherme Cabral.mp4");
        tags = populateTags(12);
        video = populateVideoModel("593abdebd2bccd1613534c02", "Sport","6:08", tags,"Desabafo de um Campeão Europeu", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Cristiano Ronaldo Top 20 Skill Moves 20162017.mp4");
        tags = populateTags(5);
        video = populateVideoModel("593abdebd2bccd1613534c02", "Sport","5:56", tags,"Cristiano Ronaldo Top 20 Skill Moves", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/The Science of Thinking.mp4");
        tags = populateTags(4);
        video = populateVideoModel("593abca7d2bccd14994506f2", "Science","4:19", tags,"The Science of Thinking", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Game of Thrones Season 7 'Long Walk' Promo Explained.mp4");
        tags = populateTags(2);
        video = populateVideoModel("593abca7d2bccd14994506f2", "TV","4:19", tags,"Game of Thrones Season 7 'Long Walk' Promo Explained", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/The Spitfire's Fatal Flaw.mp4");
        tags = populateTags(11);
        video = populateVideoModel("593abca7d2bccd14994506f2", "War","4:39", tags,"The Spitfire's Fatal Flaw", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Fails of the Month- Stuck The Landing!! -- FailArmy.mp4");
        tags = populateTags(3);
        video = populateVideoModel("593abca7d2bccd14994506f2", "Fails","6:25", tags,"Fails of the Month- Stuck The Landing!! -- FailArmy", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Casually Explained- Flirting.mp4");
        tags = populateTags(6);
        video = populateVideoModel("593abca7d2bccd14994506f2", "Funny","3:17", tags,"Casually Explained- Flirting", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/Casually Explained- Is She Into You.mp4");
        tags = populateTags(6);
        video = populateVideoModel("593abca7d2bccd14994506f2", "Funny","2:30", tags,"Casually Explained- Is She Into You", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();

        chunkTasks.fileToChunks("/home/rafael/Transferências/5 Fun Physics Phenomena.mp4");
        tags = populateTags(4);
        video = populateVideoModel("593abca7d2bccd14994506f2", "Science","5:27", tags,"5 Fun Physics Phenomena", false, "4", comments, likes, chunkTasks.getId());
        videoTasks = new VideoTasks(video);
        videoTasks.addVideo();



        for (Comment comment : comments) { //for each
            videoTasks.addVideoComment(comment);
        }

//        VideoTasks videotasks = new VideoTasks();
//        videotasks.getVideoByIndex(new ObjectId("59393d67d2bccd7dc421cee5"));
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
                tags.add("Trailer");
                tags.add("TV");
                tags.add("Series");
                tags.add("Westworld");
                tags.add("Ford");
                break;
            case 2:
                tags.add("Trailer");
                tags.add("GOT");
                tags.add("Daenerys");
                tags.add("Cersei");
                tags.add("John Snow");
                break;
            case 3:
                tags.add("Fails");
                tags.add("Funny");
                tags.add("Best of the month");
            case 4:
                tags.add("Veritasium");
                tags.add("Physics");
                tags.add("Science");
                tags.add("Learning");
                break;
            case 5:
                tags.add("Football");
                tags.add("Cristiano Ronaldo");
                tags.add("Best player");
                break;
            case 6:
                tags.add("Flirting");
                tags.add("Girls");
                tags.add("Relationships");
                break;
            case 7:
                tags.add("Funny");
                tags.add("Cartoon");
                break;
            case 8:
                tags.add("Eurovision");
                tags.add("Salvador Sobral");
                tags.add("Champions");
                tags.add("Music");
                break;
            case 9:
                tags.add("University");
                tags.add("Learning");
                tags.add("Learn");
                break;
            case 10:
                tags.add("Music");
                break;
            case 11:
                tags.add("War");
                tags.add("Spitfire");
                tags.add("WW2");
                break;
            case 12:
                tags.add("European champions");
                tags.add("Portugal 2016");
                tags.add("Champions");

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
