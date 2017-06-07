package TESTS;

import Models.Comment;
import Models.Video;

import java.util.ArrayList;

/**
 * Created by fernando on 24-04-2017.
 * Tests on Video Class
 */
public class VideoTasksDigging {
    private static Video video = new Video();
    private static ArrayList<Comment> comments = new ArrayList<>();
    private static ArrayList<String> tags = new ArrayList<>();

    public static void main(String[] args){
        tags = populatetags();
        populatetags();
        populateComments();
        populateVideoModel("58f9f7a91fb2bf7c46abe5d6", "Animais","20:40", tags,"BBC Vida Selvagem episódio x.25", false, "404", comments);

    }

    private static void populateVideoModel(String author, String Cat, String duration, ArrayList<String> tags, String name, boolean bool, String views, ArrayList<Comment> comments){
        video.setAuthor(author);
        video.setCategory(Cat);
        video.setDuration(duration);
        video.setTags(tags);
        video.setName(name);
        video.setPremium(bool);
        video.setViews(views);
        video.setCommentList(comments);
    }

    private static ArrayList<Comment> populateComments(){
        Comment comment = new Comment("Já notaram que muitos dos casos adultos são pessoal hospitalar. Porque não se imunizam estes profissionais. Centros clínicos são hotspots de infeções. Será q estão vacinados mas não imunizados? Medicina preventiva é muito mais que cocktails de vacinas. Há demasiados buracos nas teorias de vacinação. E infelizmente há sempre pessoas cujo sistema imunitário por si só não combate infeções adequadamente, e surgem fatalidades. Grande infelicidade.", "58dfaff4779fe878e07abf6a");
        Comment comment2 = new Comment("Livres, justas e transparentes?! Tal como todas as anteriores, suponho. Qual é coisa qual é ela que antes de o ser já o era? Claro que é o plebiscito angolano! Livres, justas e transparentes... Por decreto e antecipação: e há lá coisa mais estúpida que o socialismo elevado à Africanidade, sim há! O socialismo elevado ao Bolivarismo. O socialismo(s) é sempre um expoente, o problema é a base ser sempre menor que 1...\n" +
                "É o socialismo estúpido(s)!", "58dfaff4779fe878e07abf6a");
        Comment comment3 = new Comment("Nada de novo. O problema da banca é apresentado por todas as instituições internacionais, que dizem que o mesmo deveria ter sido resolvido durante a vigência da troika.", "58dfaff4779fe878e07abf6c");
        Comment comment4 = new Comment("Portugal fez um belo trabalho ao nível do seu povo e classe mais pobre, já o mesmo não se pode dizer da classe privilegiada que além dos roubos ainda mais não fizeram que continuar a esconder e a roubar..logo é difícil para os mesmos de sempre pagarem seja o que for....porque esta crise feita por corruptos labregos e porcos....não teria passado de um susto senão tivessem roubado tanto...agora da forma e do modos operandi que foi feito..Portugal corre sérios riscos...continua frágil...lógico...eles continuam ca dentro...ninguém os matou nem correram com eles...então? estamos a espera do quê? de um novo golpe? eles não param de roubar...está no ADN deles...por isso são porcos nojentos..", "58dfaff4779fe878e07abf6c");

        comments.add(comment);
        comments.add(comment2);
        comments.add(comment3);
        comments.add(comment4);

        return comments;
    }

    private static ArrayList<String> populatetags(){
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Gato");
        tags.add("Tigre");
        tags.add("Jacaré");
        tags.add("Dinossauro");
        tags.add("Armadilho");
        return tags;
    }
}
