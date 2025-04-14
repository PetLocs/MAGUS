package hu.petloc.view;

import hu.petloc.controller.CharacterCreationPanelController;
import hu.petloc.data.StaticData;
import hu.petloc.model.GameCharacter;
import hu.petloc.ui.GroupedComboBox;
import hu.petloc.ui.NumberAdjuster;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Karakter létrehozó panel nézete.
 * Ez az osztály felelős a karakter létrehozási folyamat megjelenítéséért.
 */
public class CharacterCreationPanelView {

    private VBox root;
    private final CharacterCreationPanelController controller;

    // UI komponensek
    private TextField nameField;
    private ComboBox<String> raceComboBox;
    private ComboBox<String> classComboBox;
    private ComboBox<String> subclassComboBox;
    private TextField ageField;
    private ComboBox<String> alignmentComboBox;
    private GroupedComboBox religionComboBox;
    private TextField homelandField;
    private TextField schoolField;
    private NumberAdjuster levelAdjuster;
    private Button saveButton;

    /**
     * Konstruktor a karakter létrehozó panel nézetéhez.
     *
     * @param controller A karakter létrehozó panel vezérlője
     */
    public CharacterCreationPanelView(CharacterCreationPanelController controller) {
        this.controller = controller;
        createUI();
        setupEventHandlers();
        setupBindings();
    }

