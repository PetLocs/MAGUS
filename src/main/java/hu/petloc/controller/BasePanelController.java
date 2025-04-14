package hu.petloc.controller;

import hu.petloc.model.GameCharacter;
import javafx.scene.Parent;

/**
 * Alapvető panel vezérlő interfész.
 * Minden panel vezérlő ennek az interfésznek a leszármazottja.
 */
public interface BasePanelController {

    /**
     * Karakter beállítása a panelhez.
     *
     * @param character A beállítandó karakter
     */
    void setCharacter(GameCharacter character);

    /**
     * A panel gyökér elemének lekérése.
     *
     * @return A gyökér elem
     */
    Parent getRoot();
}