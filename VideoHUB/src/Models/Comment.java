package Models;

import java.util.Date;

/**
 * Created by fernando on 21-04-2017.
 */
public class Comment {
    private String text = null;
    private String user_id = null;
    private Date date = null;
    private int id;

    public Comment(){}

    public Comment(String text, Date date, String user_id) {
        this.text = text;
        this.user_id = user_id;
        this.date = date;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", user_id='" + user_id + '\'' +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
