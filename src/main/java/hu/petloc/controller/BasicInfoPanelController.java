package hu.petloc.controller;

import hu.petloc.data.StaticData;
import hu.petloc.model.GameCharacter;
import hu.petloc.view.BasicInfoPanelView;
import javafx.scene.Parent;

import java.util.List;

/**
 * Alapvető információs panel vezérlője.
 * Ez az osztály felelős a karakter alapadatainak kezeléséért.
 */
public class BasicInfoPanelController implements BasePanelController {

    private final BasicInfoPanelView view;
    private GameCharacter character;

    /**
     * Konstruktor az alapvető információs panel vezérlőjéhez.
     */
    public BasicInfoPanelController() {
        this.view = new BasicInfoPanelView(this);
    }

    /**
     * Az alapvető információs panel nézetének lekérése.
     *
     * @return A nézet
     */
    public BasicInfoPanelView getView() {
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
     * Karakter beállítása a panelhez.
     *
     * @param character A beállítandó karakter
     */
    @Override
    public void setCharacter(GameCharacter character) {
        this.character = character;

        if (character != null) {
            view.loadFromCharacter(character);
        } else {
            view.clearFields();
        }
    }

    /**
     * A karakter nevének beállítása.
     *
     * @param name Az új név
     */
    public void setName(String name) {
        if (character != null) {
            character.setName(name);
        }
    }

    /**
     * A karakter fajának beállítása.
     *
     * @param race Az új faj
     */
    public void setRace(String race) {
        if (character != null) {
            character.setRace(race);
        }
    }

    /**
     * A karakter kasztjának beállítása.
     *
     * @param characterClass Az új kaszt
     */
    public void setCharacterClass(String characterClass) {
        if (character != null) {
            character.setCharacterClass(characterClass);
        }
    }

    /**
     * A karakter alkasztjának beállítása.
     *
     * @param subclass Az új alkaszt
     */
    public void setSubclass(String subclass) {
        if (character != null) {
            character.setSubclass(subclass);
        }
    }

    /**
     * Elérhető fajok lekérése.
     *
     * @return Az elérhető fajok listája
     */
    public List<String> getRaces() {
        // StaticData-ból lekérjük a fajokat
        // Ezt később a valós implementációval kell helyettesíteni
        return List.of("Ember", "Elf", "Félelf", "Törp", "Udvari ork", "Amund", "Dzsenn", "Khál");
    }

    /**
     * Elérhető kasztok lekérése.
     *
     * @return Az elérhető kasztok listája
     */
    public List<String> getCharacterClasses() {
        // StaticData-ból lekérjük a kasztokat
        return List.of("Harcos", "Gladiátor", "Fejvadász", "Lovag", "Tolvaj",
                "Bárd", "Pap", "Paplovag", "Szerzetes", "Sámán",
                "Boszorkány", "Boszorkánymester", "Tűzvarázsló", "Varázsló");
    }

    /**
     * Elérhető alkasztok lekérése a kiválasztott kaszthoz.
     *
     * @param characterClass A kiválasztott kaszt
     * @return Az elérhető alkasztok listája
     */
    public List<String> getSubclasses(String characterClass) {
        // StaticData-ból lekérjük az alkasztokat a kiválasztott kaszt alapján
        // Ez csak példa adatokat tartalmaz
        if ("Harcos".equals(characterClass)) {
            return List.of("Testőr", "Fejvadász", "Kardművész", "Fegyvermester");
        } else if ("Pap".equals(characterClass)) {
            return List.of("Krad", "Arel", "Kyel", "Adron", "Ellana", "Darton");
        } else {
            return List.of();
        }
    }
}