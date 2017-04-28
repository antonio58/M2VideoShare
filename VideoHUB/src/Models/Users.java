package Models;

import java.util.ArrayList;

/**
 * Created by fernando on 21-04-2017.
 */
public class Users {
    String name = null;
    String email = null;
    String _id = null;
    ArrayList<String> channels = null;
    boolean premium = false;
    String hashPassword = null;
    String avatar = null;
    ArrayList<String> watchlist = null;
    ArrayList<String> watchedlist = null;
    ArrayList<Playlist> playlists = null;

    public Users(){};

    public Users(String name, String email, String _id, ArrayList<String> channels, boolean premium, String hashPassword, String avatar, ArrayList<String> watchlist, ArrayList<String> watchedlist, ArrayList<Playlist> playlists) {
        this.name = name;
        this.email = email;
        this._id = _id;
        this.channels = channels;
        this.premium = premium;
        this.hashPassword = hashPassword;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<String> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<String> channels) {
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

    public ArrayList<String> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(ArrayList<String> watchlist) {
        this.watchlist = watchlist;
    }

    public ArrayList<String> getWatchedlist() {
        return watchedlist;
    }

    public void setWatchedlist(ArrayList<String> watchedlist) {
        this.watchedlist = watchedlist;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

}
