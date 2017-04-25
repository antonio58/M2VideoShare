package Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Utilizador on 19/04/2017.
 */
public class LoginController implements Initializable{
    @FXML private Text actiontarget;
    @FXML private HBox actiontargetBox;
    @FXML TextField userId;
    @FXML PasswordField password;
    @FXML Button signIn;

    private Main application;
    private ServerComm sc;

    public void setApp(Main application, ServerComm s){
        this.application = application;
        this.sc = s;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actiontargetBox.setVisible(false);
        userId.setPromptText("user");
        password.setPromptText("password");
        password.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                signIn();
            }
        });
    }

    void signIn(){
        try {
            if(sc.checkLogin("q", "q"/*userId.getText(), password.getText()*/)){
                System.out.println("User check!!!");
                String data = sc.getFeed(1, (byte)5);
                application.openMainMenu(1, data, (byte)5);
            }
            else {
                actiontarget.setText("User or password wrong. Please try again.");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSubmitSignIn(ActionEvent event) {
        actiontarget.setText("Hello "+userId.getText());
        actiontargetBox.setVisible(true);
        signIn();
        /*try {
            if(sc.checkLogin(userId.getText(), password.getText())){
                System.out.println("User check!!!");
                this.application.openMainMenu();
            }
            else {
                actiontarget.setText("User or password wrong. Please try again.");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        //userId.getText(); password.getText();
    }

    @FXML
    protected void handleSubmitBack(ActionEvent event){
        this.application.openWelcome();
    }



}

