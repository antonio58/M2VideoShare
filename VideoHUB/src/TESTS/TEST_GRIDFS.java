package TESTS;

import ClientSide.UI.VideoPlayer;
import Models.Hop;
import Models.Video;
import ServerSide.DeliveryTasks;
import ServerSide.MongoSide;

import ServerSide.ServerClientHandler;
import ServerSide.VideoTasks;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.bson.types.Binary;


import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rafael on 25-05-2017.
 */

public class TEST_GRIDFS {
    private MongoSide mMongo = new MongoSide();
    private MongoCollection<Document> collection = null;

    public void test() throws IOException {
        collection = mMongo.getCollection("fs.chunks");
        //TODO: É preciso alterar aqui o ObjectId que corresponde ao video
        Bson filter = Filters.eq("files_id", new ObjectId("5937c430d2bccd4c8f30b750"));
        /* Results fica com todos os chunks que correspondem a este video. */
        List<Document> results = collection.aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("data", "$data")))))).into(new ArrayList<>());
        //Escolhe o ficheiro para qual serão escritos os chunks.
        FileOutputStream fos = new FileOutputStream(new File("abc.mp4"),true);
        Binary aux;
        Binary aux2;
        //Saca os chunks e escreve no ficheiro
        for(int i = 0; i < results.size(); i++) {
            aux = (Binary)results.get(i).get("data");
            fos.write(aux.getData());
        }

        fos.close();
        System.out.println(results.size());

//        saveFile("/home/rafael/Documentos/videos/MyHorseisAmazing.mp4");
    }




    public void saveFile(String path) throws IOException {
        MongoClient client = new MongoClient();
        File fich = new File(path);
        String filename = path.substring(path.lastIndexOf("/")+1, path.length());
        GridFS gridFS = new GridFS(client.getDB("VideoHub"));
        GridFSInputFile in = gridFS.createFile(fich);
        in.setChunkSize(8000);
        in.setFilename(filename);
        in.save();
    }
}