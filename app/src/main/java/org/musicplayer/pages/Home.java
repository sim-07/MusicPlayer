package org.musicplayer.pages;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.musicplayer.components.*;
import org.musicplayer.scripts.*;

public class Home extends BorderPane {
    private Song currentSong;
    private MediaPlayer mediaPlayer;
    private PlayerControls plControls;
    private PlaylistSection playlistSection;
    private MiddleSection middleSection;
    private List<Song> queueList = new ArrayList<>();
    private Queue queueObj;

    public Home() {

        middleSection = new MiddleSection(this);
        playlistSection = new PlaylistSection(ManagePlaylist.fetchAllPlaylist(), middleSection, this);
        LeftBar leftBar = new LeftBar(middleSection, playlistSection, this);
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

    public void setQueue(Queue queue) {
        this.queueObj = queue;
        middleSection.setQueue(queue);
        plControls.setQueue(queue);
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

        plControls.setMediaPlayer(mediaPlayer);
        plControls.setPlayingBt(true);

        plControls.manageMediaPl(queueObj);
    }

    public void updatePlaylists() {
        List<Playlist> allPl = ManagePlaylist.fetchAllPlaylist();
        playlistSection.updatePl(allPl);

        middleSection.setPlName("");
        middleSection.updateSongs();
    }

    public Song getCurrentSong() {
        return currentSong;
    }

}
