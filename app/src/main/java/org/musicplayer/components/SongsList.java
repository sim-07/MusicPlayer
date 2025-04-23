package org.musicplayer.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import java.util.List;

import org.musicplayer.scripts.Song;
import org.musicplayer.pages.Home;

public class SongsList extends VBox {

    public SongsList(Home home) {
        super(5);
        Label emptyLabel = new Label("Nessuna playlist selezionata.");
        emptyLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
        this.getChildren().add(emptyLabel);
    }

    public SongsList(List<Song> songs, Home home) {
        super(5);
        updateSongs(songs, home);
    }

    public void updateSongs(List<Song> songs, Home home) {
        VBox songList = new VBox();

        for (Song song : songs) {
            Button button = new Button(song.getName());
            button.setStyle(
                    "-fx-background-color: rgb(247, 247, 247);" +
                            "-fx-text-fill: black;" +
                            "-fx-cursor: hand;" +
                            "-fx-padding: 8px 16px;" +
                            "-fx-border-color: #cccccc;" +
                            "-fx-border-width: 1px;");
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction(_ -> {
                home.setCurrentSong(song);
            });

            songList.getChildren().add(button);
        }

        ScrollPane scrollPane = new ScrollPane(songList);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.getChildren().clear();
        this.getChildren().add(scrollPane);
    }
}
