package org.musicplayer.components;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.musicplayer.scripts.ManagePlaylist;

public class LeftBar extends VBox { // vbox = lista verticale di elementi

    // final = posso poi aggiornare senza sostituire
    private final PlaylistSection plSection;

    public LeftBar(MiddleSection middleSection) {
        HBox hContainer = new HBox(10);
        HBox.setMargin(hContainer, new Insets(0, 0, 20, 0));

        Label title = new Label("Playlists");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        title.setPrefWidth(200);

        Image imgAdd = new Image(getClass().getResource("/icons/add.png").toExternalForm());
        ImageView iconAdd = new ImageView(imgAdd);
        Button createPlaylsit = new Button("", iconAdd);
        createPlaylsit.setStyle(
                "-fx-background-color: #333333;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 50;" +
                        "-fx-cursor: hand;");

        createPlaylsit.setOnAction(_ -> showPlaylistDialog());

        Image imgSettings = new Image(getClass().getResource("/icons/settings.png").toExternalForm());
        ImageView iconSettings = new ImageView(imgSettings);
        Button settings = new Button("", iconSettings);
        settings.setStyle(
                "-fx-background-color: #333333;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 50;" +
                        "-fx-cursor: hand;");

        hContainer.getChildren().addAll(title, createPlaylsit, settings);

        this.plSection = new PlaylistSection(ManagePlaylist.fetchAllPlaylist(), middleSection);
        VBox.setMargin(plSection, new Insets(40, 0, 0, 0));

        this.setPrefWidth(300);
        this.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-padding: 40px;");
        this.getChildren().addAll(hContainer, plSection);
    }

    private void showPlaylistDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Crea Playlist");
        dialog.setHeaderText("Inserisci il nome della playlist");
        dialog.setContentText("Nome:");

        dialog.setGraphic(new ImageView()); // altrimenti javafx mostra un icona di default

        Optional<String> result = dialog.showAndWait();

        // TODO controllare che il nome sia univoco
        result.ifPresent(playlistName -> {
            if (playlistName.trim().isEmpty()) {
                showErrorDialog("Aggiungi un nome alla playlist");
            } else {
                ManagePlaylist.createPlaylist(playlistName);
                plSection.updatePl(ManagePlaylist.fetchAllPlaylist());
            }

        });
    }

    private void showErrorDialog(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(error);
        alert.setContentText("");
        alert.showAndWait();
    }

}
