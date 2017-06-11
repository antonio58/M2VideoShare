package TESTS;

import Models.Channel;
import Models.Playlist;
import Models.User;
import ServerSide.UserTasks;
import org.bson.types.ObjectId;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fernando on 25-04-2017.
 */
public class TEST_USERS {

    private static User user = new User();
    private static ArrayList<ObjectId> watchlist = new ArrayList<>();
    private static ArrayList<ObjectId> seenList = new ArrayList<>();
    private static ArrayList<Channel> channelList = new ArrayList<>();
    private static ArrayList<Playlist> playList = new ArrayList<>();

    public static void main(String[] args) throws NoSuchAlgorithmException {

        //TODO avatar stuff and hashing the password / sign
        user = populateUserModel(false, "Ze Foguetao", "etc/images/ava.png", "foguetao@hotmil.com", "@2123ewqfff");
        UserTasks userTasks = new UserTasks(user);
        userTasks.addUser();
        user.set_id(userTasks.getUserID());
        watchlist = populateWatchlist();
        seenList = populateSeenList();
        channelList = populateUserChannels();
        playList = createUserPlaylists();

        user.setChannels(channelList);
        user.setWatchedlist(seenList);
        user.setWatchlist(watchlist);
        user.setPlaylists(playList);

        //populates DB with dummy arrays
        for (ObjectId aWatchlist : watchlist) { //for each
            userTasks.setWatchLater("_id", user.get_id(), aWatchlist);
        }

        for (ObjectId aSeenList : seenList) { //for each
            userTasks.setWatchlist("_id", user.get_id(), aSeenList);
        }

        for (Channel aChannelList : channelList) { //for each
            userTasks.createChannel("_id", aChannelList);
        }

        for (Channel aChannelList : channelList) {
            userTasks.addToChannel("_id", aChannelList);
        }

        //create new Playlists
        for (Playlist aPlayList : playList) { //for each
            userTasks.createPlaylist("_id", aPlayList);
        }

        for (Playlist aPlayList : playList) {
            userTasks.addToPlaylist("_id", aPlayList);
        }
    }

    private static User populateUserModel(boolean bool, String name, String avatar_path, String email, String hash){
        User user = new User();
        user.setPremium(bool);
        user.setName(name);
        user.setAvatar(avatar_path);
        user.setEmail(email);
        user.setHashPassword(hash);
        return user;
    }


    private static ArrayList<ObjectId> populateWatchlist(){
        ArrayList<ObjectId> watchlist = new ArrayList<>();
        watchlist.add(new ObjectId("58dfaa9c779fe878e07abf63"));
        watchlist.add(new ObjectId("58fe76771fb2bf25f3abd385"));
        watchlist.add(new ObjectId("58fe7d0b1fb2bf4764146c7a"));
        return watchlist;
    }

    private static ArrayList<ObjectId> populateSeenList(){
        ArrayList<ObjectId> seenList = new ArrayList<>();
        seenList.add(new ObjectId("58dfaa9c779fe878e07abf63"));
        seenList.add(new ObjectId("58fe880e1fb2bf084281200d"));
        return seenList;
    }

    private static ArrayList<Channel> populateUserChannels(){
        ArrayList<Channel> userChannels = new ArrayList<>();
        ArrayList<ObjectId> objectIds = new ArrayList<>();

        objectIds.add(new ObjectId("58dfaa9c779fe878e07abf63"));

        userChannels.add(new Channel(user.get_id(), "Animais", new Date(), objectIds));
        userChannels.add(new Channel(user.get_id(), "Informática", new Date(), objectIds));

        return userChannels;
    }

    private static ArrayList<Playlist> createUserPlaylists(){
        ArrayList<Playlist> playlists = new ArrayList<>();
        ArrayList<ObjectId> objectIds = new ArrayList<>();

        objectIds.add(new ObjectId("58fe880e1fb2bf084281200d"));

        playlists.add(new Playlist(user.get_id(), "Animais", new Date(), objectIds, 0));
        playlists.add(new Playlist(user.get_id(), "Lazer", new Date(), objectIds, 1));
        return playlists;
    }

    private static ArrayList<ObjectId> populateObjectIDs(){
        ArrayList<ObjectId> objectIds = new ArrayList<>();
        objectIds.add(new ObjectId("58dfaa9c779fe878e07abf63"));
        return objectIds;
    }
}
