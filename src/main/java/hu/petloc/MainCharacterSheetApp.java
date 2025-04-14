package hu.petloc;

import hu.petloc.controller.MainCharacterSheetAppController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Főalkalmazás a MAGUS karakterlap rendszerhez.
 * Ez az osztály a belépési pont az alkalmazásba.
 */
public class MainCharacterSheetApp extends Application {

    /**
     * A program belépési pontja.
     *
     * @param args Parancssori argumentumok
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Az alkalmazás inicializálása.
     *
     * @param primaryStage Az elsődleges ablak
     */
    @Override
    public void start(Stage primaryStage) {
        // Létrehozzuk a fő alkalmazásvezérlőt
        MainCharacterSheetAppController controller = new MainCharacterSheetAppController(primaryStage);

        // Megjelenítjük az alkalmazás ablakát
        controller.getView().show(primaryStage);
    }
}