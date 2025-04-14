package hu.petloc.controller;

import hu.petloc.event.GameCharacterEvent;
import hu.petloc.model.GameCharacter;
import hu.petloc.view.CharacterCreationPanelView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;

/**
 * Karakter létrehozó panel vezérlője.
 * Ez az osztály felelős a karakter létrehozásának vezérléséért.
 */
public class CharacterCreationPanelController {

    private final CharacterCreationPanelView view;
    private final MainCharacterSheetAppController mainController;
    private GameCharacter tempCharacter;
    private ObjectProperty<EventHandler<GameCharacterEvent>> onCharacterCreated = new SimpleObjectProperty<>();

    /**
     * Konstruktor a karakter létrehozó panel vezérlőjéhez.
     *
     * @param mainController A fő alkalmazás vezérlője
     */
    public CharacterCreationPanelController(MainCharacterSheetAppController mainController) {
        this.mainController = mainController;
        this.view = new CharacterCreationPanelView(this);
        this.tempCharacter = new GameCharacter();
    }

    /**
     * A karakter létrehozó panel nézetének lekérése.
     *
     * @return A nézet
     */
    public CharacterCreationPanelView getView() {
        return view;
    }

    /**
     * Az ideiglenes karakter lekérése.
     *
     * @return Az ideiglenes karakter
     */
    public GameCharacter getTempCharacter() {
        return tempCharacter;
    }

    /**
     * Az ideiglenes karakter beállítása.
     *
     * @param character Az új karakter
     */
    public void setTempCharacter(GameCharacter character) {
        this.tempCharacter = character;
    }

    /**
     * Karakter létrehozása a megadott adatok alapján.
     *
     * @return Az új karakter objektum
     */
    public GameCharacter createCharacter() {
        // Itt hozzuk létre az új karaktert
        GameCharacter newCharacter = tempCharacter;

        // Esemény kiváltása a karakter létrehozásáról
        if (onCharacterCreated.get() != null) {
            GameCharacterEvent event = new GameCharacterEvent(GameCharacterEvent.CHARACTER_CREATED, newCharacter);
            onCharacterCreated.get().handle(event);
        }

        return newCharacter;
    }

    /**
     * A karakter létrehozás eseménykezelő beállítása.
     *
     * @param handler Az eseménykezelő
     */
    public void setOnCharacterCreated(EventHandler<GameCharacterEvent> handler) {
        onCharacterCreated.set(handler);
    }

    /**
     * A karakter létrehozás esemény tulajdonság lekérése.
     *
     * @return Az esemény tulajdonság
     */
    public ObjectProperty<EventHandler<GameCharacterEvent>> onCharacterCreatedProperty() {
        return onCharacterCreated;
    }

    /**
     * A karakter létrehozás eseménykezelő lekérése.
     *
     * @return Az eseménykezelő
     */
    public EventHandler<GameCharacterEvent> getOnCharacterCreated() {
        return onCharacterCreated.get();
    }
}