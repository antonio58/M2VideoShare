package TESTS;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;



import ServerSide.ChunkTasks;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mp4parser.IsoFile;

public final class TEST_FOLDER extends Application {

    private ChunkTasks chunkTasks = new ChunkTasks();

    @Override
    public void start(final Stage stage) {
        stage.setTitle("File Chooser Sample");

        final FileChooser fileChooser = new FileChooser();
        final Button openButton = new Button("Open a Picture...");

        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        configureFileChooser(fileChooser);
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
                                String filename = file.getAbsolutePath();
                                IsoFile isoFile = new IsoFile(filename);
                                double lengthInSeconds = (double)
                                        isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                                        isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
                                //TODO: está a imprimir aqui o tempo em segundos
                                System.out.println(lengthInSeconds);

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });



        final GridPane inputGridPane = new GridPane();


        inputGridPane.add(openButton,0,0);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));

        stage.setScene(new Scene(rootGroup,100,100));
        stage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All videos", "*.*"),
                new FileChooser.ExtensionFilter("mp4", "*.mp4")
        );
    }
}