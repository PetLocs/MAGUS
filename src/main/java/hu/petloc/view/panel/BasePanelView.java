package hu.petloc.view.panel;

import hu.petloc.controller.panel.BasePanelController;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * Alap osztály a panel nézetek számára.
 * Minden panel nézet ehhez az osztályhoz kapcsolódik.
 */
public abstract class BasePanelView {

    protected final BasePanelController controller;

    /**
     * Konstruktor a nézet létrehozásához a kontroller kapcsolattal.
     *
     * @param controller A nézethez tartozó kontroller
     */
    public BasePanelView(BasePanelController controller) {
        this.controller = controller;
        createUI();
        setupStandardSize();
    }

    /**
     * Felhasználói felület létrehozása.
     * Az alosztályoknak felül kell írniuk ezt a metódust.
     */
    protected abstract void createUI();

    /**
     * Visszaadja a panel root elemét.
     *
     * @return A panel root eleme
     */
    public abstract Region getRoot();

    /**
     * Beállítja a standard panel méretet és stílust.
     */
    protected void setupStandardSize() {
        Region root = getRoot();

        // Egységes méretbeállítás
        root.setPrefSize(BasePanelController.PANEL_WIDTH, BasePanelController.PANEL_HEIGHT);
        root.setMinSize(BasePanelController.PANEL_WIDTH, BasePanelController.PANEL_HEIGHT);
        root.setMaxSize(BasePanelController.PANEL_WIDTH, BasePanelController.PANEL_HEIGHT);

        // Beállítjuk a padding-et a panel-re
        if (root instanceof Pane) {
            ((Pane) root).setPadding(new Insets(BasePanelController.PANEL_PADDING));
        }

        // Egységes szegély
        String currentStyle = root.getStyle();
        if (currentStyle == null || currentStyle.isEmpty()) {
            root.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 3px;");
        } else if (!currentStyle.contains("-fx-border-color")) {
            root.setStyle(currentStyle + "; -fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 3px;");
        }
    }

    /**
     * Standard panel padding értékek lekérése Insets objektumként.
     *
     * @return Insets objektum a standard padding értékekkel
     */
    protected Insets getPanelPaddingInsets() {
        return new Insets(BasePanelController.PANEL_PADDING);
    }
}