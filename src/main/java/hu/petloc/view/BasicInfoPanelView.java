package hu.petloc.view;

import hu.petloc.controller.BasePanelController;
import hu.petloc.controller.BasicInfoPanelController;
import hu.petloc.model.GameCharacter;
import hu.petloc.ui.GroupedComboBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Alapvető információs panel nézete.
 * Ez az osztály felelős a karakter alapadatainak megjelenítéséért.
 */
public class BasicInfoPanelView {

    private VBox root;
    private BasicInfoPanelController controller;

    // Beviteli mezők
    private TextField nameField;
    private ComboBox<String> raceComboBox;
    private ComboBox<String> classComboBox;
    private ComboBox<String> subclassComboBox;

    /**
     * Konstruktor az alapvető információs panel nézetéhez.
     *
     * @param controller Az alapvető információs panel vezérlője
     */
    public BasicInfoPanelView(BasicInfoPanelController controller) {
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
        Label titleLabel = new Label("Alapvető karakter adatok");
        titleLabel.setStyle(BasePanelController.TITLE_STYLE);
        titleLabel.setAlignment(Pos.CENTER_LEFT);
        titleLabel.setMaxWidth(Double.MAX_VALUE); // Teljes szélességet kitölti

        // GridPane a mezőkhöz
        GridPane gridPane = new GridPane();
        gridPane.setHgap(BasePanelController.SPACING * 2);
        gridPane.setVgap(BasePanelController.SPACING);
        gridPane.setAlignment(Pos.TOP_LEFT);

        // Oszlop kényszerek beállítása
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(110);
        col1.setMinWidth(110);
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setFillWidth(true);

        gridPane.getColumnConstraints().addAll(col1, col2);

        // Mezők létrehozása és hozzáadása a gridhez
        int row = 0;

        // 1. Név mező
        Label nameLabel = new Label("Név:");
        nameLabel.setPrefWidth(110); // Egységes címke szélesség
        nameField = new TextField();
        nameField.setMaxWidth(Double.MAX_VALUE); // Kitölti a rendelkezésre álló helyet
        nameField.textProperty().addListener((obs, oldVal, newVal) -> controller.setName(newVal));
        gridPane.add(nameLabel, 0, row);
        gridPane.add(nameField, 1, row++);

        // 2. Faj választó
        Label raceLabel = new Label("Faj:");
        raceLabel.setPrefWidth(110); // Egységes címke szélesség
        raceComboBox = new ComboBox<>();
        raceComboBox.getItems().addAll(controller.getRaces());
        raceComboBox.setMaxWidth(Double.MAX_VALUE);
        raceComboBox.setPromptText("...");
        raceComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            controller.setRace(newVal);
            updateClassComboBox();
        });
        gridPane.add(raceLabel, 0, row);
        gridPane.add(raceComboBox, 1, row++);

        // 3. Kaszt választó
        Label classLabel = new Label("Kaszt:");
        classLabel.setPrefWidth(110); // Egységes címke szélesség
        classComboBox = new ComboBox<>();
        classComboBox.setMaxWidth(Double.MAX_VALUE);
        classComboBox.setPromptText("...");
        classComboBox.setDisable(true);
        classComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            controller.setCharacterClass(newVal);
            updateSubclassComboBox();
        });
        gridPane.add(classLabel, 0, row);
        gridPane.add(classComboBox, 1, row++);

        // 4. Alkaszt választó
        Label subclassLabel = new Label("Alkaszt:");
        subclassLabel.setPrefWidth(110); // Egységes címke szélesség
        subclassComboBox = new ComboBox<>();
        subclassComboBox.setMaxWidth(Double.MAX_VALUE);
        subclassComboBox.setPromptText("...");
        subclassComboBox.setDisable(true);
        subclassComboBox.valueProperty().addListener((obs, oldVal, newVal) ->
                controller.setSubclass(newVal));
        gridPane.add(subclassLabel, 0, row);
        gridPane.add(subclassComboBox, 1, row++);

        // A ComboBox-ok CSS beállítása, hogy a prompt szöveg jól jelenjen meg
        String comboBoxStyle = "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);";
        raceComboBox.setStyle(comboBoxStyle);
        classComboBox.setStyle(comboBoxStyle);
        subclassComboBox.setStyle(comboBoxStyle);

        // Elemek hozzáadása a konténerhez
        root.getChildren().addAll(titleLabel, gridPane);
    }

    /**
     * A kaszt ComboBox frissítése a kiválasztott faj alapján.
     */
    private void updateClassComboBox() {
        String selectedRace = raceComboBox.getValue();

        // Inaktiválási logika
        boolean shouldEnableClass = selectedRace != null && !selectedRace.isEmpty() && !selectedRace.equals("...");

        classComboBox.getItems().clear();
        classComboBox.setDisable(!shouldEnableClass);

        if (shouldEnableClass) {
            classComboBox.getItems().addAll(controller.getCharacterClasses());
        }
    }

    /**
     * Az alkaszt ComboBox frissítése a kiválasztott kaszt alapján.
     */
    private void updateSubclassComboBox() {
        String selectedClass = classComboBox.getValue();

        // Inaktiválási logika
        boolean shouldEnableSubclass = selectedClass != null && !selectedClass.isEmpty() && !selectedClass.equals("...");

        subclassComboBox.getItems().clear();
        subclassComboBox.setDisable(!shouldEnableSubclass);

        if (shouldEnableSubclass) {
            subclassComboBox.getItems().addAll(controller.getSubclasses(selectedClass));
        }
    }

    /**
     * Mezők tartalmának törlése.
     */
    public void clearFields() {
        nameField.clear();
        raceComboBox.setValue(null);

        // A többi ComboBox-ot a függőségi rendszer automatikusan visszaállítja
    }

    /**
     * Az adatok érvényességének ellenőrzése.
     *
     * @return Igaz, ha minden kötelező mező ki van töltve
     */
    public boolean validateFields() {
        // Ellenőrizzük a név mezőt
        String name = nameField.getText();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        // Ellenőrizzük a faj ComboBox-ot
        String race = raceComboBox.getValue();
        if (race == null || race.isEmpty() || "...".equals(race)) {
            return false;
        }

        // Ellenőrizzük a kaszt ComboBox-ot
        String characterClass = classComboBox.getValue();
        if (characterClass == null || characterClass.isEmpty() || "...".equals(characterClass)) {
            return false;
        }

        // Az alkaszt nem kötelező

        return true;
    }

    /**
     * Karakter adatok kinyerése a panelből.
     *
     * @param character A karakter, aminek az adatait beállítjuk
     */
    public void applyToCharacter(GameCharacter character) {
        character.setName(nameField.getText());
        character.setRace(raceComboBox.getValue());
        character.setCharacterClass(classComboBox.getValue());

        // Az alkaszt opcionális
        String subclass = subclassComboBox.getValue();
        if (subclass != null && !subclass.isEmpty() && !"...".equals(subclass)) {
            character.setSubclass(subclass);
        } else {
            character.setSubclass("");
        }
    }

    /**
     * Karakter adatainak betöltése a panelbe.
     *
     * @param character A betöltendő karakter
     */
    public void loadFromCharacter(GameCharacter character) {
        if (character == null) {
            clearFields();
            return;
        }

        nameField.setText(character.getName());

        // Faj beállítása
        String race = character.getRace();
        if (race != null && !race.isEmpty()) {
            raceComboBox.setValue(race);
        }

        // Kaszt beállítása
        String characterClass = character.getCharacterClass();
        if (characterClass != null && !characterClass.isEmpty()) {
            classComboBox.setValue(characterClass);
        }

        // Alkaszt beállítása
        String subclass = character.getSubclass();
        if (subclass != null && !subclass.isEmpty()) {
            subclassComboBox.setValue(subclass);
        }
    }

    /**
     * A gyökérelem lekérése.
     *
     * @return A gyökérelem
     */
    public VBox getRoot() {
        return root;
    }
}