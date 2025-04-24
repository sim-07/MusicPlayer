package org.musicplayer.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import org.musicplayer.pages.Home;
import org.musicplayer.scripts.*;

public class MiddleSection extends VBox {
    private SongsList songsList;
    private Label plNameLabel;
    private int plId;
    private Button addToPlaylist; // per renderla accessibile anche a setPlName
    private Button delete;
    private Home home;

    public MiddleSection(Home home) {
        this.home = home;
        plNameLabel = new Label();
        plNameLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18px; -fx-font-weight: bold;");
        VBox.setMargin(plNameLabel, new Insets(20, 0, 0, 10));

        HBox hboxButton = new HBox(10);

        Image imgDelete = new Image(getClass().getResource("/icons/delete.png").toExternalForm());
        ImageView iconDelete = new ImageView(imgDelete);
        delete = new Button("", iconDelete);
        delete.setVisible(false);
        delete.setStyle(
                "-fx-background-color: #333333;" +
                        "-fx-background-radius: 25;" +
                        "-fx-padding: 2;" +
                        "-fx-cursor: hand;");
        VBox.setMargin(delete, new Insets(10, 0, 0, 10));
        delete.setOnAction(_ -> {
            ManagePlaylist.deletePlaylist(plId);
            home.updatePlaylists();
        });

        Image imgAdd = new Image(getClass().getResource("/icons/add.png").toExternalForm());
        ImageView iconAdd = new ImageView(imgAdd);
        addToPlaylist = new Button("", iconAdd);
        addToPlaylist.setVisible(false);
        addToPlaylist.setStyle(
                "-fx-background-color: #333333;" +
                        "-fx-background-radius: 25;" +
                        "-fx-padding: 2;" +
                        "-fx-cursor: hand;");
        VBox.setMargin(addToPlaylist, new Insets(10, 0, 0, 10));
        addToPlaylist.setOnAction(_ -> {
            selectFile();
        });

        VBox.setMargin(hboxButton, new Insets(10, 0, 0, 10));
        hboxButton.getChildren().addAll(addToPlaylist, delete);
        

        songsList = new SongsList(home);
        VBox.setMargin(songsList, new Insets(40, 10, 0, 10));

        this.getChildren().addAll(plNameLabel, hboxButton, songsList);
    }

    public void updateSongs() {
        List<Song> songs = ManagePlaylist.fetchSongs(plNameLabel.getText());
        songsList.updateSongs(songs, home);
    }

    public void setPlName(String name) {
        plNameLabel.setText(name);
        addToPlaylist.setVisible(name != null && !name.isEmpty());
        delete.setVisible(name != null && !name.isEmpty());
    }

    public void setPlId(int id) {
        this.plId = id;
    }

    public void selectFile() {
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.flac")
        );
        
        Stage stage = (Stage) this.getScene().getWindow();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
        
        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            for (File file : selectedFiles) {
                String fileName = file.getName();
                String filePath = file.getAbsolutePath();

                System.out.println(fileName + " " +filePath);
                ManagePlaylist.addSongsToPlaylist(fileName, filePath, plNameLabel.getText());
                updateSongs();
                
            }
        } else {
            System.out.println("Nessun file selezionato.");
        }
    }
    
}
