package ClientSide.UI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML private Text actiontarget;
    @FXML private HBox actiontargetBox;

    private Main application;

    public void setApp(Main application){
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actiontargetBox.setVisible(false);

    }

    @FXML
    void handleLogIn(ActionEvent event) {
        actiontarget.setText("Log in button pressed");
        actiontargetBox.setVisible(true);
        this.application.openLogin();

    }

    @FXML protected void handleSignUp(ActionEvent event) {
        actiontarget.setText("Sign up button pressed");
        actiontargetBox.setVisible(true);
        this.application.openSignUp();
    }

    @FXML protected void handleExit(ActionEvent event) {
        actiontarget.setText("Exit button pressed");
        actiontargetBox.setVisible(true);
        Platform.exit();
    }
}
