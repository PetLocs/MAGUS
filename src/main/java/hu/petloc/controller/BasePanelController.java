package hu.petloc.controller;

import hu.petloc.model.GameCharacter;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * Alapvető panel vezérlő absztrakt osztály.
 * Minden panel vezérlő ennek az osztálynak a leszármazottja.
 */
public abstract class BasePanelController {

    // Egységes méret konstansok
    public static final int PANEL_WIDTH = 300;
    public static final int PANEL_HEIGHT = 345;

    // Egységes padding konstans
    public static final int PANEL_PADDING = 10;

    // Hézagok mérete
    public static final int SPACING = 5;

    // Panel címek egységes stílusa
    public static final String TITLE_STYLE = "-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER-LEFT;";

    // Mezők igazítására vonatkozó konstansok
    public static final String FIELD_ALIGNMENT_STYLE = "-fx-alignment: CENTER-LEFT;";

    // Egységes címke szélesség
    public static final int LABEL_WIDTH = 80;

    // A kezelt karakter
    protected GameCharacter character;

    /**
     * Karakter beállítása a panelhez.
     *
     * @param character A beállítandó karakter
     */
    public void setCharacter(GameCharacter character) {
        this.character = character;
        updateUI();
    }

    /**
     * A panel gyökér elemének lekérése.
     *
     * @return A gyökér elem
     */
    public abstract Parent getRoot();

    /**
     * Frissíti a felhasználói felületet a karakter adatai alapján.
     * Az alosztályoknak felül kell írniuk ezt a metódust.
     */
    protected abstract void updateUI();

    /**
     * Beállítja a standard panel méretet és stílust.
     */
    protected void setupStandardSize() {
        Parent root = getRoot();

        if (root instanceof Region) {
            Region region = (Region) root;

            // Egységes méretbeállítás
            region.setPrefSize(PANEL_WIDTH, PANEL_HEIGHT);
            region.setMinSize(PANEL_WIDTH, PANEL_HEIGHT);
            region.setMaxSize(PANEL_WIDTH, PANEL_HEIGHT);

            // Beállítjuk a padding-et a panel-re
            if (region instanceof Pane) {
                ((Pane) region).setPadding(new Insets(PANEL_PADDING));
            }

            // Egységes szegély
            String currentStyle = region.getStyle();
            if (currentStyle == null || currentStyle.isEmpty()) {
                region.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 3px;");
            } else if (!currentStyle.contains("-fx-border-color")) {
                region.setStyle(currentStyle + "; -fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 3px;");
            }
        }
    }

    /**
     * Standard panel padding értékek lekérése.
     *
     * @return Insets objektum a standard padding értékekkel
     */
    protected Insets getPanelPadding() {
        return new Insets(PANEL_PADDING);
    }
}