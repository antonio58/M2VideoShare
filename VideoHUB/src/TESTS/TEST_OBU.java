package TESTS;

import Models.Obu;
import ServerSide.ObuTasks;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fernando on 25-04-2017.
 */
public class TEST_OBU {
    private static Obu obu = new Obu();
    private static ArrayList<ObjectId> allowedUsers = new ArrayList<>();

    public static void main(String[] args){
        allowedUsers = populateAllowedUsers();
        obu = populateObuModel(0.92, "/etc/path/pk.txt", true, 4, "2001:690:2280:82a::4",new Date(), new Date(), allowedUsers , 5.5 ,"6661");

        ObuTasks obuTasks = new ObuTasks(obu);
        obuTasks.addObu();
        obuTasks.getObuID();

        obu = populateObuModel(0.85,"/etc/path2/pk.txt", true, 7, "2001:690:2280:82a::1",new Date(), new Date(), allowedUsers , 4.00 ,"9671");

        obuTasks = new ObuTasks(obu);
        obuTasks.addObu();

    }

    private static Obu populateObuModel(Double successRate, String path, boolean state, int badResult, String ipAddress, Date creationDate, Date resetDate, ArrayList<ObjectId> allowedUsers, double timeout, String pin){
        Obu obu = new Obu();
        obu.setState(state);
        obu.setBadResults(badResult);
        obu.setIpAddress(ipAddress);
        obu.setCreationDate(creationDate);
        obu.setLastResetDate(resetDate);
        obu.setUsersAllowed(allowedUsers);
        obu.setPublickey(path);
        obu.setSuccessRate(successRate);
        obu.setTimeout(timeout);
        obu.setPin(pin);
        return obu;
    }

    private static ArrayList<ObjectId> populateAllowedUsers(){
        ArrayList<ObjectId> list = new ArrayList<>();
        list.add(new ObjectId("58fed5ff1fb2bf295e2cc9bc"));
        list.add(new ObjectId("58fed6241fb2bf2a7c12d958"));
        return list;
    }
}
