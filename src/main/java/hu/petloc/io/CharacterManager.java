package hu.petloc.io;

import hu.petloc.model.GameCharacter;

import java.io.File;

/**
 * Karakter menedzser osztály.
 * Ez az osztály felelős a karakterek betöltéséért és mentéséért.
 */
public class CharacterManager {

    private CharacterStorage storage;

    /**
     * Konstruktor a karakter menedzserhez.
     */
    public CharacterManager() {
        storage = new EncryptedJsonCharacterStorage();
    }

    /**
     * Karakter betöltése fájlból.
     *
     * @param filePath A fájl útvonala
     * @return A betöltött karakter
     * @throws Exception Ha hiba történik a betöltés során
     */
    public GameCharacter loadCharacter(String filePath) throws Exception {
        // Ellenőrizzük, hogy a fájl létezik-e
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new Exception("A megadott fájl nem létezik: " + filePath);
        }

        try {
            return storage.loadCharacter(filePath);
        } catch (Exception e) {
            throw new Exception("Hiba történt a karakter betöltése közben: " + e.getMessage());
        }
    }

    /**
     * Karakter mentése fájlba.
     *
     * @param character A mentendő karakter
     * @param filePath A fájl útvonala
     * @throws Exception Ha hiba történik a mentés során
     */
    public void saveCharacter(GameCharacter character, String filePath) throws Exception {
        if (character == null) {
            throw new Exception("Nem lehet null karaktert menteni!");
        }

        try {
            // Mappa létrehozása, ha szükséges
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            storage.saveCharacter(character, filePath);
        } catch (Exception e) {
            throw new Exception("Hiba történt a karakter mentése közben: " + e.getMessage());
        }
    }
}