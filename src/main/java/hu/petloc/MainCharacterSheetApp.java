package hu.petloc;

import hu.petloc.controller.panel.AbilitiesPanelController;
import hu.petloc.controller.panel.BasePanelController;
import hu.petloc.controller.panel.BasicInfoPanelController;
import hu.petloc.controller.panel.CharacterCreationPanelController;
import hu.petloc.controller.panel.LoggerPanelController;
import hu.petloc.io.CharacterManager;
import hu.petloc.model.character.Character;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.Optional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application for the modular character sheet.
 */
public class MainCharacterSheetApp extends Application {

    // Egységes konstansok
    private static final int SPACING = 5;  // Általános hézag mérete

    private Character currentCharacter;
    private final List<BasePanelController> panels = new ArrayList<>();
    private GridPane panelGrid;
    private CharacterCreationPanelController characterCreationPanelController;
    private LoggerPanelController statusBar;
    private CharacterManager characterManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // CharacterManager inicializálása
        characterManager = CharacterManager.getInstance();

        // Create the main layout
        BorderPane root = new BorderPane();

        // Create the menu bar
        MenuBar menuBar = createMenuBar(primaryStage);

        // Create the panel grid - 3x2 elrendezés
        panelGrid = new GridPane();
        panelGrid.setHgap(SPACING);  // Egységes hézag
        panelGrid.setVgap(SPACING);  // Egységes hézag
        panelGrid.setPadding(new Insets(SPACING));  // Egységes padding

        // Create simple status bar
        statusBar = new LoggerPanelController();

        // Teljes szélesség kitöltése
        statusBar.getRoot().setMaxWidth(Double.MAX_VALUE);

        // Create panels
        createPanels();

        // Karakterkészítő panel létrehozása
        characterCreationPanelController = new CharacterCreationPanelController(this);

        characterCreationPanelController.setOnCharacterCreated(character -> {
            loadCharacter(character);
            showInfoPanels();
            statusBar.showMessage(character.getName() + " karakter sikeresen létrehozva.");
        });

        // Indulásnál a karakterkészítő panelt mutatjuk a BasicInfoPanel helyén
        showCharacterCreationPanel();

        // Layout the UI components - felső menüsor
        VBox topContainer = new VBox(SPACING);  // Egységes hézag
        topContainer.setPadding(new Insets(SPACING));  // Egységes padding
        topContainer.getChildren().add(menuBar);

        // A LoggerPanel az alkalmazás aljára kerül, extra padding-gel
        HBox statusBarContainer = new HBox();
        statusBarContainer.setPadding(new Insets(SPACING));  // Egységes padding
        statusBarContainer.getChildren().add(statusBar.getRoot());
        HBox.setHgrow(statusBar.getRoot(), Priority.ALWAYS);

        root.setTop(topContainer);
        root.setCenter(panelGrid);
        root.setBottom(statusBarContainer);

        // Kezdeti üzenet
        statusBar.showMessage("Alkalmazás elindítva. Karakter létrehozása vagy betöltése...");

        // Create the scene
        Scene scene = new Scene(root, 1000, 1000);

        // Set up the stage
        primaryStage.setTitle("Magus Character Sheet");
        primaryStage.setScene(scene);

        // Automatikus méretezés a tartalom alapján
        root.applyCss();
        root.layout();
        primaryStage.setMinWidth(root.prefWidth(-1) + 20);
        primaryStage.setMinHeight(root.prefHeight(-1) + 20);

