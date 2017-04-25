package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Project fx_play was created by
 * António Lourenço on 21, April of 2017.
 */
public class ProfileController implements Initializable {
    @FXML private Text actiontarget;
    @FXML private HBox actiontargetBox;
    @FXML Text user;
    @FXML Text email;
    @FXML TextField nuser;
    @FXML TextField nemail;
    @FXML PasswordField npass;
    @FXML PasswordField cnpass;

    private Main application;
    private ServerComm sc;

    public void setApp(Main application, ServerComm s, String str){
        this.application = application;
        this.sc = s;
        String[] aux = str.split("--");
        user.setText(aux[0]);
        email.setText(aux[1]);
        nuser.setPromptText("New user name");
        nemail.setPromptText("New email");
        npass.setPromptText("New password");
        cnpass.setPromptText("Confirm password");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actiontargetBox.setVisible(false);
    }

    @FXML
    protected void handleConfirm(ActionEvent event) {
        actiontarget.setText("Confirm button pressed");
        actiontargetBox.setVisible(true);
        System.out.println("user: "+nuser.getText()+"\nemail: "+nemail.getText()+"\npass: "+npass.getText());
        if(npass.getText().equals(cnpass.getText())) {
            boolean b = sc.updateProfile(nuser.getText(), nemail.getText(), npass.getText());
            if(b) {
                String str = sc.getUserData();
                if (str.equals("error: 404"))
                    actiontarget.setText("Error getting profile data.");
                else
                    application.openProfile(str);
            } else
                actiontarget.setText("Error updating profile data");

        } else
            actiontarget.setText("Passwords don't match");

    }

    @FXML
    protected void handleSubmitBack(ActionEvent event){
        String data = sc.getFeed(1,(byte)5);
        this.application.openMainMenu(1,data,(byte)5);
    }
}
