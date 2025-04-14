package hu.petloc.controller;

import hu.petloc.data.StaticData;
import hu.petloc.event.GameCharacterEvent;
import hu.petloc.model.GameCharacter;
import hu.petloc.view.CharacterCreationPanelView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Karakter létrehozó panel vezérlője.
 * Ez az osztály felelős a karakter létrehozásának vezérléséért és az adatok kezeléséért.
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
        this.tempCharacter = new GameCharacter();
        this.view = new CharacterCreationPanelView(this);
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

    /**
     * Fajok listájának lekérése.
     *
     * @return Fajok listája
     */
    public List<String> getRaces() {
        return StaticData.getInstance().getRaces();
    }

    /**
     * Jellemek listájának lekérése.
     *
     * @return Jellemek listája
     */
    public List<String> getAlignments() {
        return StaticData.getInstance().getAlignments();
    }

    /**
     * Adott fajhoz tartozó kasztok lekérése.
     *
     * @param raceName A faj neve
     * @return A fajhoz elérhető kasztok listája
     */
    public List<String> getClassesForRace(String raceName) {
        if (raceName == null || raceName.isEmpty()) {
            return new ArrayList<>();
        }
        return StaticData.getInstance().getClassesForRace(raceName);
    }

    /**
     * Adott kaszthoz tartozó alkasztok lekérése.
     *
     * @param className A kaszt neve
     * @return A kaszthoz elérhető alkasztok "-" lehetőséggel
     */
    public List<String> getSubclasses(String className) {
        if (className == null || className.isEmpty()) {
            return new ArrayList<>();
        }

        // Alkasztok lekérése a StaticData-ból
        List<String> subclasses = new ArrayList<>();

        // "-" hozzáadása a lista elejére
        subclasses.add("-");

        // A StaticData-ból kapott alkasztok hozzáadása
        subclasses.addAll(StaticData.getInstance().getSubclasses(className));

        return subclasses;
    }

    /**
     * A vallások csoportosítása kategóriák szerint a GroupedComboBox számára
     *
     * @return Csoportosított vallások listája "-" opcióval
     */
    public List<String> getGroupedReligions() {
        // Vallások lekérése
        List<String> religions = StaticData.getInstance().getReligions();
        List<String> groupedReligions = new ArrayList<>();

        // Ha nincs vallás lista (pl. a JSON fájl nem tölthető be), akkor visszatérünk egy üres listával
        if (religions == null || religions.isEmpty()) {
            return new ArrayList<>();
        }

        // "-" lehetőség hozzáadása a lista elejére
        groupedReligions.add("-");

        // Csoportok hozzáadása
        groupedReligions.add("-- Pyarroni Pantheon --");
        groupedReligions.addAll(filterReligions(religions, Arrays.asList(
                "Adron", "Alborne", "Antoh", "Arel", "Darton", "Della", "Dreina",
                "Ellana", "Gilron (Gi-Elron)", "Krad", "Kyel", "Noir", "Uwel"
        )));

        groupedReligions.add("-- Elf vallások --");
        groupedReligions.addAll(filterReligions(religions, Arrays.asList(
                "Eidhil K'Meakhan, a Dalokban Élő", "Finna Lies, a Korokon Tanító",
                "Flidais D'amatel, a Lombok Őre", "Gor Hannain, a Tűzben Élő",
                "Hantien Harran, az Árnyékban Neszező", "Magon L'levar, a Szavakat Ismerő",
                "Mallior, az Éjben Kacagó", "Moranna Naranol, a Homály Lakója",
                "Narmiraen, a Ködökön Járó", "Nemathiel Airaven, a Tűzhelyre Vigyázó",
                "Rhienna Malvaureen, A Tűz Csiholója, a Mosolyokban Honoló",
                "Siena Boralisse, az Érintéssel Enyhítő", "Tyssa L'imenel, a Vér Nélkül Való",
                "Veela Luminatar, az Egykor Volt Fényesség", "Verrion H'anthall, a Sötétség Szája, a Mindeneket Elnyelő"
        )));

        groupedReligions.add("-- Törpe vallások --");
        groupedReligions.addAll(filterReligions(religions, Arrays.asList(
                "Bul-Ruurig, a Kőatya", "Kadal, a Tárnákat Zengető",
                "Tooma, a Fejszés", "Zimah, a Szökevény"
        )));

        groupedReligions.add("-- Káosz entitások --");
        groupedReligions.addAll(filterReligions(religions, name -> name.startsWith("Káosz-")));

        groupedReligions.add("-- Dzsad vallások --");
        groupedReligions.addAll(filterReligions(religions, Arrays.asList(
                "Doldzsah", "Dzsah", "Galradzsa"
        )));

        groupedReligions.add("-- Gorviki vallások --");
        groupedReligions.addAll(filterReligions(religions, Arrays.asList(
                "Ranagol", "Ranil"
        )));

        groupedReligions.add("-- Kráni vallások --");
        groupedReligions.addAll(filterReligions(religions, Arrays.asList(
                "Bhaer-Shadagg", "Gar Bokkar", "Ghazga", "Hram, a Mélységben Lakozó",
                "Hurag Dhaur, a Szürke Öreg", "Nagy Vordak", "Oothre, a Titokzatos",
                "Tha'ushur, a Szunnyadó", "Ughjorbagan, a Falánk"
        )));

        groupedReligions.add("-- Amund vallások --");
        groupedReligions.addAll(filterReligions(religions, Arrays.asList(
                "Amhe-Ramun (Amhe-Ramuun)", "Nesire (Nethiree)",
                "Refis (Reefith)", "Themes (Theemeth)"
        )));

        groupedReligions.add("-- Egyéb vallások --");
        List<String> knownReligions = new ArrayList<>(groupedReligions);
        knownReligions.removeIf(name -> name.startsWith("--"));
        for (String religion : religions) {
            if (!knownReligions.contains(religion)) {
                groupedReligions.add(religion);
            }
        }

        return groupedReligions;
    }

    /**
     * Szűri a vallásokat a megadott nevek alapján
     */
    private List<String> filterReligions(List<String> allReligions, List<String> religionNames) {
        return allReligions.stream()
                .filter(religionNames::contains)
                .collect(Collectors.toList());
    }

    /**
     * Szűri a vallásokat a megadott predikátum alapján
     */
    private List<String> filterReligions(List<String> allReligions, Predicate<String> predicate) {
        return allReligions.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}