package Models;

import org.bson.types.ObjectId;

import java.util.ArrayList;

/**
 * Created by fernando on 21-04-2017.
 */
public class Video {
    ObjectId _id = null;
    String name = null;
    String author = null;
    String category = null;
    String duration = null;
    String file = null;
    ArrayList<String> deliveries = null;
    int views = 0;
    ArrayList<String> tags = null;
    ArrayList<String> likes = null;
    ArrayList<Comment> commentList = null;
    boolean premium = false;
    Double size = null;
    int n_blocks = 0;

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

    public int getViews() {
        return views;
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

}
