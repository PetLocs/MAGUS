package hu.petloc.controller.panel;

import hu.petloc.model.character.Character;
import hu.petloc.view.panel.LoggerPanelView;
import javafx.scene.layout.Region;

/**
 * Státuszüzeneteket kezelő panel controller.
 */
public class LoggerPanelController extends BasePanelController {

    private final LoggerPanelView view;

    /**
     * Konstruktor.
     */
    public LoggerPanelController() {
        // Nézetkomponens létrehozása
        this.view = new LoggerPanelView(this);
    }

    /**
     * Üzenet megjelenítése.
     *
     * @param message A megjelenítendő üzenet
     */
    public void showMessage(String message) {
        view.showMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCharacter(Character character) {
        // A logger nem kapcsolódik közvetlenül karakterhez, így itt nincs teendő
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoggerPanelView getView() {
        return view;
    }
}