package Models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fernando on 25-04-2017.
 */
public class Obu {

    private ObjectId _id;
    private String publickey; //path
    private String ipAddress;
    private String pin;
    private double successRate;
    private double timeout;
    private boolean state;
    private int badResults;
    private Date creationDate;
    private Date lastResetDate;
    private ArrayList<ObjectId> usersAllowed; //'user_id's

    public Obu(){}

    public Obu(ObjectId _id, String publickey, String ipAddress, String pin, double successRate, double timeout, boolean state, int badResults, Date creationDate, Date lastResetDate, ArrayList<ObjectId> usersAllowed) {
        this._id = _id;
        this.publickey = publickey;
        this.ipAddress = ipAddress;
        this.pin = pin;
        this.successRate = successRate;
        this.timeout = timeout;
        this.state = state;
        this.badResults = badResults;
        this.creationDate = creationDate;
        this.lastResetDate = lastResetDate;
        this.usersAllowed = usersAllowed;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getBadResults() {
        return badResults;
    }

    public void setBadResults(int badResults) {
        this.badResults = badResults;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastResetDate() {
        return lastResetDate;
    }

    public void setLastResetDate(Date lastResetDate) {
        this.lastResetDate = lastResetDate;
    }

    public ArrayList<ObjectId> getUsersAllowed() {
        return usersAllowed;
    }

    public void setUsersAllowed(ArrayList<ObjectId> usersAllowed) {
        this.usersAllowed = usersAllowed;
    }

    public double getTimeout() {
        return timeout;
    }

    public void setTimeout(double timeout) {
        this.timeout = timeout;
    }
}
