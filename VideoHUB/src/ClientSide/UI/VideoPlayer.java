package ClientSide.UI;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class VideoPlayer extends Application {

    public static void main(String args[]) {
        launch(args);
    }


    public void start(final Stage stage, AnchorPane grid, String id) throws Exception {
        stage.setTitle("VideoHub Streamer");
        Group root = new Group();

        System.out.println("Playing video: "+id);

        Media media = new Media("file:///home/rafael/Documentos/VideoHubVideo/Temp/"+id);
//        Media media = new Media("file:///home/luisf99/Documentos/UniversidadeMinho/ProjetodeTelecomunicacoesInformatica2/Videos/VideoDemo.mp4");
        final MediaPlayer player = new MediaPlayer(media);
        MediaView view = new MediaView(player);
        DoubleProperty mvw = view.fitWidthProperty();
        DoubleProperty mvh = view.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
        view.setPreserveRatio(true);

        final Timeline slideIn = new Timeline();
        final Timeline slideOut = new Timeline();
        root.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                slideIn.play();
            }
        });
        root.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                slideOut.play();
            }
        });

        final VBox vBox = new VBox();
        vBox.prefWidthProperty().bind(view.fitWidthProperty());

        final Slider slider = new Slider();
        final HBox hBox = new HBox();
        vBox.getChildren().add(slider);
        vBox.getChildren().add(hBox);

        ToggleButton resume = new ToggleButton();
        ToggleButton sound = new ToggleButton();
        Button screen = new Button();
        Button reload = new Button();
        Button fast = new Button();
        Button slow = new Button();
        Button normal = new Button();
        Slider volume = new Slider();
        hBox.getChildren().add(resume);
        hBox.getChildren().add(reload);
        hBox.getChildren().add(slow);
        hBox.getChildren().add(normal);
        hBox.getChildren().add(fast);
        hBox.getChildren().add(screen);
        hBox.getChildren().add(sound);
        hBox.getChildren().add(volume);
        hBox.setSpacing(3);
        resume.setText("⏯");
        reload.setText("\u23F9");
        sound.setText("\uD83D\uDD0A");
        fast.setText("⏩");
        normal.setText("\u23FA");
        slow.setText("⏪");
        screen.setText("🖵");
        resume.setSelected(false);
        sound.setSelected(false);

        volume.setMin(0.0);
        volume.setValue(player.getVolume());
        volume.setMax(player.getVolume());

        root.getChildren().add(view);
        root.getChildren().add(vBox);

        player.play();
        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                int h = player.getMedia().getHeight();
                int w = player.getMedia().getWidth();
                Scene scene = new Scene(root, w, h, Color.BLACK);
                stage.setMinHeight(h);
                stage.setMinWidth(w);
                //stage.setMaxHeight(h + 50);
                //stage.setMaxWidth(w + 40);

                stage.setScene(scene);
                stage.show();

                vBox.setMinSize(w, 200);

                slider.setMin(0.0);
                slider.setValue(0.0);
                slider.setMax(player.getTotalDuration().toSeconds());

                slideIn.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0),
                                new KeyValue(vBox.opacityProperty(), 0.0)
                        ),
                        new KeyFrame(new Duration(300),
                                new KeyValue(vBox.opacityProperty(), 0.9)
                        )
                );
                slideOut.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0),
                                new KeyValue(vBox.opacityProperty(), 0.9)
                        ),
                        new KeyFrame(new Duration(500),
                                new KeyValue(vBox.opacityProperty(), 0.0)
                        )
                );
            }
        });

        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {
                slider.setValue(current.toSeconds());
            }
        });

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                player.stop();
//                grid.setDisable(false);
            }
        });

        resume.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!resume.selectedProperty().getValue()) {
                    resume.setText("\u23F5");
                    player.pause();
                } else {
                    resume.setText("\u23F8");
                    player.play();
                }
            }
        });

        sound.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!sound.selectedProperty().getValue()) {
                    sound.setText("\uD83D\uDD07");
                    player.setMute(true);
                    volume.setValue(0.0);
                } else {
                    sound.setText("\uD83D\uDD0A");
                    player.setMute(false);
                    volume.setValue(player.getVolume());
                }
            }
        });

        screen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (stage.isFullScreen()) {
                    stage.setFullScreen(false);
                } else {
                    stage.setFullScreen(true);
                }
            }
        });

        reload.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.seek(player.getStartTime());
                player.play();
            }
        });

        fast.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(player.getRate() == 2){
                    player.setRate(3);
                }else {
                    player.setRate(2);
                }
            }
        });

        normal.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.setRate(1);
            }
        });

        slow.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(player.getRate() == 0.5){
                    player.setRate(0.3);
                }else {
                    player.setRate(0.5);
                }
            }
        });

        slider.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.seek(Duration.seconds(slider.getValue()));
            }
        });

        slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.seek(Duration.seconds(slider.getValue()));
            }
        });

        player.volumeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(volume.getValue());
            }
        });


        volume.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.setVolume(volume.getValue());
                if (volume.getValue() == 0.0) {
                    sound.setText("\uD83D\uDD07");
                } else {
                    sound.setText("\uD83D\uDD0A");
                }
            }
        });

        volume.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.setVolume(volume.getValue());
                if (volume.getValue() == 0.0) {
                    sound.setText("\uD83D\uDD07");
                } else {
                    sound.setText("\uD83D\uDD0A");
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}