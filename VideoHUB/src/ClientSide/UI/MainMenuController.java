package ClientSide.UI;

import ClientSide.ServerComm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Utilizador on 20/04/2017.
 */
public class MainMenuController implements Initializable {

    @FXML private Text actiontarget;
    @FXML private HBox actiontargetBox;
    @FXML private TextField query;
    @FXML private AnchorPane anchor;

    private int page;
    private byte feedType;
    private Main application;
    private ServerComm sc;
    String[] Videos;

    public void setApp(Main application, ServerComm sc, int p, String data, byte fT){
        this.application = application;
        this.sc = sc;
        this.page = p;
        this.feedType = fT;
        String[] aux = data.split("!:split:!");
        System.out.println("data: "+data+"\nlength: "+aux.length);
        Videos = aux;
        System.out.println(data);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actiontargetBox.setVisible(false);
    }

    @FXML
    protected void handleAdicionar(ActionEvent event){
        System.out.println(anchor.getChildren().toString());
        GridPane gp = new GridPane();
        ImageView iv = new ImageView();
        Text title = new Text();
        Text author = new Text();
        Rectangle separator = new Rectangle();

        title.setText("Teste do titulo");
        author.setText("Teste do autor");


        iv.setLayoutY(95+3);
        iv.setId("img2");

        gp.addRow(1);
        gp.setMinHeight(78);
        gp.setMinWidth(641);
        gp.setLayoutX(108);
        gp.setLayoutY(106);
        gp.getColumnConstraints().add(new ColumnConstraints(630));
        gp.getRowConstraints().add(new RowConstraints(40));
        gp.getRowConstraints().add(new RowConstraints(40));
        gp.add(title,0,0);
        gp.add(author,0,1);
        gp.setId("grid2");
        separator.setArcHeight(5);
        separator.setArcWidth(5);
        separator.setStyle("-fx-fill: #dddddd");
        separator.setLayoutX(3);
        separator.setLayoutY(195);
        separator.setWidth(740);
        separator.setHeight(8);
        separator.setStroke(Color.BLACK);
        separator.setStrokeType(StrokeType.INSIDE);

        anchor.getChildren().add(gp);
        anchor.getChildren().add(iv);
        anchor.getChildren().add(separator);
    }

    @FXML
    protected void handleSearch(MouseEvent event) {
        actiontarget.setText("Search button pressed");
        actiontargetBox.setVisible(true);
        String results = sc.getSearchResults(query.getText());
        application.openMainMenu(1,results,(byte)7);
    }


    @FXML
    protected void handleSubmitFSub(ActionEvent event) {
        actiontarget.setText("Subscribed button pressed");
        actiontargetBox.setVisible(true);
        String dataSub = sc.getFeed(1, (byte)6);
        application.openMainMenu(1, dataSub, (byte)6);
    }

    @FXML
    protected void handleSubmitFAll(ActionEvent event) {
        actiontarget.setText("All button pressed");
        actiontargetBox.setVisible(true);
        String dataAll = this.sc.getFeed(1, (byte)5);
        application.openMainMenu(1, dataAll, (byte)5);
    }

    @FXML
    protected void handleSubmitNext(ActionEvent event) {
        actiontarget.setText("Next button pressed");
        actiontargetBox.setVisible(true);
        String dataNext = this.sc.getFeed(page+1, feedType);
        application.openMainMenu(1, dataNext, feedType);
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

    @FXML
    protected void handleStream(MouseEvent event) {
        /*actiontarget.setText("Stream button pressed");
        actiontargetBox.setVisible(true);
        String id = ((GridPane)event.getSource()).getId();
        int i = Integer.parseInt(id);
        application.openStream(Videos[i-1]);*/

        actiontarget.setText("Stream button pressed");
        actiontargetBox.setVisible(true);
        String id = ((GridPane)event.getSource()).getId();

        System.out.println(id);
        String n = ""+id.charAt(3);
        if(id.length()>4)
            n = n.concat(""+id.charAt(4));
        System.out.println("handlestream: "+n);

        int i = Integer.parseInt(n);
        String[] aux2 = Videos[i-1].split("</split/>");
        System.out.println(Videos[i-1]);

        //String video = sc.getVideoInfo(aux2[1]);
        application.openStream(Videos[i-1]);
    }
}