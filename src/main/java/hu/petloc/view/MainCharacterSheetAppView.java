package hu.petloc.view;

import hu.petloc.controller.AbilitiesPanelController;
import hu.petloc.controller.BasicInfoPanelController;
import hu.petloc.controller.CharacterCreationPanelController;
import hu.petloc.controller.LoggerPanelController;
import hu.petloc.controller.MainCharacterSheetAppController;
import hu.petloc.event.GameCharacterEvent;
import hu.petloc.model.GameCharacter;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Fő alkalmazás nézete.
 * Ez az osztály építi fel a teljes alkalmazás felhasználói felületét.
 */
public class MainCharacterSheetAppView {

    private static final int SPACING = 10;

    private MainCharacterSheetAppController controller;
    private BorderPane root;
    private GridPane panelGrid;

    private LoggerPanelController loggerController;
    private AbilitiesPanelController abilitiesController;
    private BasicInfoPanelController basicInfoController;
    private CharacterCreationPanelController characterCreationController;

    /**
     * Konstruktor az alkalmazás nézethez.
     *
     * @param controller Az alkalmazás vezérlője
     */
    public MainCharacterSheetAppView(MainCharacterSheetAppController controller) {
        this.controller = controller;
        createUI();
        createPanels();
    }

    /**
     * Az alkalmazás felület létrehozása.
     */
    private void createUI() {
        // Fő elrendezés létrehozása
        root = new BorderPane();

        // Menüsor létrehozása
        MenuBar menuBar = createMenuBar();

        // Panel rács létrehozása - 3x2 elrendezés
        panelGrid = new GridPane();
        panelGrid.setHgap(SPACING);
        panelGrid.setVgap(SPACING);
        panelGrid.setPadding(new Insets(SPACING));

        // Layout of UI components - felső menüsor
        VBox topContainer = new VBox(SPACING);
        topContainer.setPadding(new Insets(SPACING));
        topContainer.getChildren().add(menuBar);

        root.setTop(topContainer);
        root.setCenter(panelGrid);
    }

    /**
     * A panelek létrehozása és inicializálása.
     */
    private void createPanels() {
        // Logger panel létrehozása
        loggerController = new LoggerPanelController();
        HBox statusBarContainer = new HBox();
        statusBarContainer.setPadding(new Insets(SPACING));
        statusBarContainer.getChildren().add(loggerController.getView().getRoot());
        HBox.setHgrow(loggerController.getView().getRoot(), Priority.ALWAYS);

        root.setBottom(statusBarContainer);

        // Kezdeti üzenet
        loggerController.showMessage("Alkalmazás elindítva. Karakter létrehozása vagy betöltése...");

        // Képességek panel létrehozása
        abilitiesController = new AbilitiesPanelController();

        // Alap információs panel létrehozása
        basicInfoController = new BasicInfoPanelController();

        // Karakter létrehozó panel létrehozása
        characterCreationController = new CharacterCreationPanelController(controller);

        // Karakter létrehozás után eseménykezelő
        characterCreationController.setOnCharacterCreated(event -> {
            GameCharacter character = event.getCharacter();
            controller.loadCharacter(character);
            showInfoPanels();

            // Automatikus mentés
            try {
                String filename = controller.saveCharacterWithGeneratedFilename(character);
                loggerController.showMessage(character.getName() + " karakter létrehozva és elmentve: " + filename);
            } catch (Exception e) {
                loggerController.showMessage(character.getName() + " karakter sikeresen létrehozva, de a mentés sikertelen volt: " + e.getMessage());
            }
        });

        // Indulásnál a karakterkészítő panelt mutatjuk
        showCharacterCreationPanel();
    }

    /**
     * Menüsor létrehozása.
     *
     * @return A létrehozott menüsor
     */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Fájl menü
        Menu fileMenu = new Menu("Fájl");
        MenuItem newItem = new MenuItem("Új karakter");
        newItem.setOnAction(e -> {
            showCharacterCreationPanel();
            loggerController.showMessage("Új karakter létrehozása mód.");
        });

