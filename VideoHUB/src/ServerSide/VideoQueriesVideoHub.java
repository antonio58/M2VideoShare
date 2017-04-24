package ServerSide;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * Created by fernando on 24-04-2017.
 */
public class VideoQueriesVideoHub {

    private MongoSide mMongo = new MongoSide();

    public VideoQueriesVideoHub(){

    }
    /**
     * Queries - 'videos'
     * */

    public void addVideo(String name, String userName, String category, ArrayList<String> tags, boolean premium){
        LOGGER.info("Query: Video added");
        Document document = new Document()
                .append("name", name)
                .append("category", category)
                .append("tags", tags)
                .append("author",userName)
                .append("creationDate", new Date())
                .append("premium",premium)
                .append("comments", new ArrayList<String>())
                .append("file", null) //string
                .append("size", null) //double
                .append("duration", null)
                .append("nBlocks", null)
                .append("views", new ArrayList<>());
        mMongo.getCollection("videos").insertOne(document);
    }

    public void deleteVideo(ObjectId objectId){
        Document searchQuery = new Document();
        searchQuery.put("_id", objectId);
        mMongo.getCollection("videos").deleteOne(searchQuery);
    }
}
