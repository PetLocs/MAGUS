package hu.petloc.io;

import hu.petloc.model.GameCharacter;

/**
 * Karakter tároló interfész.
 * Ez az interfész határozza meg a karakterek betöltéséhez és mentéséhez szükséges műveleteket.
 */
public interface CharacterStorage {

    /**
     * Karakter betöltése fájlból.
     *
     * @param filePath A fájl útvonala
     * @return A betöltött karakter
     * @throws Exception Ha hiba történik a betöltés során
     */
    GameCharacter loadCharacter(String filePath) throws Exception;

    /**
     * Karakter mentése fájlba.
     *
     * @param character A mentendő karakter
     * @param filePath A fájl útvonala
     * @throws Exception Ha hiba történik a mentés során
     */
    void saveCharacter(GameCharacter character, String filePath) throws Exception;
}