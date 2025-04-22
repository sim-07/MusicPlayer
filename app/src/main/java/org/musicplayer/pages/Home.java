package org.musicplayer.pages;

import javafx.scene.layout.BorderPane;

import org.musicplayer.components.*;

public class Home extends BorderPane {
    public Home() {
        MiddleSection middleSection = new MiddleSection(); // passo lo stesso oggetto middlesection ad ogni componente
        LeftBar leftBar = new LeftBar(middleSection);

        this.setLeft(leftBar);
        this.setCenter(middleSection);
    }
}
