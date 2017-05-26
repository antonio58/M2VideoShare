package ServerSide;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by fernando on 16-04-2017.
 */
public class MongoSide {
    private static final Logger LOGGER = Logger.getLogger( MongoSide.class.getName() );
    private MongoClient mongoClient = null;
    private MongoDatabase database = null;
    private MongoCollection<Document> collection = null;


    public MongoSide(){
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = connectDB();
        /*MongoDB stuff display*/
        //displayAvailableDBs();
        //displayAvailableCollections();
        collection = getCollection("users");
    }

    public MongoSide(MongoClient mongoClient, MongoDatabase database, MongoCollection<Document> collection) {
        this.mongoClient = mongoClient;
        this.database = database;
        this.collection = collection;
    }

    private MongoDatabase connectDB(){
        MongoDatabase database = mongoClient.getDatabase("VideoHub");
        return database;
    }

    public MongoCollection<Document> getCollection(String str){

        MongoCollection<Document> collection = database.getCollection(str);
        return collection;
    }

    //Set collection first!
    public void insertToCollection(){
        LOGGER.info("insertToCollection");
        Document document = new Document()
                .append("name", "Fernando")
                .append("createdDate", new Date());
        collection.insertOne(document);
    }

    public void updateDocument(){
        LOGGER.info("updateDocument");
        BasicDBObject query = new BasicDBObject();
        query.put("name", "Fernando");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("age", "22");

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        collection.updateOne(query, updateObj);
    }

    //Find All Documents in a Collection
    public void findDocument(){
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
    }

    public void deleteDocument(){
        Document searchQuery = new Document();
        searchQuery.put("name", "Fernando");
        collection.deleteOne(searchQuery);
    }

    public void displayAvailableDBs(){
        MongoIterable<String> dbs = mongoClient.listDatabaseNames();
        for(String db : dbs){
            System.out.println(db);
        }
    }

    public void displayAvailableCollections(){
        MongoIterable<String> tables = database.listCollectionNames();

        for(String coll : tables){
            System.out.println(coll);
        }
    }


    public void terminate(){
        mongoClient.close();
    }
}