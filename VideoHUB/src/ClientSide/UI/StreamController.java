package ClientSide.UI;

import ClientSide.ServerComm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by rafael on 25-04-2017.
 */
public class StreamController implements Initializable {

    private Main application;
    private ServerComm sc;

    public void setApp(Main application, ServerComm s){
        this.application = application;
        this.sc = s;
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
}
