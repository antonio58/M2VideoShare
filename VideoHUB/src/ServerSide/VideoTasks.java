package ServerSide;

import Models.Video;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * Created by fernando on 24-04-2017.
 */
public class VideoTasks{

    private MongoSide mMongo = new MongoSide();
    Video video = new Video();


    public VideoTasks(Video video){
        this.video = video;
    }
    /**
     * Queries - 'videos'
     * */

    public void addVideo(Video video){
        LOGGER.info("Query: Video added");
        Document document = new Document()
                .append("name", video.getName())
                .append("category", video.getCategory())
                .append("tags", video.getTags())
                .append("author",video.getAuthor())
                .append("creationDate", new Date())
                .append("premium",video.isPremium())
                .append("comments",video.getCommentList())
                .append("file", video.getFile()) //string
                .append("size", video.getSize()) //double
                .append("duration", video.getDuration())
                .append("nBlocks", video.getN_blocks())
                .append("views", video.getViews());
        mMongo.getCollection("videos").insertOne(document);
    }

    public void deleteVideo(ObjectId objectId){
        Document searchQuery = new Document();
        searchQuery.put("_id", objectId);
        mMongo.getCollection("videos").deleteOne(searchQuery);
    }
}
