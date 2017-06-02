package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    @FXML private GridPane grid1;
    @FXML private GridPane grid2;
    @FXML private GridPane grid3;
    @FXML private GridPane grid4;
    @FXML private GridPane grid5;
    @FXML private GridPane grid6;
    @FXML private GridPane grid7;
    @FXML private GridPane grid8;
    @FXML private GridPane grid9;
    @FXML private GridPane grid10;
    @FXML private GridPane grid11;
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private ImageView img3;
    @FXML private ImageView img4;
    @FXML private ImageView img5;
    @FXML private ImageView img6;
    @FXML private ImageView img7;
    @FXML private ImageView img8;
    @FXML private ImageView img9;
    @FXML private ImageView img10;
    @FXML private Text title1;
    @FXML private Text title2;
    @FXML private Text title3;
    @FXML private Text title4;
    @FXML private Text title5;
    @FXML private Text title6;
    @FXML private Text title7;
    @FXML private Text title8;
    @FXML private Text title9;
    @FXML private Text title10;
    @FXML private Text author1;
    @FXML private Text author2;
    @FXML private Text author3;
    @FXML private Text author4;
    @FXML private Text author5;
    @FXML private Text author6;
    @FXML private Text author7;
    @FXML private Text author8;
    @FXML private Text author9;
    @FXML private Text author10;
    @FXML private Button next;
    @FXML private TextField query;
    @FXML private Text wText;

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
        if(data.equals("No videos")||data.isEmpty()){
            grid1.setVisible(false);
            wText.setVisible(true);
            wText.setText("No videos found...");
        }
        else {
            wText.setVisible(false);
            String[] aux = data.split("!:split:!");
            System.out.println("data: " + data + "\nlength: " + aux.length);
            Videos = aux;


            if (aux.length > 0) {
                String[] aux2 = aux[0].split("</split/>");
                grid1.setVisible(true);
                //img1.setImage(new Image(aux2[0]));
                title1.setText(aux2[0]);
                author1.setText(aux2[1]);
                if (aux.length > 1) {
                    aux2 = aux[1].split("</split/>");
                    grid2.setVisible(true);
                    //img2.setImage(new Image(aux2[0]));
                    title2.setText(aux2[0]);
                    author2.setText(aux2[1]);
                    if (aux.length > 2) {
                        aux2 = aux[2].split("</split/>");
                        grid3.setVisible(true);
                        //img3.setImage(new Image(aux2[0]));
                        title3.setText(aux2[0]);
                        author3.setText(aux2[1]);
                        if (aux.length > 3) {
                            aux2 = aux[3].split("</split/>");
                            grid4.setVisible(true);
                            //img4.setImage(new Image(aux2[0]));
                            title4.setText(aux2[0]);
                            author4.setText(aux2[1]);
                            if (aux.length > 4) {
                                aux2 = aux[4].split("</split/>");
                                grid5.setVisible(true);
                                //img5.setImage(new Image(aux2[0]));
                                title5.setText(aux2[0]);
                                author5.setText(aux2[1]);
                                if (aux.length > 5) {
                                    aux2 = aux[5].split("</split/>");
                                    grid6.setVisible(true);
                                    //img6.setImage(new Image(aux2[0]));
                                    title6.setText(aux2[0]);
                                    author6.setText(aux2[1]);
                                    if (aux.length > 6) {
                                        aux2 = aux[6].split("</split/>");
                                        grid7.setVisible(true);
                                        //img7.setImage(new Image(aux2[0]));
                                        title7.setText(aux2[0]);
                                        author7.setText(aux2[1]);
                                        if (aux.length > 7) {
                                            aux2 = aux[7].split("</split/>");
                                            grid8.setVisible(true);
                                            //img8.setImage(new Image(aux2[0]));
                                            title8.setText(aux2[0]);
                                            author8.setText(aux2[1]);
                                            if (aux.length > 8) {
                                                aux2 = aux[8].split("</split/>");
                                                grid9.setVisible(true);
                                                //img9.setImage(new Image(aux2[0]));
                                                title9.setText(aux2[0]);
                                                author9.setText(aux2[1]);
                                                if (aux.length > 9) {
                                                    aux2 = aux[9].split("</split/>");
                                                    grid10.setVisible(true);
                                                    //img10.setImage(new Image(aux2[0]));
                                                    title10.setText(aux2[0]);
                                                    author10.setText(aux2[1]);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (aux.length < 10)
                next.setVisible(false);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actiontargetBox.setVisible(false);
        grid1.setVisible(false);
        grid2.setVisible(false);
        grid3.setVisible(false);
        grid4.setVisible(false);
        grid5.setVisible(false);
        grid6.setVisible(false);
        grid7.setVisible(false);
        grid8.setVisible(false);
        grid9.setVisible(false);
        grid10.setVisible(false);
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
    protected void handleFeed(ActionEvent event) {
        actiontarget.setText("Feed button pressed");
        actiontargetBox.setVisible(true);
        String data = sc.getFeed(1, (byte)5);
        application.openMainMenu(1, data, (byte)5);
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
        actiontarget.setText("Stream button pressed");
        actiontargetBox.setVisible(true);
        String id = ((GridPane)event.getSource()).getId();

        String n = ""+id.charAt(4);
        if(id.length()>5)
            n = n.concat(""+id.charAt(5));
        System.out.println("handlestream: "+n);

        int i = Integer.parseInt(n);

        //String video = sc.getVideoInfo(i);
        application.openStream(Videos[i-1]);

        /*String data = sc.getVideoInfo();*/
    }
}