package ServerSide;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fernando on 07-06-2017.
 */
public class ChunkTasks {
    private MongoSide mMongo = new MongoSide();
    private MongoCollection<Document> collection = null;
    private ObjectId videoId;
    private String id;

    public ChunkTasks(){}

    public ChunkTasks(ObjectId videoId){
        this.videoId = videoId;
        collection = mMongo.getCollection("fs.chunks");


    }

    public void getChunks() throws IOException {
        //TODO: É preciso alterar aqui o ObjectId que corresponde ao video
        Bson filter = Filters.eq("files_id",videoId);
        /* Results fica com todos os chunks que correspondem a este video. */
        List<Document> results = collection.aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.project(Projections.fields(Arrays.asList(Projections.computed("data", "$data")))))).into(new ArrayList<>());
        //Escolhe o ficheiro para qual serão escritos os chunks. Vai buscar à base de dados o nome do video
        VideoTasks videoTasks = new VideoTasks();

        FileOutputStream fos = new FileOutputStream(new File(videoTasks.getVideoNameByIndex(videoId)+".mp4"),true);
        Binary aux;
        //Saca os chunks e escreve no ficheiro
        for(int i = 0; i < results.size(); i++) {
            aux = (Binary)results.get(i).get("data");
            fos.write(aux.getData());
        }

        fos.close();
        System.out.println(results.size());
    }




    public void fileToChunks(String path) throws IOException {
        MongoClient client = new MongoClient();
        File file = new File(path);
        String filename = path.substring(path.lastIndexOf("/")+1, path.length());
        GridFS gridFS = new GridFS(client.getDB("VideoHub"));
        GridFSInputFile in = gridFS.createFile(file);
        in.setChunkSize(8000);
        in.setFilename(filename);
        id = String.valueOf(in.get("_id"));
        in.save();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
