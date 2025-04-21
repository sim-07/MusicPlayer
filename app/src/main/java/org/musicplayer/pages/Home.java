package org.musicplayer.pages;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

import org.musicplayer.components.LeftBar;

public class Home extends BorderPane { // Per gestire vari elementi 
    public Home() {
        LeftBar leftBar = new LeftBar();
        this.setLeft(leftBar);
    }
}