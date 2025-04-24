package org.musicplayer.components;

import org.musicplayer.pages.Home;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private MediaPlayer mediapl;

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
        hboxButtons.setAlignment(Pos.CENTER);

        progressSlider = new Slider(); // slider per andare avanti/indietro nella canzone
        progressSlider.setMin(0);
        progressSlider.setMax(100);
        progressSlider.setValue(0);
        progressSlider.setPrefWidth(250);
        progressSlider.setStyle("-fx-cursor: hand;");
        

        playButton.setOnAction(_ -> {
            playSong();

            setPlayingBt(true);
        });

        pauseButton.setOnAction(_ -> {
            pauseSong();

            setPlayingBt(false);
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(progressSlider, hboxButtons);

        this.setSpacing(10);
        this.setPadding(new javafx.geometry.Insets(10, 0, 10, 0));
        this.getChildren().addAll(vbox);
    }

    public void manageMediaPl() {
        mediapl.setOnReady(() -> {
            Duration total = mediapl.getMedia().getDuration();
            progressSlider.setMax(total.toSeconds()); // metto la durata in secondi del brano come massimo per lo slider
        });
    
        mediapl.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            progressSlider.setValue(newTime.toSeconds());
        });

        // per andare avanti o indietro cliccando sullo slider
        progressSlider.valueProperty().addListener((obs, oldVal, newVal) -> { 
            if (progressSlider.isPressed()) {
                mediapl.seek(Duration.seconds(newVal.doubleValue()));
            }
        });
    
        // per andare avanti o indietro trascinando lo slider
        progressSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isChanging) {
                mediapl.seek(Duration.seconds(progressSlider.getValue()));
                setPlayingBt(true);
                mediapl.play();
            } else {
                System.out.println("Pausasl");
                setPlayingBt(false);
                mediapl.pause();
            }
        });

        mediapl.setOnEndOfMedia(() -> {
            // prossima canzone
        });
    }

    public void setPlayingBt(boolean play) { // true = c'è una canzone in riproduzione
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

    public void pauseSong() {
        if (mediapl != null) mediapl.pause();
        System.out.println("pause");
    }
    
    public void playSong() {
        if (mediapl != null) mediapl.play();
        System.out.println("play");
    }

    public void setMediaPlayer(MediaPlayer mediapl) {
        this.mediapl = mediapl;
    }
}
