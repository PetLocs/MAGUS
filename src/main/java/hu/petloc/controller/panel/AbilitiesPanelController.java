package hu.petloc.controller.panel;

import hu.petloc.model.character.Character;
import hu.petloc.view.panel.AbilitiesPanelView;
import javafx.scene.layout.Region;

/**
 * Képességek panel kontrollere.
 */
public class AbilitiesPanelController extends BasePanelController {

    private AbilitiesPanelView view;

    /**
     * Létrehoz egy új képességek panel kontrollert.
     */
    public AbilitiesPanelController() {
        this.view = new AbilitiesPanelView(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateUI() {
        if (character != null) {
            view.setStrengthValue(character.getStrength());
            view.setEnduranceValue(character.getEndurance());
            view.setSpeedValue(character.getSpeed());
            view.setDexterityValue(character.getDexterity());
            view.setHealthValue(character.getHealth());
            view.setCharismaValue(character.getCharisma());
            view.setIntelligenceValue(character.getIntelligence());
            view.setWillpowerValue(character.getWillpower());
            view.setAstralValue(character.getAstral());
            view.setPerceptionValue(character.getPerception());
        }
    }

    /**
     * Erő érték frissítése.
     *
     * @param value Az új érték
     */
    public void updateStrength(int value) {
        if (character != null) {
            character.setStrength(value);
        }
    }

    /**
     * Állóképesség érték frissítése.
     *
     * @param value Az új érték
     */
    public void updateEndurance(int value) {
        if (character != null) {
            character.setEndurance(value);
        }
    }

    /**
     * Gyorsaság érték frissítése.
     *
     * @param value Az új érték
     */
    public void updateSpeed(int value) {
        if (character != null) {
            character.setSpeed(value);
        }
    }

    /**
     * Ügyesség érték frissítése.
     *
     * @param value Az új érték
     */
    public void updateDexterity(int value) {
        if (character != null) {
            character.setDexterity(value);
        }
    }

    /**
     * Egészség érték frissítése.
     *
     * @param value Az új érték
     */
    public void updateHealth(int value) {
        if (character != null) {
            character.setHealth(value);
        }
    }

    /**
     * Karizma érték frissítése.
     *
     * @param value Az új érték
     */
    public void updateCharisma(int value) {
        if (character != null) {
            character.setCharisma(value);
        }
    }

    /**
     * Intelligencia érték frissítése.
     *
     * @param value Az új érték
     */
    public void updateIntelligence(int value) {
        if (character != null) {
            character.setIntelligence(value);
        }
    }

    /**
     * Akaraterő érték frissítése.
     *
     * @param value Az új érték
     */
    public void updateWillpower(int value) {
        if (character != null) {
            character.setWillpower(value);
        }
    }

    /**
     * Asztrál érték frissítése.
     *
     * @param value Az új érték
     */
    public void updateAstral(int value) {
        if (character != null) {
            character.setAstral(value);
        }
    }

    /**
     * Érzékelés érték frissítése.
     *
     * @param value Az új érték
     */
    public void updatePerception(int value) {
        if (character != null) {
            character.setPerception(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbilitiesPanelView getView() {
        return view;
    }
}