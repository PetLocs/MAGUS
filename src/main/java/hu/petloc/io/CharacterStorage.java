package hu.petloc.io;

import hu.petloc.model.character.Character;

import java.io.IOException;

/**
 * Karakter tárolási interfész.
 * Ez az interfész meghatározza a karakterek mentésének és betöltésének módját.
 */
public interface CharacterStorage {

    /**
     * Karakter mentése a megadott fájlba.
     *
     * @param character A mentendő karakter
     * @param filepath A fájl elérési útja
     * @throws IOException Ha hiba történik a mentés során
     */
    void saveCharacter(Character character, String filepath) throws IOException;

    /**
     * Karakter betöltése a megadott fájlból.
     *
     * @param filepath A fájl elérési útja
     * @return A betöltött karakter
     * @throws IOException Ha hiba történik a betöltés során
     */
    Character loadCharacter(String filepath) throws IOException;
}