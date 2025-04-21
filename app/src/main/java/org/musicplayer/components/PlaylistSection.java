package org.musicplayer.components;

import java.util.List;

import org.musicplayer.scripts.Playlist;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlaylistSection extends VBox {

    public PlaylistSection(List<Playlist> playlists) {
        super(10); // passo 10 a VBox per impostare lo spazio verticale

        for (Playlist playlist : playlists) {
            Label label = new Label(playlist.getName());
            this.getChildren().add(label);
        }
    }
}
