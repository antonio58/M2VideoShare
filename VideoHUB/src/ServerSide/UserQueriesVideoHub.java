package ServerSide;

import Models.User;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * Created by fernando on 20-04-2017.
 */
public class UserQueriesVideoHub {

    private MongoSide mMongo = new MongoSide();
    private User user;
    public UserQueriesVideoHub(User mUser){
        //deleteUser("name","fernando guima");
        //addUser("fernando guima", "lolol@loladamix.com", true);
        //addUserChannel("name","fernando guima", "space");
        //addUserChannel("name","fernando guima", "sliced fruits");
        //createPlaylist("name","fernando guima", "minha Playlist");
        //createPlaylist("name","fernando guima", "minha Playlist 2");
        //addToPlaylist("name","fernando guima", "minha Playlist", "video1");
        //addToPlaylist("name","fernando guima", "video 69", "minha Playlist 2");
    this.user = mUser;
    }

    /**
     * Queries -'users'
     * */

    public void addUser(){
        LOGGER.info("Query: User added");
        Document document = new Document()
                .append("name", user.getName())
                .append("premium",user.isPremium())
                .append("email",user.getEmail())
                .append("creationDate", new Date());
        mMongo.getCollection("users").insertOne(document);
    }

    public void deleteUser(String parameter, String value){
        Document searchQuery = new Document();
        searchQuery.put(parameter, value);
        mMongo.getCollection("users").deleteOne(searchQuery);
    }

    public void updateUser(String parameter, String value, String parameter2, String updateValue){
        LOGGER.info("updateDocument");
        BasicDBObject query = new BasicDBObject();
        //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
        query.put(parameter, value);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(parameter2, updateValue);

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        mMongo.getCollection("users").updateOne(query, updateObj);
    }

    public void addUserChannel(String parameter, String value, String channel){
        BasicDBObject query = new BasicDBObject();
        //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
        query.put(parameter, value);

        BasicDBObject mChannel = new BasicDBObject();
        mChannel.put("channel_id", getNextChannelIndex(parameter, value));
        mChannel.put("channels", channel);
        mChannel.put("creationDate", new Date());

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$push", new BasicDBObject("channel", mChannel));

        mMongo.getCollection("users").updateOne(query, updateObj);
    }

    //Adds new object to seen videos list
    public void toWatchlist(String parameter, String value, String video_id){
        BasicDBObject query = new BasicDBObject();
        //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
        query.put(parameter, value);

        BasicDBObject mObject = new BasicDBObject();
        mObject.put("video_id", video_id);
        mObject.put("creationDate", new Date());

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$push", new BasicDBObject("watchList", mObject));

        mMongo.getCollection("users").updateOne(query, updateObj);
    }

    //Adds new object to seen videos list
    public void toWatchLater(String parameter, String value, String video_id){
        BasicDBObject query = new BasicDBObject();
        //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
        query.put(parameter, value);

        BasicDBObject mObject = new BasicDBObject();
        mObject.put("video_id", video_id);
        mObject.put("creationDate", new Date());

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$push", new BasicDBObject("watchLater", mObject));

        mMongo.getCollection("users").updateOne(query, updateObj);
    }

    public void createPlaylist(String parameter, String value, String nameOfPlaylist){
        BasicDBObject query = new BasicDBObject();
        //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
        query.put(parameter, value);

        BasicDBObject mPlaylist = new BasicDBObject();
        mPlaylist.put("name", nameOfPlaylist);
        mPlaylist.put("creationDate", new Date());
        mPlaylist.put("videos", new ArrayList<>());

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$push", new BasicDBObject("playlists", mPlaylist));

        mMongo.getCollection("users").updateOne(query, updateObj);
    }

    //Adds new object to seen videos list
    public void addToPlaylist(String parameter, String value, String video_id, String playlistName){
        //user document
        BasicDBObject query = new BasicDBObject(parameter, value);
        //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
        BasicDBObject mObject = new BasicDBObject();
        mObject.put("video_id", video_id);
        mObject.put("creationDate", new Date());

        BasicDBObject updateObj = new BasicDBObject();

        int count = getPlaylistIndex(parameter, value, playlistName);
        System.out.println("playlist index :" + count);
        //falta percorrer o array das playlists para encontrar o id pelo nome da playlist
        updateObj.put("$push", new BasicDBObject("playlists."+count+".videos", mObject));

        mMongo.getCollection("users").updateOne(query, updateObj);
    }

    //returns Playlist index given the playlist name
    private int getPlaylistIndex(String parameter, String value, String playListName) {
        // filter by user name or user id e.g, parameter: "name" , value: "fernando guima"
        Bson filter = Filters.eq(parameter, value);
        int i = 0;
        //returns to Results list, playlist name
        List<Document> results = mMongo.getCollection("users").aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.unwind("$playlists"), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("name", "$playlists.name")))))).into(new ArrayList<>());


        System.out.println("\n number of playlists:"+ results.size());
        System.out.println("\n result:"+ results);
        for(i=0; i<results.size(); i++){
            if(results.get(i).getString("name").equals(playListName)){
                return i;
            }
        }
        return -1;
        /*
        List<VideoResult> results2 = mMongo.getCollection("users").aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.unwind("$playlists"), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("videos", new Document("$size","$playlists.videos")),
                Projections.computed("video_ids", "$playlists.videos.video_id")))))).map(VideoResult::new).into(new ArrayList<>());

        System.out.print("\n results:"+ results2);
        System.out.println("\n Playlist content: " + results2.get(0).getVideoIds());
        System.out.println("\n Playlist content: " + results2.get(1).getVideoIds());
        System.out.print("\n results2:"+ results2.size());
        System.out.print("\n results2:"+results2.get(0).getVideos());
        return results.size(); */
    }

    //returns next available channel ID
    private int getNextChannelIndex(String parameter, String value) {
        // filter by user name or user id e.g, parameter: "name" , value: "fernando guima"
        Bson filter = Filters.eq(parameter, value);
        int i = 0;
        //returns to Results list, playlist name
        List<Document> results = mMongo.getCollection("users").aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.unwind("$channel"))).into(new ArrayList<>());

        System.out.println("\n number of channels:"+ results.size());

        if ( results.size() > 0){
            return results.size() - 1;
        }
        else {
            return -1;
        }
        /*
        List<VideoResult> results2 = mMongo.getCollection("users").aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.unwind("$playlists"), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("videos", new Document("$size","$playlists.videos")),
                Projections.computed("video_ids", "$playlists.videos.video_id")))))).map(VideoResult::new).into(new ArrayList<>());

        System.out.print("\n results:"+ results2);
        System.out.println("\n Playlist content: " + results2.get(0).getVideoIds());
        System.out.println("\n Playlist content: " + results2.get(1).getVideoIds());
        System.out.print("\n results2:"+ results2.size());
        System.out.print("\n results2:"+results2.get(0).getVideos());
        return results.size(); */
    }

}
