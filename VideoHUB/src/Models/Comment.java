package Models;

/**
 * Created by fernando on 21-04-2017.
 */
public class Comment {
    private String text = null;
    private String user_id = null;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Comment(String text, String user_id) {
        this.text = text;
        this.user_id = user_id;
    }

}
