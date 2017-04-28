package ClientSide.UI;

import ClientSide.ServerComm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by rafael on 25-04-2017.
 */
public class StreamController implements Initializable {

    @FXML private java.awt.Button btnButton;
    @FXML private GridPane gridPane;
    @FXML Text autor;
    @FXML Text titulo;
    @FXML Text descricao;
    private Main application;
    private ServerComm sc;

    public void setApp(Main application, ServerComm s, String data){
        this.application = application;
        this.sc = s;
        String aux[] = data.split("</split/>");
        autor.setText(aux[0]);
        titulo.setText(aux[1]);
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
    protected void handlePlay(ActionEvent event){
        this.application.openPlayer(gridPane);
    }
    

    @FXML
    protected void handleLogout(ActionEvent event) {
        this.application.openWelcome();
    }
}
