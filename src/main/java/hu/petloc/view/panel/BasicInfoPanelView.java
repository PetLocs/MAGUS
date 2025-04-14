package hu.petloc.view.panel;

import hu.petloc.controller.panel.BasePanelController;
import hu.petloc.controller.panel.BasicInfoPanelController;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Alap karakterinformációs panel nézete.
 */
public class BasicInfoPanelView extends BasePanelView {

    private VBox root;

    // UI komponensek
    private Label nameValue;
    private Label raceValue;
    private Label classValue;
    private Label originValue;
    private Label levelValue;
    private Label xpValue;

    /**
     * Létrehoz egy új karakter alap információs panel nézetet.
     *
     * @param controller A nézethez tartozó kontroller
     */
    public BasicInfoPanelView(BasicInfoPanelController controller) {
        super(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createUI() {
        root = new VBox(BasePanelController.getSpacing());

        // Cím
        Label titleLabel = new Label("Karakter Információk");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        root.getChildren().add(titleLabel);

        // Információs grid
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(BasePanelController.getSpacing() * 2);
        infoGrid.setVgap(BasePanelController.getSpacing());

        // Név információ
        Label nameLabel = new Label("Név:");
        nameValue = new Label("-");
        infoGrid.add(nameLabel, 0, 0);
        infoGrid.add(nameValue, 1, 0);

        // Faj információ
        Label raceLabel = new Label("Faj:");
        raceValue = new Label("-");
        infoGrid.add(raceLabel, 0, 1);
        infoGrid.add(raceValue, 1, 1);

        // Kaszt információ
        Label classLabel = new Label("Kaszt:");
        classValue = new Label("-");
        infoGrid.add(classLabel, 0, 2);
        infoGrid.add(classValue, 1, 2);

        // Származás információ
        Label originLabel = new Label("Származás:");
        originValue = new Label("-");
        infoGrid.add(originLabel, 0, 3);
        infoGrid.add(originValue, 1, 3);

        // Szint információ
        Label levelLabel = new Label("Szint:");
        levelValue = new Label("1");
        infoGrid.add(levelLabel, 0, 4);
        infoGrid.add(levelValue, 1, 4);

        // Tapasztalati pont információ
        Label xpLabel = new Label("TP:");
        xpValue = new Label("0");
        infoGrid.add(xpLabel, 0, 5);
        infoGrid.add(xpValue, 1, 5);

        root.getChildren().add(infoGrid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Region getRoot() {
        return root;
    }

    /**
     * Beállítja a karakternév értékét.
     *
     * @param name A megjelenítendő név
     */
    public void setNameValue(String name) {
        nameValue.setText(name != null && !name.isEmpty() ? name : "-");
    }

    /**
     * Beállítja a karakter faj értékét.
     *
     * @param race A megjelenítendő faj
     */
    public void setRaceValue(String race) {
        raceValue.setText(race != null && !race.isEmpty() ? race : "-");
    }

    /**
     * Beállítja a karakter kaszt értékét.
     *
     * @param characterClass A megjelenítendő kaszt
     */
    public void setClassValue(String characterClass) {
        classValue.setText(characterClass != null && !characterClass.isEmpty() ? characterClass : "-");
    }

    /**
     * Beállítja a karakter származás értékét.
     *
     * @param origin A megjelenítendő származás
     */
    public void setOriginValue(String origin) {
        originValue.setText(origin != null && !origin.isEmpty() ? origin : "-");
    }

    /**
     * Beállítja a karakter szint értékét.
     *
     * @param level A megjelenítendő szint
     */
    public void setLevelValue(String level) {
        levelValue.setText(level != null && !level.isEmpty() ? level : "1");
    }

    /**
     * Beállítja a karakter tapasztalati pont értékét.
     *
     * @param xp A megjelenítendő tapasztalati pont
     */
    public void setXpValue(String xp) {
        xpValue.setText(xp != null && !xp.isEmpty() ? xp : "0");
    }
}