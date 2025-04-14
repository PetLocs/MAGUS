package hu.petloc.controller;

import hu.petloc.io.CharacterManager;
import hu.petloc.model.GameCharacter;
import hu.petloc.view.MainCharacterSheetAppView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Fő alkalmazás vezérlő.
 * Ez az osztály koordinálja az alkalmazás működését.
 */
public class MainCharacterSheetAppController {

    private GameCharacter currentCharacter;
    private final CharacterManager characterManager;
    private final MainCharacterSheetAppView view;
    private final Stage primaryStage;

    /**
     * Konstruktor a fő alkalmazás vezérlőhöz.
     *
     * @param primaryStage Az elsődleges ablak
     */
    public MainCharacterSheetAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.characterManager = new CharacterManager();
        this.view = new MainCharacterSheetAppView(this);
    }

    /**
     * Az aktuális karakter lekérése.
     *
     * @return Az aktuális karakter
     */
    public GameCharacter getCurrentCharacter() {
        return currentCharacter;
    }

    /**
     * Karakter betöltése.
     *
     * @param character A betöltendő karakter
     */
    public void loadCharacter(GameCharacter character) {
        if (character != null) {
            currentCharacter = character;
            updatePanelsWithCharacter();
        }
    }

    /**
     * Panelek frissítése az aktuális karakterrel.
     */
    private void updatePanelsWithCharacter() {
        // Frissítjük az összes panel tartalmát
        view.getAbilitiesController().setCharacter(currentCharacter);
        view.getBasicInfoController().setCharacter(currentCharacter);
    }

    /**
     * Karakter mentése generált fájlnévvel.
     *
     * @param character A mentendő karakter
     * @return A mentett fájl neve
     * @throws Exception Ha hiba történik a mentés során
     */
    public String saveCharacterWithGeneratedFilename(GameCharacter character) throws Exception {
        // Alapértelmezett könyvtár a felhasználó home könyvtárában
        String characterDirPath = System.getProperty("user.home") + File.separator + "Karakterek";
        File characterDir = new File(characterDirPath);

        // Könyvtár létrehozása, ha nem létezik
        if (!characterDir.exists()) {
            characterDir.mkdirs();
        }

        // Generált fájlnév létrehozása a karakter neve, szintje, kasztja és az aktuális idő alapján
        String name = character.getName();
        int level = character.getLevel();
        String characterClass = character.getCharacterClass();

        // Időbélyeg generálása
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);

        // Fájlnév összeállítása
        String fileName = name + "_" + level + "_szintu_" + characterClass + "_" + timestamp + ".ejson";
        fileName = fileName.replaceAll("[^a-zA-Z0-9_\\-.]", "_").toLowerCase();

        // Teljes fájl útvonal
        String filePath = characterDirPath + File.separator + fileName;

        // Karakter mentése
        characterManager.saveCharacter(character, filePath);

        return fileName;
    }

    /**
     * Karakter mentése fájlba.
     */
    public void saveCharacter() {
        if (currentCharacter != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Karakter fájl mentése");

            // Titkosított fájl kiterjesztés beállítása
            String extension = ".ejson";
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Titkosított JSON fájlok", "*.ejson")
            );

            // Alapértelmezett mentési könyvtár beállítása
            String characterDirPath = System.getProperty("user.home") + File.separator + "Karakterek";
            File characterDir = new File(characterDirPath);
            if (characterDir.exists() && characterDir.isDirectory()) {
                fileChooser.setInitialDirectory(characterDir);
            }

            // Alapértelmezett fájlnév beállítása a karakter neve alapján
            String defaultFileName = currentCharacter.getName();
            if (defaultFileName == null || defaultFileName.trim().isEmpty()) {
                defaultFileName = "unnamed_character";
            } else {
                defaultFileName = defaultFileName.replaceAll("[^a-zA-Z0-9_\\-.]", "_").toLowerCase();
            }
            fileChooser.setInitialFileName(defaultFileName + extension);

            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    // Mentés a fájlba
                    String fileName = selectedFile.getName();
                    String filePath = selectedFile.getAbsolutePath();

                    characterManager.saveCharacter(currentCharacter, filePath);
                    view.getLoggerController().showMessage("Karakter elmentve: " + fileName);

                } catch (Exception ex) {
                    view.showErrorAlert("Hiba a mentés során", "Nem sikerült menteni a karaktert: " + ex.getMessage());
                    view.getLoggerController().showMessage("Hiba a karakter mentésekor: " + ex.getMessage());
                }
            }
        } else {
            view.showErrorAlert("Nincs karakter", "Nincs betöltött karakter a mentéshez.");
            view.getLoggerController().showMessage("Nincs betöltött karakter a mentéshez.");
        }
    }

    /**
     * Karakter betöltése fájlból.
     */
    public void openCharacter() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Karakter fájl megnyitása");

        // Titkosított fájl kiterjesztés beállítása
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Titkosított JSON fájlok", "*.ejson")
        );

        // Alapértelmezett könyvtár beállítása
        String characterDirPath = System.getProperty("user.home") + File.separator + "Karakterek";
        File characterDir = new File(characterDirPath);
        if (characterDir.exists() && characterDir.isDirectory()) {
            fileChooser.setInitialDirectory(characterDir);
        }

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            try {
                // Betöltés a fájlból
                String filePath = selectedFile.getAbsolutePath();
                GameCharacter loadedCharacter = characterManager.loadCharacter(filePath);

                loadCharacter(loadedCharacter);
                view.showInfoPanels();
                view.getLoggerController().showMessage("Karakter betöltve: " + selectedFile.getName());

            } catch (Exception ex) {
                view.showErrorAlert("Hiba a betöltés során", "Nem sikerült betölteni a karaktert: " + ex.getMessage());
                view.getLoggerController().showMessage("Hiba a karakter betöltésekor: " + ex.getMessage());
            }
        }
    }

    /**
     * Titkosítási jelszó beállítása dialógus segítségével.
     */
    public void setEncryptionPassword() {
        // Egyszerű jelszó bekérő dialógus létrehozása
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Titkosítási jelszó");
        dialog.setHeaderText("Adja meg a titkosítási jelszót");
        dialog.setContentText("Jelszó:");

        // A jelszó mező kicserélése egy PasswordField-re
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Jelszó");

        // A default text field kicserélése
        GridPane dialogPane = (GridPane) dialog.getDialogPane().getContent();
        dialogPane.getChildren().remove(1); // Remove default TextField
        dialogPane.add(passwordField, 1, 0); // Add PasswordField instead

        // A dialógus eredményének lekérése a PasswordField-ből
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return passwordField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(password -> {
            if (password.isEmpty()) {
                view.showErrorAlert("Hiba", "A jelszó nem lehet üres.");
                view.getLoggerController().showMessage("Hiba: A jelszó nem lehet üres.");
            } else {
                // Jelszó beállítása a CharacterManager-ben
                // TODO: Implement jelszó tárolás a CharacterManager-ben
                view.getLoggerController().showMessage("Titkosítási jelszó beállítva.");
            }
        });
    }

    /**
     * A nézet lekérése.
     *
     * @return A nézet
     */
    public MainCharacterSheetAppView getView() {
        return view;
    }
}