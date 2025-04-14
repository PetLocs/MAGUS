package hu.petloc.controller;

import hu.petloc.model.GameCharacter;
import hu.petloc.view.LoggerPanelView;
import javafx.scene.Parent;

/**
 * Naplózó panel vezérlője.
 * Ez az osztály felelős a karakterhez kapcsolódó események naplózásáért.
 * Kivételesen saját méretezéssel rendelkezik, amely nem a BasePanelController-től származik.
 */
public class LoggerPanelController extends BasePanelController {

    private final LoggerPanelView view;

    /**
     * Konstruktor a naplózó panel vezérlőjéhez.
     */
    public LoggerPanelController() {
        this.view = new LoggerPanelView(this);
    }

    /**
     * A naplózó panel nézetének lekérése.
     *
     * @return A nézet
     */
    public LoggerPanelView getView() {
        return view;
    }

    /**
     * A panel gyökér elemének lekérése.
     *
     * @return A gyökér elem
     */
    @Override
    public Parent getRoot() {
        return view.getRoot();
    }

    /**
     * Frissíti a felhasználói felületet a karakter adatai alapján.
     */
    @Override
    protected void updateUI() {
        // Naplóbejegyzések frissítése a karakter alapján
        if (character != null) {
            view.updateLogs(character);
        } else {
            view.clearLogs();
        }
    }

    /**
     * Üzenet naplózása.
     *
     * @param message Az üzenet
     */
    public void showMessage(String message) {
        // Ha van karakter, akkor hozzáadjuk a naplóbejegyzésekhez
        if (character != null) {
            character.addLogEntry(message);
        }

        // Frissítjük a nézetben a naplóbejegyzéseket
        if (character != null) {
            view.updateLogs(character);
        }
    }

    /**
     * Esemény naplózása.
     *
     * @param event Az esemény
     */
    public void logEvent(String event) {
        showMessage(event);
    }

    /**
     * Naplóbejegyzések törlése.
     */
    public void clearLogs() {
        if (character != null) {
            character.clearLogEntries();
        }

        view.clearLogs();
    }
}