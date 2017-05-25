package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by rafael on 25-04-2017.
 */
public class StreamController implements Initializable {
    @FXML Text autor;
    @FXML Text titulo;
    @FXML Text descricao;
    @FXML private GridPane gridPane;
    int id;


    private Main application;
    private ServerComm sc;

    public void setApp(Main application, ServerComm s, String data){
        this.application = application;
        this.sc = s;
        String aux[] = data.split("</split/>");
        //this.id = Integer.parseInt(aux[0]);
        autor.setText(aux[0]);
        titulo.setText(aux[1]);
        id = Integer.parseInt(aux[2]);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void handleSubmitBack(ActionEvent event){
        String data = sc.getFeed(1,(byte)5);
        this.application.openMainMenu(1,data,(byte)5);
    }

    @FXML
    protected void handleLogout(ActionEvent event) {
        this.application.openWelcome();
    }

    @FXML
    protected void handlePlay(ActionEvent event){
        String aux = String.valueOf(this.id);
        String[] aux2 = {aux};
        aux = sc.buildFrame((byte)8, aux2);
        List<String> aux3 = sc.readFrame(aux);
        aux = aux3.get(0);
        //Correr thread download aqui
        this.application.openPlayer(gridPane, aux);
    }

}
