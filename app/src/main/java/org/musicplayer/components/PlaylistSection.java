package org.musicplayer.components;

import java.util.List;

import org.musicplayer.pages.Home;
import org.musicplayer.scripts.*;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PlaylistSection extends VBox {

    private MiddleSection middleSection;
    private Home home;

    public PlaylistSection(List<Playlist> playlists, MiddleSection middleSection, Home home) {
        super(10); // spazio verticale
        this.middleSection = middleSection;
        this.home = home;
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
                middleSection.setPlName(playlist.getName());
                middleSection.setPlId(playlist.getId());
                middleSection.updateSongs();

                home.setQueue(new Queue(playlist.getName(), false));
            });

            this.getChildren().add(button);

        }
    }
}
