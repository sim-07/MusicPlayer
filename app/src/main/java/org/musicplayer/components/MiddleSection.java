package org.musicplayer.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.List;
import org.musicplayer.scripts.Song;

public class MiddleSection extends VBox {
    private SongsList songsList;
    private Label plNameLabel;

    public MiddleSection() {
        plNameLabel = new Label();
        plNameLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18px; -fx-font-weight: bold;");
        VBox.setMargin(plNameLabel, new Insets(20, 0, 0, 10));

        songsList = new SongsList();
        VBox.setMargin(songsList, new Insets(40, 0, 0, 10));

        this.getChildren().addAll(plNameLabel, songsList);
    }

    public void updateSongs(List<Song> songs) {
        songsList.updateSongs(songs);
    }

    public void setPlName(String name) {
        plNameLabel.setText(name);
    }
}
