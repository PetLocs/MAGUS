package hu.petloc.controller;

import hu.petloc.model.GameCharacter;
import hu.petloc.view.AbilitiesPanelView;
import javafx.scene.Parent;

/**
 * Képességek panel vezérlője.
 * Ez az osztály felelős a karakter képességeinek kezeléséért.
 */
public class AbilitiesPanelController implements BasePanelController {

    private final AbilitiesPanelView view;
    private GameCharacter character;

    /**
     * Konstruktor a képességek panel vezérlőjéhez.
     */
    public AbilitiesPanelController() {
        this.view = new AbilitiesPanelView(this);
    }

    /**
     * A képességek panel nézetének lekérése.
     *
     * @return A nézet
     */
    public AbilitiesPanelView getView() {
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
            updateViewFromCharacter();
        }
    }

    /**
     * A nézet frissítése a karakter alapján.
     */
    private void updateViewFromCharacter() {
        // Képességértékek beállítása a nézetben
        view.setStrength(character.getStrength());
        view.setSpeed(character.getSpeed());
        view.setDexterity(character.getDexterity());
        view.setEndurance(character.getEndurance());
        view.setHealth(character.getHealth());
        view.setCharisma(character.getCharisma());
        view.setIntelligence(character.getIntelligence());
        view.setWillpower(character.getWillpower());
        view.setAstral(character.getAstral());
        view.setPerception(character.getPerception());
    }

    /**
     * Erő képesség beállítása.
     *
     * @param value Az új érték
     */
    public void setStrength(int value) {
        if (character != null) {
            character.setStrength(value);
        }
    }

    /**
     * Állóképesség képesség beállítása.
     *
     * @param value Az új érték
     */
    public void setEndurance(int value) {
        if (character != null) {
            character.setEndurance(value);
        }
    }

    /**
     * Ügyesség képesség beállítása.
     *
     * @param value Az új érték
     */
    public void setDexterity(int value) {
        if (character != null) {
            character.setDexterity(value);
        }
    }

    /**
     * Gyorsaság képesség beállítása.
     *
     * @param value Az új érték
     */
    public void setSpeed(int value) {
        if (character != null) {
            character.setSpeed(value);
        }
    }

    /**
     * Intelligencia képesség beállítása.
     *
     * @param value Az új érték
     */
    public void setIntelligence(int value) {
        if (character != null) {
            character.setIntelligence(value);
        }
    }

    /**
     * Akaraterő képesség beállítása.
     *
     * @param value Az új érték
     */
    public void setWillpower(int value) {
        if (character != null) {
            character.setWillpower(value);
        }
    }

    /**
     * Asztrál képesség beállítása.
     *
     * @param value Az új érték
     */
    public void setAstral(int value) {
        if (character != null) {
            character.setAstral(value);
        }
    }

    /**
     * Karizma képesség beállítása.
     *
     * @param value Az új érték
     */
    public void setCharisma(int value) {
        if (character != null) {
            character.setCharisma(value);
        }
    }

    /**
     * Egészség képesség beállítása.
     *
     * @param value Az új érték
     */
    public void setHealth(int value) {
        if (character != null) {
            character.setHealth(value);
        }
    }

    /**
     * Érzékelés képesség beállítása.
     *
     * @param value Az új érték
     */
    public void setPerception(int value) {
        if (character != null) {
            character.setPerception(value);
        }
    }

    /**
     * @deprecated Használd helyette a setCharisma() metódust
     * Szépség képesség beállítása.
     *
     * @param value Az új érték
     */
    @Deprecated
    public void setBeauty(int value) {
        if (character != null) {
            character.setBeauty(value);
        }
    }
}