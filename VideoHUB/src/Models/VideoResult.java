package Models;

import org.bson.Document;

import java.util.List;

/**
 * Created by fernando on 24-04-2017.
 */
public class VideoResult {

    private int videos;
    private List<String> videoIds;

    public VideoResult(Document doc) {
        this.videos = doc.getInteger("videos");
        this.videoIds = (List<String>) doc.get("video_ids");
    }


    public int getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public List<String> getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(List<String> videoIds) {
        this.videoIds = videoIds;
    }
}
