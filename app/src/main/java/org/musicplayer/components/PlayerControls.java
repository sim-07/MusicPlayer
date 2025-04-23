package org.musicplayer.components;

import org.musicplayer.pages.Home;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Slider;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class PlayerControls extends HBox {

    private Button playButton;
    private Button pauseButton;
    private Button nextButton;
    private Button previousButton;
    private Slider progressSlider;

    public PlayerControls(Home home) {
        Image imgPlay = new Image(getClass().getResource("/icons/play.png").toExternalForm());
        Image imgPause = new Image(getClass().getResource("/icons/pause.png").toExternalForm());
        Image imgNext = new Image(getClass().getResource("/icons/skip_next.png").toExternalForm());
        Image imgPrev = new Image(getClass().getResource("/icons/skip_previous.png").toExternalForm());

        HBox hboxButtons = new HBox(10);

        playButton = createButton(imgPlay);
        pauseButton = createButton(imgPause);
        nextButton = createButton(imgNext);
        previousButton = createButton(imgPrev);

        pauseButton.setVisible(false);
        pauseButton.setManaged(false);

        hboxButtons.getChildren().addAll(previousButton, playButton, pauseButton, nextButton);

        progressSlider = new Slider(); // slider per andare avanti/indietro nella canzone
        progressSlider.setMin(0);
        progressSlider.setMax(100);
        progressSlider.setValue(0);
        progressSlider.setPrefWidth(200);
        progressSlider.setStyle("-fx-cursor: hand;");

        playButton.setOnAction(_ -> {
            home.playSong();

            setPlayBt(true);
        });

        pauseButton.setOnAction(_ -> {
            home.pauseSong();

            setPlayBt(false);
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(progressSlider, hboxButtons);

        this.setSpacing(10);
        this.setPadding(new javafx.geometry.Insets(10, 0, 10, 0));
        this.getChildren().addAll(vbox);
    }

    public void setPlayBt(boolean play) {
        if (play) {
            playButton.setVisible(false);
            playButton.setManaged(false); // con managed a false la pagina si comporta come se non ci fosse il bottone

            pauseButton.setVisible(true);
            pauseButton.setManaged(true);
        } else {
            pauseButton.setVisible(false);
            pauseButton.setManaged(false);

            playButton.setVisible(true);
            playButton.setManaged(true);
        }
    }

    private Button createButton(Image iconImage) {
        ImageView icon = new ImageView(iconImage);
        icon.setFitWidth(20);
        icon.setFitHeight(20);

        Button button = new Button("", icon);
        button.setStyle(
                "-fx-background-color: #222;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-padding: 10px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 4, 0, 0, 1);");
        return button;
    }
}
