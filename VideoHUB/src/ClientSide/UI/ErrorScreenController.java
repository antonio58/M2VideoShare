package ClientSide.UI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Utilizador on 20/04/2017.
 */
public class ErrorScreenController implements Initializable{

    @FXML
    private Text actiontarget;
    @FXML private HBox actiontargetBox;

    private String errorMessage;
    private Main application;

    public void setApp(Main application, String str){
        this.application = application;
        this.errorMessage = str;
        actiontarget.setText(str);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actiontargetBox.setVisible(true);
        //actiontarget.setText("Can't connect to the server");

    }

    @FXML protected void handleExit(ActionEvent event) {
        actiontarget.setText("Exit button pressed");
        actiontargetBox.setVisible(true);
        Platform.exit();
    }

    @FXML protected void handleRetry(ActionEvent event) {
        actiontarget.setText("Exit button pressed");
        actiontargetBox.setVisible(true);
        application.reallyStart();;
    }
}
