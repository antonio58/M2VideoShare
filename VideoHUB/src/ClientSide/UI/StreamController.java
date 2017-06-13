package ClientSide.UI;

import ClientSide.ServerComm;
import ClientSide.DownloadHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import javafx.scene.control.Button;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

/**
 * Created by rafael on 25-04-2017.
 */
public class StreamController implements Initializable {
    @FXML private Button btnButton;
    @FXML private GridPane gridPane;
    @FXML Text author;
    @FXML Text title;
    @FXML
    TextArea description;
    @FXML
    TextArea comments;
    @FXML
    TextArea new_comment;
    private Main application;
    private ServerComm sc;
    String id;
    String title_;

    public void setApp(Main application, ServerComm s, List<String> data){
        this.application = application;
        this.sc = s;
        System.out.println("Stream data: "+data.toString());
        title.setText(data.get(1));
        author.setText(data.get(0));
        this.title_ = data.get(0);
        this.id = data.get(2);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void handleSubmitBack(ActionEvent event) {
        String data = sc.getFeed(1, (byte) 5);
        this.application.openMainMenu(1, data, (byte) 5);
    }

    @FXML
    protected void handleLogout(ActionEvent event) {
        this.application.openWelcome();
    }

    @FXML
    protected void handlePlay(ActionEvent event){

        /*String aux = id;//String.valueOf(this.id);
        String[] aux2 = {aux};
        System.out.println("handlePlay: "+aux);
        aux = sc.buildFrame((byte)8, aux2);
        aux = sc.talk(aux);
        List<String> aux3 = sc.readFrame(aux);
        aux = aux3.get(0);*/
        /*int i = aux.lastIndexOf("/");
        aux = aux.substring(i,aux.length());*/

        //Correr thread download aqui
        DownloadHandler dh = new DownloadHandler(sc, this.id);
        Thread DH = new Thread(dh);

        DH.start();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.application.openPlayer(gridPane, this.id);
    }
}