        MenuItem openItem = new MenuItem("Karakter betöltése");
        openItem.setOnAction(e -> controller.openCharacter());

        MenuItem saveItem = new MenuItem("Karakter mentése");
        saveItem.setOnAction(e -> controller.saveCharacter());

        // Szeparátor a fájl és a titkosítási opciók között
        fileMenu.getItems().addAll(newItem, openItem, saveItem, new SeparatorMenuItem());

        // Titkosítási menü elemek
        MenuItem setPasswordItem = new MenuItem("Titkosítási jelszó beállítása");
        setPasswordItem.setOnAction(e -> controller.setEncryptionPassword());

        fileMenu.getItems().addAll(setPasswordItem, new SeparatorMenuItem());

        MenuItem exitItem = new MenuItem("Kilépés");
        exitItem.setOnAction(e -> Platform.exit());
        fileMenu.getItems().add(exitItem);

        // Súgó menü
        Menu helpMenu = new Menu("Súgó");
        MenuItem aboutItem = new MenuItem("Névjegy");
        aboutItem.setOnAction(e -> {
            loggerController.showMessage("Magus Karakterlap - Verzió 1.0");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Névjegy");
            alert.setHeaderText("Magus Karakterlap");
            alert.setContentText("M.A.G.U.S. karakter készítő és kezelő alkalmazás az UTK (Új Törvénykönyv) szabályai szerint.");
            alert.showAndWait();
        });

        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);

        return menuBar;
    }

    /**
     * A karakterkészítő panel megjelenítése.
     */
    public void showCharacterCreationPanel() {
        // Töröljük a panelek eddigi tartalmát
        panelGrid.getChildren().clear();

        // Elhelyezzük a képességek panelt és a karakterkészítő panelt
        panelGrid.add(abilitiesController.getView().getRoot(), 0, 0);
        panelGrid.add(characterCreationController.getView().getRoot(), 1, 0);
    }

    /**
     * Az információs panelek megjelenítése.
     */
    public void showInfoPanels() {
        // Töröljük a panelek eddigi tartalmát
        panelGrid.getChildren().clear();

        // Elhelyezzük a paneleket a megfelelő pozíciókban
        panelGrid.add(abilitiesController.getView().getRoot(), 0, 0);
        panelGrid.add(basicInfoController.getView().getRoot(), 1, 0);

        // További panelek helyei:
        // Jobb felső: (2, 0)
        // Bal alsó: (0, 1)
        // Középső alsó: (1, 1)
        // Jobb alsó: (2, 1)
    }

    /**
     * Hibát jelző ablak megjelenítése.
     *
     * @param title Az ablak címe
     * @param message A hibaüzenet
     */
    public void showErrorAlert(String title, String message) {
        loggerController.showMessage("Hiba: " + message);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Az alkalmazás ablakának megjelenítése.
     *
     * @param stage Az ablak
     */
    public void show(Stage stage) {
        // Az ablak beállítása
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("M.A.G.U.S. Karakterlap");
        stage.setScene(scene);

        // Automatikus méretezés a tartalom alapján
        root.applyCss();
        root.layout();
        stage.setMinWidth(root.prefWidth(-1) + 20);
        stage.setMinHeight(root.prefHeight(-1) + 20);

        stage.show();
    }

    /**
     * A LoggerPanelController lekérése.
     *
     * @return A LoggerPanelController példány
     */
    public LoggerPanelController getLoggerController() {
        return loggerController;
    }

    /**
     * Az AbilitiesPanelController lekérése.
     *
     * @return Az AbilitiesPanelController példány
     */
    public AbilitiesPanelController getAbilitiesController() {
        return abilitiesController;
    }

    /**
     * A BasicInfoPanelController lekérése.
     *
     * @return A BasicInfoPanelController példány
     */
    public BasicInfoPanelController getBasicInfoController() {
        return basicInfoController;
    }
}