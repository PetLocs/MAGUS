package hu.petloc.view;

import hu.petloc.controller.AbilitiesPanelController;
import hu.petloc.controller.BasePanelController;
import hu.petloc.ui.NumberAdjuster;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Képességek panel nézete.
 * Ez az osztály felelős a karakter képességeinek megjelenítéséért.
 */
public class AbilitiesPanelView {

    private VBox root;
    private AbilitiesPanelController controller;

    // Képesség beállítók
    private NumberAdjuster strengthAdjuster;
    private NumberAdjuster speedAdjuster;
    private NumberAdjuster dexterityAdjuster;
    private NumberAdjuster enduranceAdjuster;
    private NumberAdjuster healthAdjuster;
    private NumberAdjuster charismaAdjuster;
    private NumberAdjuster intelligenceAdjuster;
    private NumberAdjuster willpowerAdjuster;
    private NumberAdjuster astralAdjuster;
    private NumberAdjuster perceptionAdjuster;

    /**
     * Konstruktor a képességek panel nézetéhez.
     *
     * @param controller A képességek panel vezérlője
     */
    public AbilitiesPanelView(AbilitiesPanelController controller) {
        this.controller = controller;
        createUI();
    }

    /**
     * Felhasználói felület létrehozása.
     */
    private void createUI() {
        // Konténer a teljes tartalomnak
        root = new VBox(BasePanelController.SPACING);
        root.setPadding(new Insets(BasePanelController.PANEL_PADDING));

        // Beállítjuk a standard méreteket
        root.setPrefSize(BasePanelController.PANEL_WIDTH, BasePanelController.PANEL_HEIGHT);
        root.setMinSize(BasePanelController.PANEL_WIDTH, BasePanelController.PANEL_HEIGHT);
        root.setMaxSize(BasePanelController.PANEL_WIDTH, BasePanelController.PANEL_HEIGHT);

        // Egységes szegély beállítása
        root.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 3px;");

        // Főcím - balra igazítva
        Label titleLabel = new Label("Tulajdonságok");
        titleLabel.setStyle(BasePanelController.TITLE_STYLE);
        titleLabel.setAlignment(Pos.CENTER_LEFT);
        titleLabel.setMaxWidth(Double.MAX_VALUE); // Teljes szélességet kitölti

        // Tulajdonságok rács
        GridPane grid = new GridPane();
        grid.setHgap(BasePanelController.SPACING * 2);
        grid.setVgap(BasePanelController.SPACING);
        grid.setAlignment(Pos.TOP_LEFT);

        // Oszlop kényszerek beállítása - egységes címke szélesség
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(80);
        col1.setMinWidth(80);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setFillWidth(true);

        grid.getColumnConstraints().addAll(col1, col2);

        int row = 0;

        // Erő
        Label strengthLabel = new Label("Erő");
        strengthAdjuster = createAdjuster(3, 18, 10);
        strengthAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setStrength(newVal.intValue()));
        grid.add(strengthLabel, 0, row);
        grid.add(strengthAdjuster, 1, row++);

