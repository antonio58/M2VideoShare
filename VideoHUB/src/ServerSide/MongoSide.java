package ServerSide;

import com.mongodb.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by fernando on 16-04-2017.
 */
public class MongoSide {
    private MongoClient mongoClient = null;
    private DB database = null;
    private DBCollection collection = null;

    public MongoSide(){
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        connectDB();
    }

    public MongoSide(MongoClient mongoClient, DB database, DBCollection collection) {
        this.mongoClient = mongoClient;
        this.database = database;
        this.collection = collection;
    }

    private void connectDB(){
        database = mongoClient.getDB("VideoHub");

    }

    public void getCollection(String str){
        collection = database.getCollection(str);
    }

    //Set collection first!
    public void insertToCollection(){
        //collection.insert(new BasicDBObject("field", "value"));
        BasicDBObject document = new BasicDBObject();
        document.put("name", "Fernando");
        document.put("createdDate", new Date());
        collection.insert(document);
    }

    public void updateDocument(){
        BasicDBObject query = new BasicDBObject();
        query.put("name", "Fernando");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("age", "22");

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        collection.update(query, updateObj);
    }

    public void displayAvailableDBs(){
        List<String> dbs = mongoClient.getDatabaseNames();
        for(String db : dbs){
            System.out.println(db);
        }
    }

    public void displayAvailableCollections(){
        Set<String> tables = database.getCollectionNames();

        for(String coll : tables){
            System.out.println(coll);
        }
    }


    public void terminate(){
        mongoClient.close();
    }
}