        primaryStage.show();
    }

    private MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New Character");
        newItem.setOnAction(event -> {
            showCharacterCreationPanel();
            statusBar.showMessage("Új karakter létrehozása...");
        });

        MenuItem loadItem = new MenuItem("Load Character");
        loadItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Character betöltése");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Character Files (*.char)", "*.char"));

            // A Karakterek mappát alapértelmezettként állítjuk be, ha létezik
            File karakterekDir = new File("Karakterek");
            if (karakterekDir.exists() && karakterekDir.isDirectory()) {
                fileChooser.setInitialDirectory(karakterekDir);
            }

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    Character loadedCharacter = characterManager.loadCharacter(selectedFile.getAbsolutePath());
                    loadCharacter(loadedCharacter);
                    showInfoPanels();
                    statusBar.showMessage(loadedCharacter.getName() + " karakter sikeresen betöltve.");
                } catch (Exception e) {
                    showError("Hiba a karakter betöltése során", e.getMessage());
                    statusBar.showMessage("Hiba a karakter betöltése során: " + e.getMessage());
                }
            }
        });

        MenuItem saveItem = new MenuItem("Save Character");
        saveItem.setOnAction(event -> {
            if (currentCharacter == null) {
                showError("Hiba", "Nincs betöltött karakter a mentéshez.");
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Character mentése");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Character Files (*.char)", "*.char"));

            // Biztosítjuk, hogy a Karakterek mappa létezik
            File karakterekDir = new File("Karakterek");
            if (!karakterekDir.exists()) {
                karakterekDir.mkdir();
            }
            fileChooser.setInitialDirectory(karakterekDir);

            // Alapértelmezett fájlnév: név + szint + ". szintű " + kaszt + timestamp
            String defaultFileName = currentCharacter.getName() + " " +
                    currentCharacter.getLevel() + ". szintű " +
                    currentCharacter.getCharacterClass() + " " +
                    System.currentTimeMillis() + ".char";
            fileChooser.setInitialFileName(defaultFileName);

            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    characterManager.saveCharacter(currentCharacter, selectedFile.getAbsolutePath());
                    statusBar.showMessage(currentCharacter.getName() + " karakter sikeresen mentve.");
                } catch (Exception e) {
                    showError("Hiba a karakter mentése során", e.getMessage());
                    statusBar.showMessage("Hiba a karakter mentése során: " + e.getMessage());
                }
            }
        });

        MenuItem encryptionItem = new MenuItem("Set Encryption Password");
        encryptionItem.setOnAction(event -> {
            setEncryptionPassword();
        });

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(event -> Platform.exit());

        fileMenu.getItems().addAll(newItem, loadItem, saveItem, new SeparatorMenuItem(), encryptionItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    /**
     * Titkosítási jelszó beállítása
     */
    private void setEncryptionPassword() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Titkosítási jelszó");
        dialog.setHeaderText("Állítsd be a titkosítási jelszót a karakterfájlokhoz");
        dialog.setContentText("Jelszó:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String password = result.get();
            if (password.isEmpty()) {
                characterManager.disableEncryption();
                statusBar.showMessage("Titkosítás kikapcsolva.");
            } else {
                characterManager.enableEncryption(password);
                statusBar.showMessage("Titkosítási jelszó beállítva.");
            }
        }
    }

    /**
     * Panelek létrehozása
     */
    private void createPanels() {
        // Alap információs panel
        BasicInfoPanelController basicInfoPanel = new BasicInfoPanelController();
        panels.add(basicInfoPanel);

        // Képességek panel
        AbilitiesPanelController abilitiesPanel = new AbilitiesPanelController();
        panels.add(abilitiesPanel);
    }

    /**
     * Hibaüzenet megjelenítése
     *
     * @param title Az ablak címe
     * @param message A hibaüzenet
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Karakter betöltése a panelekbe
     *
     * @param character A betöltendő karakter
     */
    public void loadCharacter(Character character) {
        currentCharacter = character;
        for (BasePanelController panel : panels) {
            panel.setCharacter(character);
        }
    }

    /**
     * Karakterkészítő panel megjelenítése
     */
    private void showCharacterCreationPanel() {
        // Töröljük a paneleket
        panelGrid.getChildren().clear();

        // A karakterkészítő panelt adjuk hozzá
        Region creationPanelRoot = characterCreationPanelController.getRoot();
        panelGrid.add(creationPanelRoot, 0, 0);
        GridPane.setRowSpan(creationPanelRoot, 2);
    }

    /**
     * Karakter információs panelek megjelenítése
     */
    private void showInfoPanels() {
        // Töröljük a paneleket
        panelGrid.getChildren().clear();

        // Pozicionáljuk a paneleket a gridben
        int col = 0;
        int row = 0;
        int maxCol = 3; // Három oszlopos elrendezés

        for (BasePanelController panel : panels) {
            Region panelRoot = panel.getView().getRoot();

            // Hozzáadjuk a panelt a gridhez
            panelGrid.add(panelRoot, col, row);

            // Következő pozíció számítása
            col++;
            if (col >= maxCol) {
                col = 0;
                row++;
            }
        }
    }
}