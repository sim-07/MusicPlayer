package org.musicplayer.components;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PlayerControls extends HBox {

    private Button addToPlaylist;
    private Button playButton;
    private Button pauseButton;
    private Button nextButton;
    private Button previousButton;

    public PlayerControls() {
        Image imgPlay = new Image(getClass().getResource("/icons/play.png").toExternalForm());
        Image imgPause = new Image(getClass().getResource("/icons/pause.png").toExternalForm());
        Image imgNext = new Image(getClass().getResource("/icons/skip_next.png").toExternalForm());
        Image imgPrev = new Image(getClass().getResource("/icons/skip_previous.png").toExternalForm());

        playButton = createRoundButton(imgPlay);
        pauseButton = createRoundButton(imgPause);
        nextButton = createRoundButton(imgNext);
        previousButton = createRoundButton(imgPrev);

        pauseButton.setVisible(false);
        pauseButton.setManaged(false);

        playButton.setOnAction(_ -> {
            playButton.setVisible(false);
            playButton.setManaged(false);

            pauseButton.setVisible(true);
            pauseButton.setManaged(true);
        });

        pauseButton.setOnAction(_ -> {
            pauseButton.setVisible(false);
            pauseButton.setManaged(false);

            playButton.setVisible(true);
            playButton.setManaged(true);
        });

        this.setSpacing(10);
        this.setPadding(new javafx.geometry.Insets(10, 0, 10, 0));
        this.getChildren().addAll(previousButton, playButton, pauseButton, nextButton);
    }

    private Button createRoundButton(Image iconImage) {
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
