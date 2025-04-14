package hu.petloc.view;

import hu.petloc.controller.BasePanelController;
import hu.petloc.controller.LoggerPanelController;
import hu.petloc.model.GameCharacter;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Naplózó panel nézete.
 * Ez az osztály felelős a karakter eseményeinek naplózásáért és megjelenítéséért.
 */
public class LoggerPanelView {

    private BorderPane root;
    private LoggerPanelController controller;
    private ListView<String> logListView;

    /**
     * Konstruktor a naplózó panel nézetéhez.
     *
     * @param controller A naplózó panel vezérlője
     */
    public LoggerPanelView(LoggerPanelController controller) {
        this.controller = controller;
        createUI();
    }

    /**
     * Felhasználói felület létrehozása.
     */
    private void createUI() {
        root = new BorderPane();
        root.setPadding(new Insets(BasePanelController.PANEL_PADDING));

        // Egyedi méretezés a LoggerPanel-nek
        // Ez a panel ugyanolyan széles, de magasabb, mint a standard BasePanelController méretek
        root.setPrefSize(BasePanelController.PANEL_WIDTH, 500);
        root.setMinSize(BasePanelController.PANEL_WIDTH, 500);
        root.setMaxSize(BasePanelController.PANEL_WIDTH, 500);

        // Szegély hozzáadása
        root.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 3px;");

        // Főcím
        Label titleLabel = new Label("Eseménynapló");
        titleLabel.setStyle(BasePanelController.TITLE_STYLE);

        // Napló lista létrehozása
        logListView = new ListView<>();
        logListView.setPrefHeight(400);

        // Lista elhelyezése TitledPane-be
        TitledPane logPane = new TitledPane("Eseménynapló", logListView);
        logPane.setCollapsible(false);
        VBox.setVgrow(logPane, Priority.ALWAYS);

        // Vezérlőgombok létrehozása
        Button clearButton = new Button("Törlés");
        clearButton.setOnAction(e -> controller.clearLogs());

        Button addEntryButton = new Button("Új bejegyzés hozzáadása");
        addEntryButton.setOnAction(e -> {
            // Egyszerű demonstráció, egy valós alkalmazásban egy dialógusablak lenne
            controller.logEvent("Manuális bejegyzés " + System.currentTimeMillis());
        });

        // Gombok elhelyezése
        HBox buttonBox = new HBox(BasePanelController.SPACING);
        buttonBox.setPadding(new Insets(BasePanelController.SPACING, 0, 0, 0));
        buttonBox.getChildren().addAll(addEntryButton, clearButton);

        // Nézet összeállítása
        VBox contentBox = new VBox(BasePanelController.SPACING);
        contentBox.getChildren().addAll(titleLabel, logPane, buttonBox);

        root.setCenter(contentBox);
    }

    /**
     * Naplóbejegyzések frissítése a karakter alapján.
     *
     * @param character A karakter, amelynek a naplóbejegyzéseit megjelenítjük
     */
    public void updateLogs(GameCharacter character) {
        if (character == null) {
            clearLogs();
            return;
        }

        logListView.getItems().clear();
        logListView.getItems().addAll(character.getLogEntries());
    }

    /**
     * Naplóbejegyzések törlése.
     */
    public void clearLogs() {
        logListView.getItems().clear();
    }

    /**
     * A gyökérelem lekérése.
     *
     * @return A gyökérelem
     */
    public BorderPane getRoot() {
        return root;
    }
}