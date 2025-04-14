package hu.petloc.view;

import hu.petloc.controller.CharacterCreationPanelController;
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
import java.util.List;

/**
 * Karakter létrehozó panel nézete.
 * Ez az osztály felelős a karakter létrehozási folyamat megjelenítéséért.
 */
public class CharacterCreationPanelView {

    /**
     * Speciális érték a ComboBox-ok számára, ami a promptText-et fogja megjeleníteni
     */
    private static final String PROMPT_VALUE = "...";

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
        raceComboBox.setPromptText("...");
        formGrid.add(raceLabel, 0, row);
        formGrid.add(raceComboBox, 1, row++);

        // Kaszt
        Label classLabel = new Label("Kaszt:");
        classComboBox = new ComboBox<>();
        classComboBox.setPromptText("...");
        formGrid.add(classLabel, 0, row);
        formGrid.add(classComboBox, 1, row++);

        // Alkaszt
        Label subclassLabel = new Label("Alkaszt:");
        subclassComboBox = new ComboBox<>();
        subclassComboBox.setPromptText("...");
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
        alignmentComboBox.setPromptText("...");
        formGrid.add(alignmentLabel, 0, row);
        formGrid.add(alignmentComboBox, 1, row++);

        // Vallás - Csoportosított vallások a JSON adatok alapján
        Label religionLabel = new Label("Vallás:");
        // Vallások csoportosítva
        List<String> groupedReligions = createGroupedReligions();
        religionComboBox = new GroupedComboBox(groupedReligions);
        religionComboBox.setPromptText("...");
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
        // Biztosítsuk induláskor is a promptText megjelenítését
        refreshPromptText(raceComboBox);
        refreshPromptText(classComboBox);
        refreshPromptText(subclassComboBox);

