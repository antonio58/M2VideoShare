package ServerSide;

import Models.Delivery;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * Created by fernando on 24-04-2017.
 * Queries - 'delivery'
 * */
public class DeliveryTasks {
    private MongoSide mMongo = new MongoSide();
    private Delivery delivery;

    public DeliveryTasks(){

    }

    public void DeliveryTasks(Delivery delivery){
        this.delivery = delivery;

        addDelivery(this.delivery);
        //espera ativa pelo estado "completed" da delivery, assim que atinja esse estado a delivery é arquivada.



    }

    public void updateDelivery(String parameter2, String updateValue){
            LOGGER.info("updateDocument");
            BasicDBObject query = new BasicDBObject();
            //First it fetches the wanted document by a parameter and its value e.g, (name, Fernando)
            query.put("_id", delivery.get_id());

            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put(parameter2, updateValue);

            BasicDBObject updateObj = new BasicDBObject();
            updateObj.put("$set", newDocument);

            mMongo.getCollection("delivery").updateOne(query, updateObj);
    }
    //add delivery to DB
    public void addDelivery(Delivery delivery){

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

    public void deleteDelivery(ObjectId objectId){
        Document searchQuery = new Document();
        searchQuery.put("_id", objectId);
        mMongo.getCollection("delivery").deleteOne(searchQuery);
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
