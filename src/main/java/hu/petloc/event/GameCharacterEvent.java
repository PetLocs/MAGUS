package hu.petloc.event;

import hu.petloc.model.GameCharacter;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * Karakter esemény osztály.
 * Ez az osztály szolgál a karakterekkel kapcsolatos események kezelésére.
 */
public class GameCharacterEvent extends Event {

    public static final EventType<GameCharacterEvent> ANY =
            new EventType<>(Event.ANY, "CHARACTER");

    public static final EventType<GameCharacterEvent> CHARACTER_CREATED =
            new EventType<>(ANY, "CHARACTER_CREATED");

    private final GameCharacter character;

    /**
     * Konstruktor a karakter eseményhez.
     *
     * @param eventType Az esemény típusa
     * @param character A karakter, amelyhez az esemény kapcsolódik
     */
    public GameCharacterEvent(EventType<GameCharacterEvent> eventType, GameCharacter character) {
        super(eventType);
        this.character = character;
    }

    /**
     * A karakter lekérése, amelyhez az esemény kapcsolódik.
     *
     * @return A karakter
     */
    public GameCharacter getCharacter() {
        return character;
    }
}