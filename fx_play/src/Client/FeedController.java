package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Project fx_play was created by
 * António Lourenço on 21, April of 2017.
 */
public class FeedController implements Initializable{
    private Main application;
    private ServerComm sc;


    public void setApp(Main application, ServerComm sc){

        this.sc = sc;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void handleSubmitBack(ActionEvent event){
        this.application.openMainMenu();
    }


}
