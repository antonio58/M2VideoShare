package Models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fernando on 24-04-2017.
 */
public class Delivery {


    private ObjectId _id;
    private ObjectId id_video;
    private ObjectId id_user;
    private String ip_address;
    private String state;
    private int id_block;
    private int totalBlocks;
    private Date date;
    private ArrayList<Hop> hops;

    public Delivery(){}



    public void Delivery(ObjectId _id, ObjectId id_video, ObjectId id_user, String ip_address, String state, int id_block, int totalBlocks, Date date, ArrayList<Hop> hops) {
        this._id = _id;
        this.id_video = id_video;
        this.id_user = id_user;
        this.ip_address = ip_address;
        this.state = state;
        this.id_block = id_block;
        this.totalBlocks = totalBlocks;
        this.date = date;
        this.hops = hops;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }


    public ObjectId getId_video() {
        return id_video;
    }

    public void setId_video(ObjectId id_video) {
        this.id_video = id_video;
    }

    public ObjectId getId_user() {
        return id_user;
    }

    public void setId_user(ObjectId id_user) {
        this.id_user = id_user;
    }

    public String getIPaddress() {
        return ip_address;
    }

    public void setIPaddress(String ip_adress) {
        this.ip_address = ip_adress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId_block() {
        return id_block;
    }

    public void setId_block(int id_block) {
        this.id_block = id_block;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    public void setTotalBlocks(int totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Hop> getHops() {
        return hops;
    }

    public void setHops(ArrayList<Hop> hops) {
        this.hops = hops;
    }

}
