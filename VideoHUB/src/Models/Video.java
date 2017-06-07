package Models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fernando on 21-04-2017.
 */
public class Video {
    private ObjectId _id = null;
    private String name = null;
    private String author = null;
    private String category = null;
    private String duration = null;
    private String file = null;
    private Date creationDate = null;
    private ArrayList<String> deliveries = null;
    private int views = 0;
    private ArrayList<String> tags = null;
    private ArrayList<String> likes = null;
    private ArrayList<Comment> commentList = null;
    private boolean premium = false;
    private Double size = null;
    private int n_blocks = 0;

    public Video(){}

    public void Video(ObjectId _id, String name, String author, String category, String duration, String file, ArrayList<String> deliveries, int views, ArrayList<String> tags, ArrayList<String> likes, ArrayList<Comment> commentList, boolean premium, Double size, int n_blocks) {
        this._id = _id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.duration = duration;
        this.file = file;
        this.deliveries = deliveries;
        this.views = views;
        this.tags = tags;
        this.likes = likes;
        this.commentList = commentList;
        this.premium = premium;
        this.size = size;
        this.n_blocks = n_blocks;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public ArrayList<String> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(ArrayList<String> deliveries) {
        this.deliveries = deliveries;
    }

    public String getViews() {
        return String.valueOf(views);
    }

    public void setViews(int views) {
        this.views = views;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public int getN_blocks() {
        return n_blocks;
    }

    public void setN_blocks(int n_blocks) {
        this.n_blocks = n_blocks;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Video{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", duration='" + duration + '\'' +
                ", file='" + file + '\'' +
                ", creationDate=" + creationDate +
                ", deliveries=" + deliveries +
                ", views=" + views +
                ", tags=" + tags +
                ", likes=" + likes +
                ", commentList=" + commentList +
                ", premium=" + premium +
                ", size=" + size +
                ", n_blocks=" + n_blocks +
                '}';
    }
}
