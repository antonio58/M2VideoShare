package TESTS;

import Models.User;
import ServerSide.UserTasks;
import org.bson.types.ObjectId;

import java.util.ArrayList;

/**
 * Created by fernando on 25-04-2017.
 */
public class TEST_USERS {

    private static User user = new User();
    private static ArrayList<ObjectId> watchlist = new ArrayList<>();
    private static ArrayList<ObjectId> seenList = new ArrayList<>();
    private static ArrayList<String> likes = new ArrayList<>();

    public static void main(String[] args){
        watchlist = populateWatchlist();
        seenList = populateSeenList();
        user = populateUserModel(false, "Fernando Guimaraẽs", "etc/images/ava.png", "ze_foguetao@hotmil.com", "@2123ewqfff");
        UserTasks userTasks = new UserTasks(user);
        userTasks.addUser();
        user.set_id(userTasks.getUserID());
        //videoTasks.getVideoID(video);
        //videoTasks.addVideo();
        for(int i=0; i <watchlist.size(); i++ ) {
            userTasks.setWatchLater("_id", user.get_id(),watchlist.get(i));
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

}
