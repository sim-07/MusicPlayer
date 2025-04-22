package org.musicplayer.components;

import java.util.List;

import org.musicplayer.scripts.Playlist;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlaylistSection extends VBox {

    public PlaylistSection(List<Playlist> playlists) {
        super(10); // passo 10 a VBox per impostare lo spazio verticale
        updatePl(playlists);
    }

    public void updatePl(List<Playlist> playlists) {
        this.getChildren().clear(); // per non rimettere le pl già esistenti
        for (Playlist playlist : playlists) {

            Button button = new Button(playlist.getName());
            
            this.getChildren().add(button);

        }
    }
}
