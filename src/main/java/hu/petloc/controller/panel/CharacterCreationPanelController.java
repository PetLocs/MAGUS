package hu.petloc.controller.panel;

import hu.petloc.MainCharacterSheetApp;
import hu.petloc.model.data.StaticData;
import hu.petloc.model.character.Character;
import hu.petloc.view.panel.CharacterCreationPanelView;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.function.Consumer;

/**
 * Karakter létrehozó panel kontrollere.
 */
public class CharacterCreationPanelController extends BasePanelController {

    private final CharacterCreationPanelView view;
    private final MainCharacterSheetApp mainApp;
    private Consumer<Character> onCharacterCreated;

    /**
     * Létrehoz egy új karakter létrehozó panel kontrollert.
     *
     * @param mainApp A fő alkalmazás referencia
     */
    public CharacterCreationPanelController(MainCharacterSheetApp mainApp) {
        this.mainApp = mainApp;
        this.view = new CharacterCreationPanelView(this);
        setupData();
    }

    /**
     * Kezdeti adatok beállítása.
     */
    private void setupData() {
        // Fajok betöltése
        List<String> races = StaticData.getInstance().getRaces();
        view.setRaces(races);

        // Kasztok betöltése
        view.setClasses(StaticData.getInstance().getCharacterClasses());

        // Származások betöltése
        view.setOrigins(StaticData.getInstance().getOrigins());
    }

    /**
     * Karakter létrehozása.
     */
    public void createCharacter() {
        String name = view.getName();
        String race = view.getSelectedRace();
        String characterClass = view.getSelectedClass();

        if (name == null || name.trim().isEmpty()) {
            view.showErrorMessage("A név megadása kötelező!");
            return;
        }

        if (race == null || race.isEmpty()) {
            view.showErrorMessage("A faj kiválasztása kötelező!");
            return;
        }

        if (characterClass == null || characterClass.isEmpty()) {
            view.showErrorMessage("A kaszt kiválasztása kötelező!");
            return;
        }

        // Karakter létrehozása
        Character character = new Character(name, race, characterClass);
        character.setOrigin(view.getSelectedOrigin());
        character.setSubclass(view.getSelectedSubclass());
        character.setAge(view.getAge());

        // Visszahívás
        if (onCharacterCreated != null) {
            onCharacterCreated.accept(character);
        }
    }

    /**
     * Alkaszt választó frissítése a kiválasztott kaszt alapján.
     *
     * @param selectedClass A kiválasztott kaszt
     */
    public void updateSubclasses(String selectedClass) {
        if (selectedClass != null && !selectedClass.isEmpty()) {
            List<String> subclasses = StaticData.getInstance().getSubclasses(selectedClass);
            view.setSubclasses(subclasses);
        } else {
            view.clearSubclasses();
        }
    }

    /**
     * Beállítja a karakter létrehozásakor meghívandó callback-et.
     *
     * @param callback A callback interfész
     */
    public void setOnCharacterCreated(Consumer<Character> callback) {
        this.onCharacterCreated = callback;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateUI() {
        // Nincs szükség adatok betöltésére, hiszen új karaktert hozunk létre
    }

    @Override
    public CharacterCreationPanelView getView() {
        return view;
    }
}