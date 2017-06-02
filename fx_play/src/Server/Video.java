package Server;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * The Project fx_play was created by
 * António Lourenço on 21, April of 2017.
 */
public class Video {
    int id;
    String title;
    String author;
    List<String> tags;
    LocalDateTime date;
    String path;

    public Video() {
        id = 0;
        this.title = "";
        this.author = "";
        this.tags = null;
        this.date = LocalDateTime.now();
        this.path = "";
    }

    public Video(int i, String title, String author, List<String> tags, LocalDateTime d, String path) {
        id = i;
        this.title = title;
        this.author = author;
        this.tags = tags;
        this.date = d;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public int getId(){ return this.id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
