package Models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fernando on 25-04-2017.
 */
public class Channel {
    private String name;
    private Date date;
    private ObjectId id_user;
    private ArrayList<ObjectId> videoId;

    public  Channel(){

    }

    public Channel(ObjectId id_user, String name, Date date, ArrayList<ObjectId> videoId) {
        this.id_user = id_user;
        this.name = name;
        this.date = date;
        this.videoId = videoId;
    }


    public ObjectId getId_user() {
        return id_user;
    }

    public void setId_user(ObjectId id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getData() {
        return date;
    }

    public void setData(Date data) {
        this.date = data;
    }

    public ArrayList<ObjectId> getVideoId() {
        return videoId;
    }

    public void setVideoId(ArrayList<ObjectId> videoId) {
        this.videoId = videoId;
    }

}