package TESTS;

import Models.Delivery;
import Models.Hop;
import ServerSide.DeliveryTasks;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fernando on 25-04-2017.
 */
public class TEST_DELIVERY {
    private static Delivery delivery = new Delivery();
    private static ArrayList<Hop> hops = new ArrayList<>();

    public static void main(String[] args){
        hops = populateHops();
        delivery = populateDeliveryModel("completed", 244, new ObjectId("58fed5ff1fb2bf295e2cc9bc"), hops, "192.168.1.5", 244, new ObjectId("58fed7eb1fb2bf35a1888e10"));
        DeliveryTasks deliveryTasks = new DeliveryTasks(delivery);
        deliveryTasks.addDelivery();
        delivery.set_id(deliveryTasks.getDeliveryID());


        for(Hop aHop : hops){
            deliveryTasks.addHopTo(aHop);
        }

    }

    private static Delivery populateDeliveryModel(String state, int totalBlocks, ObjectId user_id, ArrayList<Hop> hops, String IPaddress, int lastBlock, ObjectId video_id){
        Delivery delivery = new Delivery();
        delivery.setState(state);
        delivery.setTotalBlocks(totalBlocks);
        delivery.setId_user(user_id);
        delivery.setId_video(video_id);
        delivery.setIPaddress(IPaddress);
        delivery.setId_block(lastBlock);
        delivery.setDate(new Date());
        return delivery;
    }

    private static ArrayList<Hop> populateHops(){
        ArrayList<Hop> hops = new ArrayList<>();
        hops.add(new Hop("192.168.1.1", "192.168.1.2"));
        hops.add(new Hop("192.168.1.2", "192.168.1.3"));
        hops.add(new Hop("192.168.1.3", "192.168.1.4"));
        hops.add(new Hop("192.168.1.4", "192.168.1.5"));
        return hops;
    }

}
