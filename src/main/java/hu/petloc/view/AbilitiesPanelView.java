package hu.petloc.view;

import hu.petloc.controller.AbilitiesPanelController;
import hu.petloc.ui.NumberAdjuster;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private NumberAdjuster enduranceAdjuster;
    private NumberAdjuster dexterityAdjuster;
    private NumberAdjuster speedAdjuster;
    private NumberAdjuster intelligenceAdjuster;
    private NumberAdjuster willpowerAdjuster;
    private NumberAdjuster astralAdjuster;
    private NumberAdjuster beautyAdjuster;

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
        root = new VBox(10);
        root.setPadding(new Insets(10));

        // Főcím
        Label titleLabel = new Label("Képességértékek");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Fizikai képességek
        GridPane physicalGrid = new GridPane();
        physicalGrid.setHgap(10);
        physicalGrid.setVgap(10);
        physicalGrid.setAlignment(Pos.CENTER);

        int row = 0;

        // Erő
        Label strengthLabel = new Label("Erő");
        strengthAdjuster = createAdjuster(3, 18, 10);
        strengthAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setStrength(newVal.intValue()));
        physicalGrid.add(strengthLabel, 0, row);
        physicalGrid.add(strengthAdjuster, 1, row++);

        // Állóképesség
        Label enduranceLabel = new Label("Állóképesség");
        enduranceAdjuster = createAdjuster(3, 18, 10);
        enduranceAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setEndurance(newVal.intValue()));
        physicalGrid.add(enduranceLabel, 0, row);
        physicalGrid.add(enduranceAdjuster, 1, row++);

        // Ügyesség
        Label dexterityLabel = new Label("Ügyesség");
        dexterityAdjuster = createAdjuster(3, 18, 10);
        dexterityAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setDexterity(newVal.intValue()));
        physicalGrid.add(dexterityLabel, 0, row);
        physicalGrid.add(dexterityAdjuster, 1, row++);

        // Gyorsaság
        Label speedLabel = new Label("Gyorsaság");
        speedAdjuster = createAdjuster(3, 18, 10);
        speedAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setSpeed(newVal.intValue()));
        physicalGrid.add(speedLabel, 0, row);
        physicalGrid.add(speedAdjuster, 1, row++);

        // Mentális képességek
        GridPane mentalGrid = new GridPane();
        mentalGrid.setHgap(10);
        mentalGrid.setVgap(10);
        mentalGrid.setAlignment(Pos.CENTER);

        row = 0;

        // Intelligencia
        Label intelligenceLabel = new Label("Intelligencia");
        intelligenceAdjuster = createAdjuster(3, 18, 10);
        intelligenceAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setIntelligence(newVal.intValue()));
        mentalGrid.add(intelligenceLabel, 0, row);
        mentalGrid.add(intelligenceAdjuster, 1, row++);

        // Akaraterő
        Label willpowerLabel = new Label("Akaraterő");
        willpowerAdjuster = createAdjuster(3, 18, 10);
        willpowerAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setWillpower(newVal.intValue()));
        mentalGrid.add(willpowerLabel, 0, row);
        mentalGrid.add(willpowerAdjuster, 1, row++);

        // Asztrál
        Label astralLabel = new Label("Asztrál");
        astralAdjuster = createAdjuster(3, 18, 10);
        astralAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setAstral(newVal.intValue()));
        mentalGrid.add(astralLabel, 0, row);
        mentalGrid.add(astralAdjuster, 1, row++);

        // Szépség
        Label beautyLabel = new Label("Szépség");
        beautyAdjuster = createAdjuster(3, 18, 10);
        beautyAdjuster.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setBeauty(newVal.intValue()));
        mentalGrid.add(beautyLabel, 0, row);
        mentalGrid.add(beautyAdjuster, 1, row++);

        // Panelek elhelyezése
        TitledPane physicalPane = new TitledPane("Fizikai képességek", physicalGrid);
        physicalPane.setCollapsible(false);

        TitledPane mentalPane = new TitledPane("Mentális képességek", mentalGrid);
        mentalPane.setCollapsible(false);

        // Elemek hozzáadása a konténerhez
        root.getChildren().addAll(titleLabel, physicalPane, mentalPane);
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
        adjuster.setPrefWidth(150);
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
     * Állóképesség érték beállítása.
     *
     * @param value Az új érték
     */
    public void setEndurance(int value) {
        enduranceAdjuster.setValue(value);
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
     * Gyorsaság érték beállítása.
     *
     * @param value Az új érték
     */
    public void setSpeed(int value) {
        speedAdjuster.setValue(value);
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
     * Szépség érték beállítása.
     *
     * @param value Az új érték
     */
    public void setBeauty(int value) {
        beautyAdjuster.setValue(value);
    }
}