    /**
     * Felhasználói felület létrehozása.
     */
    private void createUI() {
        // Konténer a teljes tartalomnak
        root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Főcím
        Label titleLabel = new Label("Új karakter létrehozása");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        // GridPane a form elemeknek
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));

        // Oszlopok beállítása
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setFillWidth(true);
        formGrid.getColumnConstraints().addAll(col1, col2);

        // Form elemek
        int row = 0;

        // Név
        Label nameLabel = new Label("Név:");
        nameField = new TextField();
        nameField.setPromptText("Karakter neve");
        formGrid.add(nameLabel, 0, row);
        formGrid.add(nameField, 1, row++);

        // Faj
        Label raceLabel = new Label("Faj:");
        raceComboBox = new ComboBox<>();
        formGrid.add(raceLabel, 0, row);
        formGrid.add(raceComboBox, 1, row++);

        // Kaszt
        Label classLabel = new Label("Kaszt:");
        classComboBox = new ComboBox<>();
        formGrid.add(classLabel, 0, row);
        formGrid.add(classComboBox, 1, row++);

        // Alkaszt
        Label subclassLabel = new Label("Alkaszt:");
        subclassComboBox = new ComboBox<>();
        formGrid.add(subclassLabel, 0, row);
        formGrid.add(subclassComboBox, 1, row++);

        // Életkor
        Label ageLabel = new Label("Életkor:");
        ageField = new TextField();
        ageField.setPromptText("Életkor (opcionális)");
        formGrid.add(ageLabel, 0, row);
        formGrid.add(ageField, 1, row++);

        // Jellem
        Label alignmentLabel = new Label("Jellem:");
        alignmentComboBox = new ComboBox<>();
        formGrid.add(alignmentLabel, 0, row);
        formGrid.add(alignmentComboBox, 1, row++);

        // Vallás - A StaticData nem ad lehetőséget csoportosított listára, ezért mintaadatot használunk
        Label religionLabel = new Label("Vallás:");
        // Példa vallások csoportosítva
        List<String> religionSample = createSampleReligions();
        religionComboBox = new GroupedComboBox(religionSample);
        formGrid.add(religionLabel, 0, row);
        formGrid.add(religionComboBox, 1, row++);

        // Szülőföld
        Label homelandLabel = new Label("Szülőföld:");
        homelandField = new TextField();
        homelandField.setPromptText("Karakter szülőföldje");
        formGrid.add(homelandLabel, 0, row);
        formGrid.add(homelandField, 1, row++);

        // Iskola
        Label schoolLabel = new Label("Iskola:");
        schoolField = new TextField();
        schoolField.setPromptText("Karakter iskolája");
        formGrid.add(schoolLabel, 0, row);
        formGrid.add(schoolField, 1, row++);

        // Szint és Mentés gomb (egy sorban)
        Label levelLabel = new Label("Szint:");
        levelAdjuster = new NumberAdjuster(1, 20, 1);
        saveButton = new Button("Mentés");
        saveButton.setStyle("-fx-font-weight: bold;");
        saveButton.setOnAction(e -> saveCharacter());

        HBox levelAndSaveBox = new HBox(10);
        levelAndSaveBox.setAlignment(Pos.CENTER_LEFT);
        levelAndSaveBox.getChildren().add(levelAdjuster);
        HBox.setHgrow(levelAdjuster, Priority.ALWAYS);
        levelAndSaveBox.getChildren().add(saveButton);

        formGrid.add(levelLabel, 0, row);
        formGrid.add(levelAndSaveBox, 1, row++);

        // Adatok betöltése
        loadComboBoxData();

        // Elemek hozzáadása a konténerhez
        root.getChildren().addAll(titleLabel, formGrid);
    }

    /**
     * Eseménykezelők beállítása.
     */
    private void setupEventHandlers() {
        // Faj változása
        raceComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                controller.getTempCharacter().setRace(newVal);
                updateClassOptions();
            }
        });

        // Kaszt változása
        classComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                controller.getTempCharacter().setCharacterClass(newVal);
                updateSubclassOptions();
            }
        });

        // Alkaszt változása
        subclassComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                controller.getTempCharacter().setSubclass(newVal);
            }
        });

        // Jellem változása
        alignmentComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                controller.getTempCharacter().setAlignment(newVal);
            }
        });

        // Vallás változása
        religionComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.startsWith("--")) {
                controller.getTempCharacter().setReligion(newVal);
            }
        });

        // Szint változása
        levelAdjuster.valueProperty().addListener((obs, oldVal, newVal) -> {
            controller.getTempCharacter().setLevel(newVal.intValue());
        });
    }

    /**
     * Adatkötések beállítása.
     */
    private void setupBindings() {
        GameCharacter character = controller.getTempCharacter();

        // Név
        nameField.textProperty().addListener((obs, oldVal, newVal) -> {
            character.setName(newVal);
        });

        // Életkor
        ageField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (newVal != null && !newVal.isEmpty()) {
                    character.setAge(Integer.parseInt(newVal));
                }
            } catch (NumberFormatException e) {
                // Érvénytelen érték esetén nem állítjuk be
            }
        });

        // Szülőföld
        homelandField.textProperty().addListener((obs, oldVal, newVal) -> {
            character.setHomeland(newVal);
        });

        // Iskola
        schoolField.textProperty().addListener((obs, oldVal, newVal) -> {
            character.setSchool(newVal);
        });
    }

    /**
     * ComboBox-ok feltöltése adatokkal.
     */
    private void loadComboBoxData() {
        // Faj ComboBox feltöltése
        List<String> races = StaticData.getInstance().getRaces();
        if (!races.isEmpty()) {
            raceComboBox.setItems(FXCollections.observableArrayList(races));
            raceComboBox.getSelectionModel().selectFirst();
        }

        // Jellem ComboBox feltöltése
        List<String> alignments = StaticData.getInstance().getAlignments();
        if (!alignments.isEmpty()) {
            alignmentComboBox.setItems(FXCollections.observableArrayList(alignments));
            alignmentComboBox.getSelectionModel().selectFirst();
        }

        // Induló állapot beállítása
        updateClassOptions();
    }

    /**
     * Kaszt opciók frissítése a kiválasztott faj alapján.
     */
    private void updateClassOptions() {
        String selectedRace = raceComboBox.getValue();
        // Itt még nem szűrünk faj szerint, minden kasztot betöltünk
        List<String> classes = StaticData.getInstance().getCharacterClasses();

        classComboBox.setItems(FXCollections.observableArrayList(classes));
        if (!classes.isEmpty()) {
            classComboBox.getSelectionModel().selectFirst();
        } else {
            classComboBox.getSelectionModel().clearSelection();
        }

        updateSubclassOptions();
    }

    /**
     * Alkaszt opciók frissítése a kiválasztott kaszt alapján.
     */
    private void updateSubclassOptions() {
        String selectedClass = classComboBox.getValue();
        List<String> subclasses = selectedClass != null && !selectedClass.isEmpty() ?
                StaticData.getInstance().getSubclasses(selectedClass) : new ArrayList<>();

        subclassComboBox.setItems(FXCollections.observableArrayList(subclasses));
        if (!subclasses.isEmpty()) {
            subclassComboBox.getSelectionModel().selectFirst();
        } else {
            subclassComboBox.getSelectionModel().clearSelection();
        }
    }

    /**
     * Példa vallások létrehozása csoportosítva a GroupedComboBox számára
     */
    private List<String> createSampleReligions() {
        // A valódi adatok helyett egy egyszerűsített példát használunk
        return Arrays.asList(
                "-- Pyarroni vallások --",
                "Adron",
                "Arel",
                "Dreina",
                "Kyel",
                "-- Gorviki vallások --",
                "Ranagol",
                "-- Törpe vallások --",
                "Kadal",
                "Tooma",
                "-- Elf vallások --",
                "Urria",
                "Veela",
                "-- Egyéb vallások --",
                "Ősi hit",
                "Krad"
        );
    }

    /**
     * Karakter mentése.
     */
    private void saveCharacter() {
        controller.createCharacter();
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