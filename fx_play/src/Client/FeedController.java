package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Project fx_play was created by
 * António Lourenço on 21, April of 2017.
 */
public class FeedController implements Initializable{
    @FXML private Text actiontarget;
    @FXML private HBox actiontargetBox;

    private Main application;
    private ServerComm sc;


    public void setApp(Main application, ServerComm sc){
        this.application = application;
        this.sc = sc;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actiontargetBox.setVisible(false);
    }

    @FXML
    protected void handleSubmitBack(ActionEvent event){
        this.application.openMainMenu();
    }

    @FXML
    protected void handleSubmitFSub(ActionEvent event) {
        actiontarget.setText("Subscribed button pressed");
        actiontargetBox.setVisible(true);
    }

    @FXML
    protected void handleSubmitFAll(ActionEvent event) {
        actiontarget.setText("All button pressed");
        actiontargetBox.setVisible(true);
    }

}
