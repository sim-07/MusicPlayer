package org.musicplayer.pages;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import org.musicplayer.components.*;
import org.musicplayer.scripts.*;

public class Home extends BorderPane {
    private Song currentSong;
    private MediaPlayer mediaPlayer;
    private PlayerControls plControls;

    public Home() {
        MiddleSection middleSection = new MiddleSection(this); // passo lo stesso oggetto middlesection ad ogni componente
        LeftBar leftBar = new LeftBar(middleSection, this);
        plControls = new PlayerControls(this);

        HBox plcontrols = new HBox(plControls); // metto plcontrols in un hbox per centrarlo orizzontalmente
        plcontrols.setAlignment(Pos.CENTER);

        this.setLeft(leftBar);
        this.setCenter(middleSection);
        this.setBottom(plcontrols);
    }

    public void showPage(Node page) {
        this.setCenter(page);
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
        System.out.println("currentsong: " + currentSong.getName());

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Media media = new Media(new File(currentSong.path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        plControls.setPlayBt(true);
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void pauseSong() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }
    
    public void playSong() {
        if (mediaPlayer != null) mediaPlayer.play();
    }
    
    public void stopSong() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }
}
