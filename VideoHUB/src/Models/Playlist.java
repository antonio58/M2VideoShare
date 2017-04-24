package Models;

/**
 * Created by fernando on 21-04-2017.
 */
public class Playlist {
    String name = null;
    String data = null;
    String videoId = null;
    String videoName = null;

    public Playlist(String name, String data, String videoId, String videoName) {
        this.name = name;
        this.data = data;
        this.videoId = videoId;
        this.videoName = videoName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }


}
