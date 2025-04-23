package org.musicplayer.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.List;
import org.musicplayer.scripts.Song;

public class SongsList extends VBox {

    public SongsList() {
        super(5);
        Label emptyLabel = new Label("Nessuna playlist selezionata.");
        emptyLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
        this.getChildren().add(emptyLabel);
    }

    public SongsList(List<Song> songs) {
        super(5);
        updateSongs(songs);
    }

    public void updateSongs(List<Song> songs) {
        this.getChildren().clear();
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

            this.getChildren().add(button);
        }
    }
}
