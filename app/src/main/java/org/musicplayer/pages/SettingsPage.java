package org.musicplayer.pages;

import org.musicplayer.components.MiddleSection;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;

public class SettingsPage extends VBox {

    public SettingsPage(Home home, MiddleSection middleSection) {
        this.setSpacing(15);
        this.setPadding(new Insets(20));

        Text titolo = new Text("Impostazioni");
        titolo.setStyle("-fx-fill: black; -fx-font-size: 24px;");

        VBox sezioneNote = new VBox(5);
        Label etichettaNote = new Label("Note:");
        TextArea areaNote = new TextArea("Note...");
        areaNote.setPrefRowCount(3);
        sezioneNote.getChildren().addAll(etichettaNote, areaNote);

        VBox sezioneTema = new VBox(5);
        Label etichettaTema = new Label("Tema dell'app:");
        ComboBox<String> selectTema = new ComboBox<>();
        selectTema.getItems().addAll("Chiaro", "Scuro");
        selectTema.setValue("Scuro");
        sezioneTema.getChildren().addAll(etichettaTema, selectTema);

        VBox sezioneNotifiche = new VBox(5);
        Label etichettaNot = new Label("Notifiche:");
        CheckBox notificaConcerti = new CheckBox("Concerti vicino a me");
        CheckBox notificaAgg = new CheckBox("Aggiornamenti dell'app");
        CheckBox notificaProm = new CheckBox("Promozioni");
        sezioneNotifiche.getChildren().addAll(
            etichettaNot, notificaConcerti, notificaAgg, notificaProm
        );

        VBox sezioneQualita = new VBox(5);
        Label etichettaQualita = new Label("Qualità audio:");
        RadioButton bassa = new RadioButton("Bassa");
        RadioButton media = new RadioButton("Media");
        RadioButton alta = new RadioButton("Alta");
        ToggleGroup qualitaContainer = new ToggleGroup();
        bassa.setToggleGroup(qualitaContainer);
        media.setToggleGroup(qualitaContainer);
        alta.setToggleGroup(qualitaContainer);
        media.setSelected(true);
        sezioneQualita.getChildren().addAll(etichettaQualita, bassa, media, alta);

        Button homeBtn = new Button("Torna alla Home");
        homeBtn.setOnAction(_ -> home.showPage(middleSection));

        this.getChildren().addAll(
            titolo,
            sezioneNote,
            sezioneTema,
            sezioneNotifiche,
            sezioneQualita,
            homeBtn
        );
    }
}
