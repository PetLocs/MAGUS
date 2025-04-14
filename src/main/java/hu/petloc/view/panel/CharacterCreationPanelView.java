package hu.petloc.view.panel;

import hu.petloc.controller.panel.BasePanelController;
import hu.petloc.controller.panel.CharacterCreationPanelController;
import hu.petloc.ui.GroupedComboBox;
import hu.petloc.ui.NumberField;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Collections;

/**
 * Karakter létrehozó panel nézete.
 */
public class CharacterCreationPanelView extends BasePanelView {

    private VBox root;
    private TextField nameField;
    private ComboBox<String> raceComboBox;
    private GroupedComboBox<String> classComboBox;
    private ComboBox<String> subclassComboBox;
    private ComboBox<String> originComboBox;
    private NumberField ageField;

    /**
     * Létrehoz egy új karakter létrehozó panel nézetet.
     *
     * @param controller A nézethez tartozó kontroller
     */
    public CharacterCreationPanelView(CharacterCreationPanelController controller) {
        super(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createUI() {
        root = new VBox(BasePanelController.getSpacing());

        // Cím
        Label titleLabel = new Label("Új Karakter Létrehozása");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        root.getChildren().add(titleLabel);

        // Űrlap
        GridPane formGrid = new GridPane();
        formGrid.setHgap(BasePanelController.getSpacing() * 2);
        formGrid.setVgap(BasePanelController.getSpacing());

        // Oszlopok beállítása a responsiveness érdekében
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(100);
        col1.setMinWidth(80);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        formGrid.getColumnConstraints().addAll(col1, col2);

        int row = 0;

        // Név mező
        Label nameLabel = new Label("Név:");
        nameField = new TextField();
        formGrid.add(nameLabel, 0, row);
        formGrid.add(nameField, 1, row++);

        // Faj mező
        Label raceLabel = new Label("Faj:");
        raceComboBox = new ComboBox<>();
        formGrid.add(raceLabel, 0, row);
        formGrid.add(raceComboBox, 1, row++);

        // Kaszt mező
        Label classLabel = new Label("Kaszt:");
        classComboBox = new GroupedComboBox<>(
                Collections.emptyList(),
                item -> item,
                item -> item != null && item.startsWith("--")
        );
        classComboBox.setOnAction(e -> {
            String selectedClass = classComboBox.getValue();
            ((CharacterCreationPanelController) controller).updateSubclasses(selectedClass);
        });
        formGrid.add(classLabel, 0, row);
        formGrid.add(classComboBox, 1, row++);

        // Alkaszt mező
        Label subclassLabel = new Label("Alkaszt:");
        subclassComboBox = new ComboBox<>();
        subclassComboBox.setDisable(true);
        formGrid.add(subclassLabel, 0, row);
        formGrid.add(subclassComboBox, 1, row++);

        // Származás mező
        Label originLabel = new Label("Származás:");
        originComboBox = new ComboBox<>();
        formGrid.add(originLabel, 0, row);
        formGrid.add(originComboBox, 1, row++);

        // Kor mező
        Label ageLabel = new Label("Kor:");
        ageField = new NumberField();
        ageField.setIntValue(20);
        formGrid.add(ageLabel, 0, row);
        formGrid.add(ageField, 1, row++);

        root.getChildren().add(formGrid);

        // Gombok
        HBox buttonBox = new HBox(BasePanelController.getSpacing());
        Button createButton = new Button("Karakter Létrehozása");
        createButton.setOnAction(e -> ((CharacterCreationPanelController) controller).createCharacter());
        buttonBox.getChildren().add(createButton);

        root.getChildren().add(buttonBox);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Region getRoot() {
        return root;
    }

    /**
     * Karakter név lekérése.
     *
     * @return A beírt karakter név
     */
    public String getName() {
        return nameField.getText();
    }

    /**
     * Kiválasztott faj lekérése.
     *
     * @return A kiválasztott faj
     */
    public String getSelectedRace() {
        return raceComboBox.getValue();
    }

    /**
     * Kiválasztott kaszt lekérése.
     *
     * @return A kiválasztott kaszt
     */
    public String getSelectedClass() {
        return classComboBox.getValue();
    }

    /**
     * Kiválasztott alkaszt lekérése.
     *
     * @return A kiválasztott alkaszt
     */
    public String getSelectedSubclass() {
        return subclassComboBox.getValue();
    }

    /**
     * Kiválasztott származás lekérése.
     *
     * @return A kiválasztott származás
     */
    public String getSelectedOrigin() {
        return originComboBox.getValue();
    }

    /**
     * Kor érték lekérése.
     *
     * @return A beírt kor
     */
    public int getAge() {
        return ageField.getIntValue();
    }

    /**
     * Fajok listájának beállítása.
     *
     * @param races A fajok listája
     */
    public void setRaces(List<String> races) {
        if (races != null) {
            raceComboBox.setItems(FXCollections.observableArrayList(races));
        }
    }

    /**
     * Kasztok listájának beállítása.
     *
     * @param classes A kasztok listája
     */
    public void setClasses(List<String> classes) {
        if (classes != null) {
            classComboBox.setItems(FXCollections.observableArrayList(classes));
        }
    }

    /**
     * Alkasztok listájának beállítása.
     *
     * @param subclasses Az alkasztok listája
     */
    public void setSubclasses(List<String> subclasses) {
        if (subclasses != null && !subclasses.isEmpty()) {
            subclassComboBox.setItems(FXCollections.observableArrayList(subclasses));
            subclassComboBox.setDisable(false);
        } else {
            clearSubclasses();
        }
    }

    /**
     * Alkasztok törlése.
     */
    public void clearSubclasses() {
        subclassComboBox.getItems().clear();
        subclassComboBox.setValue(null);
        subclassComboBox.setDisable(true);
    }

    /**
     * Származások listájának beállítása.
     *
     * @param origins A származások listája
     */
    public void setOrigins(List<String> origins) {
        if (origins != null) {
            originComboBox.setItems(FXCollections.observableArrayList(origins));
        }
    }

    /**
     * Hibaüzenet megjelenítése.
     *
     * @param message A megjelenítendő üzenet
     */
    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}