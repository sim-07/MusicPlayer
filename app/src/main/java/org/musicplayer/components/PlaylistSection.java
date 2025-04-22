package org.musicplayer.components;

import java.util.List;

import org.musicplayer.scripts.*;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PlaylistSection extends VBox {

    private MiddleSection middleSection;

    public PlaylistSection(List<Playlist> playlists, MiddleSection middleSection) {
        super(10); // spazio verticale
        this.middleSection = middleSection;
        updatePl(playlists);
    }

    public void updatePl(List<Playlist> playlists) {
        this.getChildren().clear(); // per non rimettere le pl già esistenti
        for (Playlist playlist : playlists) {

            Button button = new Button(playlist.getName());
            button.setStyle(
                    "-fx-background-color:rgb(31, 31, 31);" +
                            "-fx-text-fill: white;" +
                            "-fx-cursor: hand;" +
                            "-fx-padding: 8px 16px;");
            button.setMaxWidth(Double.MAX_VALUE);
            VBox.setMargin(button, new Insets(5, 0, 0, 0));
            button.setOnAction(_ -> {
                List<Song> songs = ManagePlaylist.fetchSongs(playlist.getName());
                System.out.println("Playlist selezionata: " + playlist.getName());
                this.middleSection.setPlName(playlist.getName());
                this.middleSection.updateSongs(songs);
            });

            this.getChildren().add(button);

        }
    }
}