        // Gyorsaság
        Label speedLabel = new Label("Gyorsaság");
        speedAdjuster = createAdjuster(3, 18, 10);
        speedAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setSpeed(newVal.intValue()));
        grid.add(speedLabel, 0, row);
        grid.add(speedAdjuster, 1, row++);

        // Ügyesség
        Label dexterityLabel = new Label("Ügyesség");
        dexterityAdjuster = createAdjuster(3, 18, 10);
        dexterityAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setDexterity(newVal.intValue()));
        grid.add(dexterityLabel, 0, row);
        grid.add(dexterityAdjuster, 1, row++);

        // Állóképesség
        Label enduranceLabel = new Label("Állóképesség");
        enduranceAdjuster = createAdjuster(3, 18, 10);
        enduranceAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setEndurance(newVal.intValue()));
        grid.add(enduranceLabel, 0, row);
        grid.add(enduranceAdjuster, 1, row++);

        // Egészség
        Label healthLabel = new Label("Egészség");
        healthAdjuster = createAdjuster(3, 18, 10);
        healthAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setHealth(newVal.intValue()));
        grid.add(healthLabel, 0, row);
        grid.add(healthAdjuster, 1, row++);

        // Karizma
        Label charismaLabel = new Label("Karizma");
        charismaAdjuster = createAdjuster(3, 18, 10);
        charismaAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setCharisma(newVal.intValue()));
        grid.add(charismaLabel, 0, row);
        grid.add(charismaAdjuster, 1, row++);

        // Intelligencia
        Label intelligenceLabel = new Label("Intelligencia");
        intelligenceAdjuster = createAdjuster(3, 18, 10);
        intelligenceAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setIntelligence(newVal.intValue()));
        grid.add(intelligenceLabel, 0, row);
        grid.add(intelligenceAdjuster, 1, row++);

        // Akaraterő
        Label willpowerLabel = new Label("Akaraterő");
        willpowerAdjuster = createAdjuster(3, 18, 10);
        willpowerAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setWillpower(newVal.intValue()));
        grid.add(willpowerLabel, 0, row);
        grid.add(willpowerAdjuster, 1, row++);

        // Asztrál
        Label astralLabel = new Label("Asztrál");
        astralAdjuster = createAdjuster(3, 18, 10);
        astralAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setAstral(newVal.intValue()));
        grid.add(astralLabel, 0, row);
        grid.add(astralAdjuster, 1, row++);

        // Érzékelés
        Label perceptionLabel = new Label("Érzékelés");
        perceptionAdjuster = createAdjuster(3, 18, 10);
        perceptionAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setPerception(newVal.intValue()));
        grid.add(perceptionLabel, 0, row);
        grid.add(perceptionAdjuster, 1, row++);

        // Elemek hozzáadása a konténerhez
        root.getChildren().addAll(titleLabel, grid);
    }

    /**
     * Adjuster létrehozása.
     *
     * @param min Minimális érték
     * @param max Maximális érték
     * @param initial Kezdeti érték
     * @return Az új adjuster
     */
    private NumberAdjuster createAdjuster(int min, int max, int initial) {
        NumberAdjuster adjuster = new NumberAdjuster(min, max, initial);
        // Számítás a panel szélessége alapján:
        // panel szélesség - (2 * padding) - címke szélességét (110px) - hézag (2 * spacing)
        int adjusterWidth = BasePanelController.PANEL_WIDTH - (2 * BasePanelController.PANEL_PADDING) - 110 - (2 * BasePanelController.SPACING);
        adjuster.setPrefWidth(adjusterWidth);
        adjuster.setMaxWidth(Double.MAX_VALUE); // Kitölti a rendelkezésre álló helyet
        return adjuster;
    }

    /**
     * A gyökérelem lekérése.
     *
     * @return A gyökérelem
     */
    public VBox getRoot() {
        return root;
    }

    /**
     * Erő érték beállítása.
     *
     * @param value Az új érték
     */
    public void setStrength(int value) {
        strengthAdjuster.setValue(value);
    }

    /**
     * Gyorsaság érték beállítása.
     *
     * @param value Az új érték
     */
    public void setSpeed(int value) {
        speedAdjuster.setValue(value);
    }

    /**
     * Ügyesség érték beállítása.
     *
     * @param value Az új érték
     */
    public void setDexterity(int value) {
        dexterityAdjuster.setValue(value);
    }

    /**
     * Állóképesség érték beállítása.
     *
     * @param value Az új érték
     */
    public void setEndurance(int value) {
        enduranceAdjuster.setValue(value);
    }

    /**
     * Egészség érték beállítása.
     *
     * @param value Az új érték
     */
    public void setHealth(int value) {
        healthAdjuster.setValue(value);
    }

    /**
     * Karizma érték beállítása.
     *
     * @param value Az új érték
     */
    public void setCharisma(int value) {
        charismaAdjuster.setValue(value);
    }

    /**
     * Intelligencia érték beállítása.
     *
     * @param value Az új érték
     */
    public void setIntelligence(int value) {
        intelligenceAdjuster.setValue(value);
    }

    /**
     * Akaraterő érték beállítása.
     *
     * @param value Az új érték
     */
    public void setWillpower(int value) {
        willpowerAdjuster.setValue(value);
    }

    /**
     * Asztrál érték beállítása.
     *
     * @param value Az új érték
     */
    public void setAstral(int value) {
        astralAdjuster.setValue(value);
    }

    /**
     * Érzékelés érték beállítása.
     *
     * @param value Az új érték
     */
    public void setPerception(int value) {
        perceptionAdjuster.setValue(value);
    }

    /**
     * @deprecated Használd helyette a setCharisma() metódust
     * Szépség érték beállítása.
     *
     * @param value Az új érték
     */
    @Deprecated
    public void setBeauty(int value) {
        charismaAdjuster.setValue(value);
    }
}