package ServerSide;

import Models.Obu;
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
 *
 *  private ObjectId _id;
 private String publickey; //path
 private String ipAddress;
 private String pin;
 private double successRate;
 private boolean state;
 private int badResults;
 private Date creationDate;
 private Date lastResetDate;
 private ArrayList<ObjectId> usersAllowed; //'user_id's
 */
public class ObuTasks {

    private Obu obu = new Obu();
    private MongoSide mMongo = new MongoSide();

    public ObuTasks(Obu obu){
        this.obu = obu;
    }
    /**
     * Queries - 'obu'
     * */

    public void addObu(){
        LOGGER.info("Query: Obu added");
        Document document = new Document()
                .append("timeout", obu.getTimeout())
                .append("active",obu.isState())
                .append("Public_Key",obu.getPublickey())
                .append("creationDate", new Date())
                .append("resetDate", new Date()) //reset aos badresults
                .append("badResult", obu.getBadResults())
                .append("ipAddress", obu.getIpAddress())
                .append("successRate", obu.getSuccessRate())
                .append("allowedUsers", obu.getUsersAllowed());
        mMongo.getCollection("obu").insertOne(document);
    }


    public void performReset(){
        LOGGER.info("performObuReset");
        BasicDBObject query = new BasicDBObject();
        //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
        query.put("_id", obu.get_id());

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("resetDate", new Date());
        newDocument.put("badresults", 0);
        newDocument.put("successRate", 0.0);


        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        mMongo.getCollection("obu").updateOne(query, updateObj);
    }

    public void deleteObu(){

    }

    public void updateObu(String parameter, String value){
        LOGGER.info("updateObu");
        BasicDBObject query = new BasicDBObject();
        //First it fetches the wanted document by a parameter and its value e.g, (_id, ObjectID(obu))
        query.put("_id", obu.get_id());

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(parameter, value);

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        mMongo.getCollection("obu").updateOne(query, updateObj);

    }

    // ipaddress is a unique id, ideal for search in users collection
    public ObjectId getObuID(){
        if(obu == null || obu.getCreationDate() == null){
            System.out.println("getObuId() in ObuTasks.java --- obu null");
        }
        else{
            System.out.println("userName: " + obu.getIpAddress() +" creationDate: " + obu.getCreationDate());
            Bson filter = Filters.eq("ipAddress", obu.getIpAddress());

            //output is desired _id of "name" and "email" queries
            List<Document> results = mMongo.getCollection("obu").aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("_id", "$_id")))))).into(new ArrayList<>());

            //must return just one element, unique email a user name.
            //System.out.println("\n number of obu with matched email: "+ results.size());
            //System.out.println("\n results "+ results.get(0).getObjectId("_id"));
            obu.set_id(results.get(0).getObjectId("_id"));
            return results.get(0).getObjectId("_id");

        }
        return null;
    }
}
