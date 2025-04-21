package org.musicplayer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import org.musicplayer.pages.Home;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Home homePage = new Home();

        Scene scene = new Scene(homePage, 1024, 768);

        stage.setTitle("MusicPlayer");
        stage.setScene(scene);
        stage.show();
    }

}