package hu.petloc.view.panel;

import hu.petloc.controller.panel.AbilitiesPanelController;
import hu.petloc.controller.panel.BasePanelController;
import hu.petloc.ui.NumberAdjuster;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Képességek panel nézete.
 */
public class AbilitiesPanelView extends BasePanelView {

    private VBox root;
    private NumberAdjuster strengthAdjuster;
    private NumberAdjuster enduranceAdjuster;
    private NumberAdjuster speedAdjuster;
    private NumberAdjuster dexterityAdjuster;
    private NumberAdjuster healthAdjuster;
    private NumberAdjuster charismaAdjuster;
    private NumberAdjuster intelligenceAdjuster;
    private NumberAdjuster willpowerAdjuster;
    private NumberAdjuster astralAdjuster;
    private NumberAdjuster perceptionAdjuster;

    /**
     * Létrehoz egy új képességek panel nézetet.
     *
     * @param controller A nézethez tartozó kontroller
     */
    public AbilitiesPanelView(AbilitiesPanelController controller) {
        super(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createUI() {
        root = new VBox(BasePanelController.getSpacing());

        // Cím
        Label titleLabel = new Label("Képességek");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        root.getChildren().add(titleLabel);

        // Képességek grid
        GridPane abilitiesGrid = new GridPane();
        abilitiesGrid.setHgap(BasePanelController.getSpacing() * 2);
        abilitiesGrid.setVgap(BasePanelController.getSpacing());

        // Fizikai képességek
        int row = 0;

        // Erő
        Label strengthLabel = new Label("Erő:");
        strengthAdjuster = new NumberAdjuster(10, 3, 25);
        bindStrengthAdjuster();
        abilitiesGrid.add(strengthLabel, 0, row);
        abilitiesGrid.add(strengthAdjuster, 1, row++);

        // Állóképesség
        Label enduranceLabel = new Label("Állóképesség:");
        enduranceAdjuster = new NumberAdjuster(10, 3, 25);
        bindEnduranceAdjuster();
        abilitiesGrid.add(enduranceLabel, 0, row);
        abilitiesGrid.add(enduranceAdjuster, 1, row++);

        // Gyorsaság
        Label speedLabel = new Label("Gyorsaság:");
        speedAdjuster = new NumberAdjuster(10, 3, 25);
        bindSpeedAdjuster();
        abilitiesGrid.add(speedLabel, 0, row);
        abilitiesGrid.add(speedAdjuster, 1, row++);

        // Ügyesség
        Label dexterityLabel = new Label("Ügyesség:");
        dexterityAdjuster = new NumberAdjuster(10, 3, 25);
        bindDexterityAdjuster();
        abilitiesGrid.add(dexterityLabel, 0, row);
        abilitiesGrid.add(dexterityAdjuster, 1, row++);

        // Egészség
        Label healthLabel = new Label("Egészség:");
        healthAdjuster = new NumberAdjuster(10, 3, 25);
        bindHealthAdjuster();
        abilitiesGrid.add(healthLabel, 0, row);
        abilitiesGrid.add(healthAdjuster, 1, row++);

        // Szociális és mentális képességek

        // Karizma
        Label charismaLabel = new Label("Karizma:");
        charismaAdjuster = new NumberAdjuster(10, 3, 25);
        bindCharismaAdjuster();
        abilitiesGrid.add(charismaLabel, 0, row);
        abilitiesGrid.add(charismaAdjuster, 1, row++);

        // Intelligencia
        Label intelligenceLabel = new Label("Intelligencia:");
        intelligenceAdjuster = new NumberAdjuster(10, 3, 25);
        bindIntelligenceAdjuster();
        abilitiesGrid.add(intelligenceLabel, 0, row);
        abilitiesGrid.add(intelligenceAdjuster, 1, row++);

        // Akaraterő
        Label willpowerLabel = new Label("Akaraterő:");
        willpowerAdjuster = new NumberAdjuster(10, 3, 25);
        bindWillpowerAdjuster();
        abilitiesGrid.add(willpowerLabel, 0, row);
        abilitiesGrid.add(willpowerAdjuster, 1, row++);

        // Asztrál
        Label astralLabel = new Label("Asztrál:");
        astralAdjuster = new NumberAdjuster(10, 3, 25);
        bindAstralAdjuster();
        abilitiesGrid.add(astralLabel, 0, row);
        abilitiesGrid.add(astralAdjuster, 1, row++);

        // Érzékelés
        Label perceptionLabel = new Label("Érzékelés:");
        perceptionAdjuster = new NumberAdjuster(10, 3, 25);
        bindPerceptionAdjuster();
        abilitiesGrid.add(perceptionLabel, 0, row);
        abilitiesGrid.add(perceptionAdjuster, 1, row);

        root.getChildren().add(abilitiesGrid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Region getRoot() {
        return root;
    }

    /**
     * Erő értékének beállítása.
     *
     * @param value Az új érték
     */
    public void setStrengthValue(int value) {
        strengthAdjuster.setValue(value);
    }

    /**
     * Állóképesség értékének beállítása.
     *
     * @param value Az új érték
     */
    public void setEnduranceValue(int value) {
        enduranceAdjuster.setValue(value);
    }

    /**
     * Gyorsaság értékének beállítása.
     *
     * @param value Az új érték
     */
    public void setSpeedValue(int value) {
        speedAdjuster.setValue(value);
    }

    /**
     * Ügyesség értékének beállítása.
     *
     * @param value Az új érték
     */
    public void setDexterityValue(int value) {
        dexterityAdjuster.setValue(value);
    }

    /**
     * Egészség értékének beállítása.
     *
     * @param value Az új érték
     */
    public void setHealthValue(int value) {
        healthAdjuster.setValue(value);
    }

    /**
     * Karizma értékének beállítása.
     *
     * @param value Az új érték
     */
    public void setCharismaValue(int value) {
        charismaAdjuster.setValue(value);
    }

    /**
     * Intelligencia értékének beállítása.
     *
     * @param value Az új érték
     */
    public void setIntelligenceValue(int value) {
        intelligenceAdjuster.setValue(value);
    }

    /**
     * Akaraterő értékének beállítása.
     *
     * @param value Az új érték
     */
    public void setWillpowerValue(int value) {
        willpowerAdjuster.setValue(value);
    }

    /**
     * Asztrál értékének beállítása.
     *
     * @param value Az új érték
     */
    public void setAstralValue(int value) {
        astralAdjuster.setValue(value);
    }

    /**
     * Érzékelés értékének beállítása.
     *
     * @param value Az új érték
     */
    public void setPerceptionValue(int value) {
        perceptionAdjuster.setValue(value);
    }

    /**
     * Erő adjuster bekötése a kontrollerhez.
     */
    private void bindStrengthAdjuster() {
        strengthAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ((AbilitiesPanelController) controller).updateStrength(newVal.intValue());
            }
        });
    }

    /**
     * Állóképesség adjuster bekötése a kontrollerhez.
     */
    private void bindEnduranceAdjuster() {
        enduranceAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ((AbilitiesPanelController) controller).updateEndurance(newVal.intValue());
            }
        });
    }

    /**
     * Gyorsaság adjuster bekötése a kontrollerhez.
     */
    private void bindSpeedAdjuster() {
        speedAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ((AbilitiesPanelController) controller).updateSpeed(newVal.intValue());
            }
        });
    }

    /**
     * Ügyesség adjuster bekötése a kontrollerhez.
     */
    private void bindDexterityAdjuster() {
        dexterityAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ((AbilitiesPanelController) controller).updateDexterity(newVal.intValue());
            }
        });
    }

    /**
     * Egészség adjuster bekötése a kontrollerhez.
     */
    private void bindHealthAdjuster() {
        healthAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ((AbilitiesPanelController) controller).updateHealth(newVal.intValue());
            }
        });
    }

    /**
     * Karizma adjuster bekötése a kontrollerhez.
     */
    private void bindCharismaAdjuster() {
        charismaAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ((AbilitiesPanelController) controller).updateCharisma(newVal.intValue());
            }
        });
    }

    /**
     * Intelligencia adjuster bekötése a kontrollerhez.
     */
    private void bindIntelligenceAdjuster() {
        intelligenceAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ((AbilitiesPanelController) controller).updateIntelligence(newVal.intValue());
            }
        });
    }

    /**
     * Akaraterő adjuster bekötése a kontrollerhez.
     */
    private void bindWillpowerAdjuster() {
        willpowerAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ((AbilitiesPanelController) controller).updateWillpower(newVal.intValue());
            }
        });
    }

    /**
     * Asztrál adjuster bekötése a kontrollerhez.
     */
    private void bindAstralAdjuster() {
        astralAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ((AbilitiesPanelController) controller).updateAstral(newVal.intValue());
            }
        });
    }

    /**
     * Érzékelés adjuster bekötése a kontrollerhez.
     */
    private void bindPerceptionAdjuster() {
        perceptionAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ((AbilitiesPanelController) controller).updatePerception(newVal.intValue());
            }
        });
    }
}