package ClientSide.UI;

import ClientSide.ServerComm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

/**
 * Created by Utilizador on 20/04/2017.
 */
public class SignUpController implements Initializable{
    @FXML private Text actiontarget;
    @FXML TextField userId;
    @FXML TextField email;
    @FXML TextField cemail;
    @FXML PasswordField password;
    @FXML PasswordField cpassword;

    private Main application;
    private ServerComm sc;

    public void setApp(Main application, ServerComm s){
        this.application = application;
        this.sc = s;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId.setPromptText("User");
        password.setPromptText("Password");
        email.setPromptText("Email");
        cemail.setPromptText("Confirm email");
        cpassword.setPromptText("Confirm password");
    }

    @FXML
    protected void handleConfirm(ActionEvent event) throws NoSuchAlgorithmException {
        if(!email.getText().equals(cemail.getText())) {
            actiontarget.setText("The emails didn't match!");
            return;
        }

        if(!password.getText().equals(cpassword.getText())) {
            actiontarget.setText("The passwords didn't match!");
            return;
        }

        boolean foo = sc.registerUSer(userId.getText(), password.getText(), email.getText());

        if(foo){
            application.openWelcome();
            System.out.println("User registered!!!");

        }
    }


    @FXML
    protected void handleSubmitBack(ActionEvent event){
        this.application.openWelcome();
    }
}
