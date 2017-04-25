package ServerSide;

import Models.Delivery;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * Created by fernando on 24-04-2017.
 * Queries - 'delivery'
 * */
public class DeliveryTasks {
    private MongoSide mMongo = new MongoSide();
    private Delivery delivery = new Delivery();

    public DeliveryTasks(){

    }

    public void DeliveryTasks(Delivery delivery){
        this.delivery = delivery;
        //espera ativa pelo estado "completed" da delivery, assim que atinja esse estado a delivery é arquivada.



    }

    public void updateDelivery(String parameter, String updateValue){
            LOGGER.info("updateDocument");
            BasicDBObject query = new BasicDBObject();
            //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
            query.put("_id", delivery.get_id());

            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put(parameter, updateValue);

            BasicDBObject updateObj = new BasicDBObject();
            updateObj.put("$set", newDocument);

            mMongo.getCollection("delivery").updateOne(query, updateObj);
    }
    //add delivery to DB
    public void addDelivery(){
        LOGGER.info("Query: addDelivery Task");
        Document document = new Document()
                .append("id_video", delivery.getId_video())
                .append("id_user", delivery.getId_user())
                .append("IP", delivery.getIp_adress())
                .append("state", delivery.getState())
                .append("creationDate", delivery.setDate())
                .append("hops", delivery.getHops()) //piviasleague path, fazer exec de um traceroute ao IP cliente
                .append("idBlock", delivery.getId_block());
        mMongo.getCollection("delivery").insertOne(document);
    }

    public void deleteDelivery(){
        Document searchQuery = new Document();
        searchQuery.put("_id", delivery.get_id());
        mMongo.getCollection("delivery").deleteOne(searchQuery);
    }


    // Searches by video name and author id to achieve unique final ID
    public void getDeliveryID(){
        if(delivery == null || delivery.getId_user() == null | delivery.getId_video() == null){
            System.out.println("getDeliveryID() in DeliveryTasks.java --- delivery null");
        }
        else{
            System.out.println("Delivery videoID: " + delivery.getId_video() +" userID: " + delivery.getId_user());
            Bson filter = Filters.eq("id_video", delivery.getId_video());
            Bson filter2 = Filters.eq("id_user", delivery.getId_user());
            Bson filter3 = Filters.eq("creationDate", delivery.getDate());


            //returns to Results list, playlist name
            List<Document> results = mMongo.getCollection("delivery").aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.match(filter2), Aggregates.match(filter3), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("_id", "$_id")))))).into(new ArrayList<>());


            //must return just 1 result
            //System.out.println("\n number of video with matched name: "+ results.size());
            //System.out.println("\n results "+ results.get(0).getObjectId("_id"));
            delivery.set_id(results.get(0).getObjectId("_id"));
        }
    }
    public void updateState(){
        switch (delivery.getState()){
            case "completed":
                if(delivery.getId_block()>delivery.getTotalBlocks()){
                    //updates mongodb document
                    updateDelivery("state", "completed");
                }
                break;
            case "pending":
                if(delivery.getId_block()<delivery.getTotalBlocks() && delivery.getId_block() <= 0){
                    updateDelivery("state", "pending");
                }
                break;
            case "canceled":
        }
    }
}
