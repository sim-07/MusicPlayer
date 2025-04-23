package org.musicplayer.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.util.List;
import org.musicplayer.scripts.Song;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import org.musicplayer.pages.Home;
import org.musicplayer.scripts.ManagePlaylist;

public class MiddleSection extends VBox {
    private SongsList songsList;
    private Label plNameLabel;
    private Button addToPlaylist; // per renderla accessibile anche a setPlName
    private Home home;

    public MiddleSection(Home home) {
        this.home = home;
        plNameLabel = new Label();
        plNameLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18px; -fx-font-weight: bold;");
        VBox.setMargin(plNameLabel, new Insets(20, 0, 0, 10));

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

        songsList = new SongsList(home);
        VBox.setMargin(songsList, new Insets(40, 10, 0, 10));

        this.getChildren().addAll(plNameLabel, addToPlaylist, songsList);
    }

    public void updateSongs() {
        List<Song> songs = ManagePlaylist.fetchSongs(plNameLabel.getText());
        songsList.updateSongs(songs, home);
    }

    public void setPlName(String name) {
        plNameLabel.setText(name);
        addToPlaylist.setVisible(name != null && !name.isEmpty());
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
