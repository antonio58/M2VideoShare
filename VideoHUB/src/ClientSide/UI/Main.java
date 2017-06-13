package ClientSide.UI;

import ClientSide.ServerComm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private Group root = new Group();
    ServerComm sc;
    private Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) /*throws Exception*/{

        /*sc = new ServerComm();

        boolean b = sc.connectToServer();


        if(b) {
            openWelcome();
        }
        else {
            openErrorScreen("Can't connect to the server");
        }*/

        openErrorScreen(null);
        primaryStage.setResizable(false);
        primaryStage.setTitle("VideoHub");
        primaryStage.setScene(new Scene(root, 1000, 600));
        setPrimaryStage(primaryStage);
        primaryStage.show();
        reallyStart();
    }

    public void reallyStart(){
        sc = new ServerComm();

        boolean b = false;
        try {
            b = sc.connectToServer("2001:690:2280:827:200:ff:feaa:e"/*"::1"*/, 3333);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(b)
            openWelcome();
        else
            openErrorScreen("Can't connect to the server");
//        String data = "olhaolhaolha";
//        int p = 5;
//        byte b = 3;
//        openMenuPrincipal(p, data, b);
    }

    public void openErrorScreen(String s){
        try {
            ErrorScreenController errorScreen = (ErrorScreenController) replaceSceneContent("ErrorScreen.fxml");
            errorScreen.setApp(this, s);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openWelcome(){
        try {
            WelcomeController welcome = (WelcomeController) replaceSceneContent("Welcome.fxml");
            welcome.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openLogin(){
        try {
            LoginController login = (LoginController) replaceSceneContent("Login.fxml");
            login.setApp(this, this.sc);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openSignUp(){
        try {
            SignUpController signUp = (SignUpController) replaceSceneContent("SignUp.fxml");
            signUp.setApp(this, this.sc);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openMainMenu(int p, String data, byte b){
        try {
            MainMenuController mainMenu = (MainMenuController) replaceSceneContent("MainMenu.fxml");
            mainMenu.setApp(this, this.sc, p, data, b);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openProfile(String str){
        try {
            ProfileController profile = (ProfileController) replaceSceneContent("Profile.fxml");
            profile.setApp(this, this.sc, str);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    public Stage getStage(){
        return this.primaryStage;
    }

    public void openStream(List<String> data){
        try {
            StreamController stream = (StreamController) replaceSceneContent("Stream.fxml");
            stream.setApp(this, this.sc, data);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openPlayer(AnchorPane grid, String fileName){
        try {
            VideoPlayer video = new VideoPlayer();
            Stage stage = new Stage();
            video.start(getStage(), grid, fileName,this,sc);
//            grid.setDisable(true);
            stage.setAlwaysOnTop(true);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openPlaylist(){
        try {
            PlaylistController profile = (PlaylistController) replaceSceneContent("Playlist.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = loader.load(in);
        } finally {
            in.close();
        }
        root.getChildren().removeAll();
        root.getChildren().addAll(page);
        return (Initializable) loader.getController();
    }
}
