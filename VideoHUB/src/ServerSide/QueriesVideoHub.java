package ServerSide;

import com.mongodb.BasicDBObject;
import org.bson.Document;

import java.util.Date;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * Created by fernando on 20-04-2017.
 */
public class QueriesVideoHub {

    private MongoSide mMongo = new MongoSide();

    public QueriesVideoHub(){
        deleteUser("name","fernando guima");
        addUser("fernando guima", "lolol@loladamix.com", true);
        addUserChannel("name","fernando guima", "space");
        addUserChannel("name","fernando guima", "sliced fruits");
        createPlaylist("name","fernando guima", "minha Playlist");
        createPlaylist("name","fernando guima", "minha Playlist 2");
        addToPlaylist("name","fernando guima", "minha Playlist", "video1");
        addToPlaylist("name","fernando guima", "minha Playlist", "video2");

    }


    /**
     * Queries -'users'
     * */

    public void addUser(String name, String email, boolean premium){
        LOGGER.info("Query: User added");
        Document document = new Document()
                .append("name", name)
                .append("premium",premium)
                .append("email",email)
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
        mChannel.put("channel_id", "3");
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
        mPlaylist.put("videos", null);

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$push", new BasicDBObject("Playlists", mPlaylist));

        mMongo.getCollection("users").updateOne(query, updateObj);
    }

    //Adds new object to seen videos list
    public void addToPlaylist(String parameter, String value, String playListName, String video_id){
        BasicDBObject query = new BasicDBObject(parameter, value);
        //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
        query.put("Playlist", "videos");

        BasicDBObject mObject = new BasicDBObject();
        mObject.put("video_id", video_id);
        mObject.put("creationDate", new Date());

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$push", new BasicDBObject("videos", mObject));

        mMongo.getCollection("users").updateOne(query, updateObj);
    }


    /**
     * Queries - 'videos'
     * */
}
