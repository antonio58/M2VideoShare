package Models;

import ClientSide.DataHandle;
import org.bson.types.ObjectId;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by fernando on 21-04-2017.
 */
public class User {
    String name = null;
    String email = null;
    ObjectId _id = null;
    ArrayList<Channel> channels = null;
    boolean premium = false;
    String hashPassword = null;
    String avatar = null;
    ArrayList<ObjectId> watchlist = null;
    ArrayList<ObjectId> watchedlist = null;
    ArrayList<Playlist> playlists = null;

    public User(){};

    public User(String name, String email, ObjectId _id, ArrayList<Channel> channels, boolean premium, String hashPassword, String avatar, ArrayList<ObjectId> watchlist, ArrayList<ObjectId> watchedlist, ArrayList<Playlist> playlists) throws NoSuchAlgorithmException {
        this.name = name;
        this.email = email;
        this._id = _id;
        this.channels = channels;
        this.premium = premium;
        this.hashPassword = DataHandle.getSHA1Hex(hashPassword);
        this.avatar = avatar;
        this.watchlist = watchlist;
        this.watchedlist = watchedlist;
        this.playlists = playlists;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<ObjectId> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(ArrayList<ObjectId> watchlist) {
        this.watchlist = watchlist;
    }

    public ArrayList<ObjectId> getWatchedlist() {
        return watchedlist;
    }

    public void setWatchedlist(ArrayList<ObjectId> watchedlist) {
        this.watchedlist = watchedlist;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

}
