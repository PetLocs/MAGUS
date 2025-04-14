package hu.petloc.controller.panel;

import hu.petloc.model.character.Character;
import hu.petloc.view.panel.BasicInfoPanelView;
import javafx.scene.layout.Region;

/**
 * Alap karakterinformációs panel kontrollere.
 */
public class BasicInfoPanelController extends BasePanelController {

    private BasicInfoPanelView view;

    /**
     * Létrehoz egy új karakter alap információs panel kontrollert.
     */
    public BasicInfoPanelController() {
        this.view = new BasicInfoPanelView(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateUI() {
        if (character != null) {
            view.setNameValue(character.getName());
            view.setRaceValue(character.getRace());
            view.setClassValue(character.getCharacterClass());
            view.setOriginValue(character.getOrigin());
            view.setLevelValue(Integer.toString(character.getLevel()));
            view.setXpValue(Integer.toString(character.getExperiencePoints()));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return A kontroller által kezelt nézet
     */
    @Override
    public BasicInfoPanelView getView() {
        return view;
    }
}