        // Faj változása
        raceComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Ha van új érték, beállítjuk a karaktert és frissítjük a kaszt opciókat
            if (newVal != null) {
                controller.getTempCharacter().setRace(newVal);
                updateClassOptions();
                // Megjegyzés: Az updateClassOptions() most már meghívja az updateSubclassOptions()-t
                // így nem kell itt külön meghívni
            }
            // Ha nullra váltunk (pl. kiürítjük a kiválasztást)
            else {
                controller.getTempCharacter().setRace(null);
                updateClassOptions(); // Ez letiltja a kaszt és alkaszt ComboBox-okat
            }
        });

        // Kaszt változása
        classComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                controller.getTempCharacter().setCharacterClass(newVal);
                // Az alkaszt opciók frissítése a kiválasztott kaszt alapján
                updateSubclassOptions();
            }
        });

        // Alkaszt változása
        subclassComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Ha "-" van kiválasztva, akkor nullt állítunk be
                if ("-".equals(newVal)) {
                    controller.getTempCharacter().setSubclass(null);
                } else {
                    controller.getTempCharacter().setSubclass(newVal);
                }
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
            if (newVal != null) {
                // Ha csoportcím vagy "-" van kiválasztva, akkor nullt állítunk be
                if (newVal.startsWith("--") || "-".equals(newVal)) {
                    controller.getTempCharacter().setReligion(null);
                } else {
                    controller.getTempCharacter().setReligion(newVal);
                }
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
     * ComboBox-ok feltöltése adatokkal és kezdeti állapot beállítása.
     */
    private void loadComboBoxData() {
        // Faj ComboBox feltöltése - a prompt értéket is hozzáadjuk a listához
        List<String> races = controller.getRaces();
        if (!races.isEmpty()) {
            // Hozzáadjuk a promptot az első elemnek
            List<String> racesWithPrompt = new ArrayList<>();
            racesWithPrompt.add(PROMPT_VALUE);
            racesWithPrompt.addAll(races);

            // Kikapcsoljuk a promptText-et, mert most már az értékek között van
            raceComboBox.setPromptText("");

            // Beállítjuk a prompttel együtt az itemeket
            raceComboBox.setItems(FXCollections.observableArrayList(racesWithPrompt));

            // Kezdetben a promptot választjuk ki
            raceComboBox.setValue(PROMPT_VALUE);
        }

        // Jellem ComboBox feltöltése
        List<String> alignments = controller.getAlignments();
        if (!alignments.isEmpty()) {
            alignmentComboBox.setItems(FXCollections.observableArrayList(alignments));
            alignmentComboBox.getSelectionModel().selectFirst();
        }

        // Kaszt és alkaszt ComboBox-ok inaktívak kezdetben és a prompt érték látszik
        List<String> promptList = new ArrayList<>();
        promptList.add(PROMPT_VALUE);

        classComboBox.setDisable(true);
        classComboBox.setPromptText("");
        classComboBox.setItems(FXCollections.observableArrayList(promptList));
        classComboBox.setValue(PROMPT_VALUE);

        subclassComboBox.setDisable(true);
        subclassComboBox.setPromptText("");
        subclassComboBox.setItems(FXCollections.observableArrayList(promptList));
        subclassComboBox.setValue(PROMPT_VALUE);
    }



    /**
     * Kaszt opciók frissítése a kiválasztott faj alapján.
     * Ha a korábban kiválasztott kaszt elérhető az új fajnál, akkor megtartja azt.
     */
    private void updateClassOptions() {
        String selectedRace = raceComboBox.getValue();

        // Ha nincs faj kiválasztva, a kaszt ComboBox inaktív
        if (selectedRace == null || selectedRace.isEmpty() || PROMPT_VALUE.equals(selectedRace)) {
            // Prompt értéket adjunk a ComboBox-nak null helyett
            List<String> promptList = new ArrayList<>();
            promptList.add(PROMPT_VALUE);

            classComboBox.setPromptText("");  // Kikapcsoljuk a promptText-et
            classComboBox.setItems(FXCollections.observableArrayList(promptList));
            classComboBox.setValue(PROMPT_VALUE);
            classComboBox.setDisable(true); // Inaktív állapot

            // Ha nincs faj, akkor alkaszt sem választható
            subclassComboBox.setPromptText("");  // Kikapcsoljuk a promptText-et
            subclassComboBox.setItems(FXCollections.observableArrayList(promptList));
            subclassComboBox.setValue(PROMPT_VALUE);
            subclassComboBox.setDisable(true); // Inaktív állapot

            return;
        }

        // Ha van faj kiválasztva, a kaszt ComboBox aktív
        classComboBox.setDisable(false);

        // Mentsük el a jelenlegi kaszt kiválasztást
        String currentClass = classComboBox.getValue();

        // A fajhoz elérhető kasztok lekérése a controller-től
        List<String> classes = controller.getClassesForRace(selectedRace);

        // Hozzáadjuk a promptot az első elemnek
        List<String> classesWithPrompt = new ArrayList<>();
        classesWithPrompt.add(PROMPT_VALUE);
        classesWithPrompt.addAll(classes);

        // Kikapcsoljuk a promptText-et, mert most már az értékek között van
        classComboBox.setPromptText("");

        // Frissítsük a ComboBox tartalmát
        classComboBox.setItems(FXCollections.observableArrayList(classesWithPrompt));

        // Ha a korábban kiválasztott kaszt szerepel az új listában, tegyük azt aktívvá
        if (currentClass != null && !PROMPT_VALUE.equals(currentClass) && classes.contains(currentClass)) {
            classComboBox.setValue(currentClass);
            System.out.println("Korábbi kaszt megtartva: " + currentClass);
        }
        // Egyébként állítsuk a prompt értékre
        else {
            classComboBox.setValue(PROMPT_VALUE);
            System.out.println("Kaszt prompt értékre állítva");
        }

        updateSubclassOptions();
    }

    /**
     * Segédmetódus a promptText frissítéséhez
     */
    private void refreshPromptText(ComboBox<?> comboBox) {
        // A prompt text értékének ideiglenes nullázása és visszaállítása kényszeríti a UI frissítést
        String promptText = comboBox.getPromptText();
        comboBox.setPromptText(null);
        comboBox.setPromptText(promptText);
    }

    /**
     * Alkaszt opciók frissítése a kiválasztott kaszt alapján.
     * Ha a korábban kiválasztott alkaszt elérhető az új kasztnál, akkor megtartja azt.
     */
    private void updateSubclassOptions() {
        String selectedClass = classComboBox.getValue();

        // Ha nincs kaszt kiválasztva, vagy prompt érték, az alkaszt ComboBox inaktív
        if (selectedClass == null || selectedClass.isEmpty() || PROMPT_VALUE.equals(selectedClass)) {
            // Prompt értéket adjunk a ComboBox-nak null helyett
            List<String> promptList = new ArrayList<>();
            promptList.add(PROMPT_VALUE);

            subclassComboBox.setPromptText("");  // Kikapcsoljuk a promptText-et
            subclassComboBox.setItems(FXCollections.observableArrayList(promptList));
            subclassComboBox.setValue(PROMPT_VALUE);
            subclassComboBox.setDisable(true); // Inaktív állapot
            return;
        }

        // Ha van kaszt kiválasztva, az alkaszt ComboBox aktív
        subclassComboBox.setDisable(false);

        // Mentsük el a jelenlegi alkaszt kiválasztást
        String currentSubclass = subclassComboBox.getValue();

        // A kaszthoz elérhető alkasztok lekérése a controller-től
        List<String> subclasses = controller.getSubclasses(selectedClass);

        // Hozzáadjuk a "-" opciót, ha erre szükség van
        if (!subclasses.contains("-")) {
            subclasses.add(0, "-");
        }

        // Hozzáadjuk a promptot az első elemnek
        List<String> subclassesWithPrompt = new ArrayList<>();
        subclassesWithPrompt.add(PROMPT_VALUE);
        subclassesWithPrompt.addAll(subclasses);

        // Kikapcsoljuk a promptText-et, mert most már az értékek között van
        subclassComboBox.setPromptText("");

        // Frissítsük a ComboBox tartalmát
        subclassComboBox.setItems(FXCollections.observableArrayList(subclassesWithPrompt));

        // Ha a korábban kiválasztott alkaszt szerepel az új listában, tegyük azt aktívvá
        if (currentSubclass != null && !PROMPT_VALUE.equals(currentSubclass) && subclasses.contains(currentSubclass)) {
            subclassComboBox.setValue(currentSubclass);
            System.out.println("Korábbi alkaszt megtartva: " + currentSubclass);
        }
        // Egyébként állítsuk a prompt értékre
        else {
            subclassComboBox.setValue(PROMPT_VALUE);
            System.out.println("Alkaszt prompt értékre állítva");
        }
    }

    /**
     * A vallások csoportosítása kategóriák szerint a GroupedComboBox számára
     */
    private List<String> createGroupedReligions() {
        return controller.getGroupedReligions();
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