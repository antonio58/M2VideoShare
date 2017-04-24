package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private Group root = new Group();
    ServerComm sc;

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
        primaryStage.setScene(new Scene(root, 750, 500));
        primaryStage.show();
        reallyStart();
    }

    public void reallyStart(){
        sc = new ServerComm();

        boolean b = false;
        try {
            b = sc.connectToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(b)
            openWelcome();
        else
            openErrorScreen("Can't connect to the server");
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

    public void openMainMenu(){
        try {
            MainMenuController mainMenu = (MainMenuController) replaceSceneContent("MainMenu.fxml");
            mainMenu.setApp(this, this.sc);
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

    public void openFeed(){
        try {
            FeedController feed = (FeedController) replaceSceneContent("Feed.fxml");
            feed.setApp(this, this.sc);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        GridPane page;
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
