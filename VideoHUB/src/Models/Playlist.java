package Models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fernando on 21-04-2017.
 */
public class Playlist {
    private String name;
    private Date date;
    private ObjectId id_user;
    private ArrayList<ObjectId> videoId;
    private int _id;

    public  Playlist(){

    }

    public Playlist(ObjectId id_user, String name, Date date, ArrayList<ObjectId> videoId, int _id) {
        this.id_user = id_user;
        this.name = name;
        this.date = date;
        this.videoId = videoId;
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ObjectId> getVideoId() {
        return videoId;
    }

    public void setVideoId(ArrayList<ObjectId> videoId) {
        this.videoId = videoId;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ObjectId getId_user() {
        return id_user;
    }

    public void setId_user(ObjectId id_user) {
        this.id_user = id_user;
    }
}
