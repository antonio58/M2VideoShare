package ServerSide;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * Created by fernando on 24-04-2017.
 */
public class ObuQueriesVideosHub {

    private MongoSide mMongo = new MongoSide();

    public ObuQueriesVideosHub(){
        //deleteUser("name","fernando guima");
        //addUser("fernando guima", "lolol@loladamix.com", true);
        //addUserChannel("name","fernando guima", "space");
        //addUserChannel("name","fernando guima", "sliced fruits");
        //createPlaylist("name","fernando guima", "minha Playlist");
        //createPlaylist("name","fernando guima", "minha Playlist 2");
        //addToPlaylist("name","fernando guima", "minha Playlist", "video1");
        //addToPlaylist("name","fernando guima", "video 69", "minha Playlist 2");
    }
    /**
     * Queries - 'obu'
     * */

    public void addObu(double timeout, boolean active, String path, ArrayList<String> list){
        LOGGER.info("Query: Obu added");
        Document document = new Document()
                .append("OBU_timeout", timeout)
                .append("OBU_active",active)
                .append("Public_Key",path)
                .append("creationDate", new Date())
                .append("OBU_Keys", list);
        mMongo.getCollection("users").insertOne(document);
    }

    public void deleteObu(){

    }

    public void updateObu(){

    }
}
