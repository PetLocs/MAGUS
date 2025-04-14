package hu.petloc.controller.panel;

import hu.petloc.model.character.Character;
import hu.petloc.view.panel.BasePanelView;
import javafx.scene.layout.Region;

/**
 * Alap osztály a panel kontrollerek számára.
 * Minden panel kontroller ebből az osztályból származik.
 */
public abstract class BasePanelController {

    // Egységes méret konstansok
    public static final int PANEL_WIDTH = 300;
    public static final int PANEL_HEIGHT = 345;

    // Egységes padding konstans
    public static final int PANEL_PADDING = 10;

    // Hézagok mérete
    public static final int SPACING = 5;

    protected Character character;

    /**
     * Beállítja a karaktert, akinek az adatait a panel megjeleníti.
     *
     * @param character A megjelenítendő karakter
     */
    public void setCharacter(Character character) {
        this.character = character;
        updateUI();
    }

    /**
     * Visszaadja a kezelt karaktert.
     *
     * @return A kontroller által kezelt karakter
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Frissíti a felhasználói felületet a karakter adatai alapján.
     * Az alosztályoknak felül kell írniuk ezt a metódust.
     */
    protected abstract void updateUI();

    /**
     * Standard hézag méretének lekérése.
     *
     * @return A standard hézag mérete
     */
    public static int getSpacing() {
        return SPACING;
    }

    /**
     * Standard panel padding lekérése.
     *
     * @return A standard panel padding
     */
    public static int getPanelPadding() {
        return PANEL_PADDING;
    }

    /**
     * Visszaadja a kontroller nézetét.
     * Az alosztályoknak felül kell írniuk ezt a metódust.
     *
     * @return A kontroller által kezelt nézet
     */
    public abstract BasePanelView getView();

    /**
     * Visszaadja a panel fő elemét.
     *
     * @return A panel root Region
     */
    public Region getRoot() {
        return getView().getRoot();
    }
}