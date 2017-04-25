package ServerSide;

import Models.Comment;
import Models.Video;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * Created by fernando on 24-04-2017.
 */
public class VideoTasks{

    private MongoSide mMongo = new MongoSide();
    private Video video = new Video();


    public VideoTasks(Video video){
        this.video = video;
    }

    /**
     * Queries - 'videos'
     * */

    //Add video to database
    public void addVideo(){
        LOGGER.info("Query: Video added");
        Document document = new Document()
                .append("name", video.getName())
                .append("category", video.getCategory())
                .append("tags", video.getTags())
                .append("author",video.getAuthor())
                .append("creationDate", new Date())
                .append("premium",video.isPremium())
                .append("file", video.getFile()) //string
                .append("size", video.getSize()) //double
                .append("duration", video.getDuration())
                .append("nBlocks", video.getN_blocks())
                .append("views", video.getViews());
        mMongo.getCollection("videos").insertOne(document);

        //set video._idUser;

    }
    public void addVideoComment(Comment comment){
        BasicDBObject query = new BasicDBObject();
        //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
        query.put("_id", video.get_id());

        BasicDBObject mComment = new BasicDBObject();
        mComment.put("_idComment", getNextComment(video.get_id()));
        mComment.put("comment", comment.getText());
        mComment.put("creationDate", new Date());

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$push", new BasicDBObject("comments", mComment));

        mMongo.getCollection("videos").updateOne(query, updateObj);
    }

    //returns next available channel ID
    private int getNextComment(ObjectId id) {
        Bson filter = Filters.eq("_id", id);

        //returns to Results list, playlist name
        List<Document> results = mMongo.getCollection("videos").aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.unwind("$comments"))).into(new ArrayList<>());

        System.out.println("\n number of comments:"+ (results.size()+1));

        if ( (results.size()+1) > 0){
            return results.size();
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

    // Searches by video name and author id to achieve unique final ID
    // Beware to first addVideo()!
    public void getVideoID(){
        if(video == null || video.getAuthor() == null | video.getName() == null){
            System.out.println("getVideoID() in VideoTaks.java --- video null");
        }
        else{
            System.out.println("videoName: " + video.getName() +" author: " + video.getAuthor());
            Bson filter = Filters.eq("name", video.getName());
            Bson filter2 = Filters.eq("author", video.getAuthor());


            //returns to Results list, playlist name
            List<Document> results = mMongo.getCollection("videos").aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.match(filter2), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("_id", "$_id")))))).into(new ArrayList<>());


            //must return just 1 result
            //System.out.println("\n number of video with matched name: "+ results.size());
            //System.out.println("\n results "+ results.get(0).getObjectId("_id"));
            video.set_id(results.get(0).getObjectId("_id"));
        }
    }


    //first get VideoID before deleting video
    public void deleteVideo(){
        Document searchQuery = new Document();
        searchQuery.put("_id", video.get_id());
        mMongo.getCollection("videos").deleteOne(searchQuery);
    }
}
