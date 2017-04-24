package Models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fernando on 24-04-2017.
 */
public class Delivery {


    private String _id;
    private String id_video;
    private String id_user;
    private String ip_adress;
    private String state;
    private int id_block;
    private int totalBlocks;
    private Date date;
    private ArrayList<Hops> hops;

    public void Delivery(){

    }


    public Delivery( String _id, String id_video, String id_user, String ip_adress, String state, int id_block, int totalBlocks, Date date, ArrayList<Hops> hops) {
        this._id = _id;
        this.id_video = id_video;
        this.id_user = id_user;
        this.ip_adress = ip_adress;
        this.state = state;
        this.id_block = id_block;
        this.totalBlocks = totalBlocks;
        this.date = date;
        this.hops = hops;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getId_video() {
        return id_video;
    }

    public void setId_video(String id_video) {
        this.id_video = id_video;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getIp_adress() {
        return ip_adress;
    }

    public void setIp_adress(String ip_adress) {
        this.ip_adress = ip_adress;
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

    public Date setDate() {
        return new Date();
    }

    public ArrayList<Hops> getHops() {
        return hops;
    }

    public void setHops(ArrayList<Hops> hops) {
        this.hops = hops;
    }

}
