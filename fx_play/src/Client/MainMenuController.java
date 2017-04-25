package Client;

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
public class MainMenuController implements Initializable {

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
    protected void handleSearch(ActionEvent event) {
        actiontarget.setText("Search button pressed");
        actiontargetBox.setVisible(true);
    }

    @FXML
    protected void handleFeed(ActionEvent event) {
        actiontarget.setText("Feed button pressed");
        actiontargetBox.setVisible(true);
        String data = sc.getFeed(1, (byte)5);
        application.openFeed(1, data, (byte)5);
    }

    @FXML
    protected void handleWatchList(ActionEvent event) {
        actiontarget.setText("Watchlist button pressed");
        actiontargetBox.setVisible(true);
    }

    @FXML
    protected void handlePlaylists(ActionEvent event) {
        actiontarget.setText("Playlist button pressed");
        actiontargetBox.setVisible(true);
    }

    @FXML
    protected void handleProfile(ActionEvent event) {
        actiontarget.setText("Profile button pressed");
        actiontargetBox.setVisible(true);
        String str = sc.getUserData();
        if(str.equals("error: 404"))
            actiontarget.setText("Error getting profile data.");
        else
            application.openProfile(str);
    }

    @FXML
    protected void handleLogout(ActionEvent event) {
        this.application.openWelcome();
    }
}
