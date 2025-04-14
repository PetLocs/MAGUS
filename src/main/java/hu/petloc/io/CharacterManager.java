package hu.petloc.io;

import hu.petloc.model.character.Character;

import java.io.File;
import java.io.IOException;

/**
 * Karakter kezelő osztály, amely absztrahálja a tárolási megoldást.
 * Ez az osztály singleton mintát követ, így globálisan elérhető.
 */
public class CharacterManager {

    private static CharacterManager instance;
    private CharacterStorage storage;
    private boolean encryptionEnabled = false;

    /**
     * Privát konstruktor a singleton minta részeként.
     */
    private CharacterManager() {
        // Alapértelmezetten titkosítás nélküli tárolás
        this.storage = new JsonCharacterStorage();
    }

    /**
     * A CharacterManager egyetlen példányának lekérése.
     *
     * @return A CharacterManager singleton példánya
     */
    public static synchronized CharacterManager getInstance() {
        if (instance == null) {
            instance = new CharacterManager();
        }
        return instance;
    }

    /**
     * Titkosítás bekapcsolása a megadott jelszóval.
     *
     * @param password A titkosítási jelszó
     */
    public void enableEncryption(String password) {
        this.storage = new EncryptedJsonCharacterStorage(password);
        this.encryptionEnabled = true;
    }

    /**
     * Titkosítás kikapcsolása.
     */
    public void disableEncryption() {
        this.storage = new JsonCharacterStorage();
        this.encryptionEnabled = false;
    }

    /**
     * Karakter mentése a megadott fájlnévvel.
     *
     * @param character A mentendő karakter
     * @param filepath A fájl elérési útja
     * @throws IOException Ha hiba történik a mentés során
     */
    public void saveCharacter(Character character, String filepath) throws IOException {
        // Ellenőrizzük, hogy a könyvtár létezik-e
        File file = new File(filepath);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }

        storage.saveCharacter(character, filepath);
    }

    /**
     * Karakter betöltése a megadott fájlból.
     *
     * @param filepath A fájl elérési útja
     * @return A betöltött karakter
     * @throws IOException Ha hiba történik a betöltés során
     */
    public Character loadCharacter(String filepath) throws IOException {
        return storage.loadCharacter(filepath);
    }

    /**
     * Visszaadja, hogy a titkosítás be van-e kapcsolva.
     *
     * @return true ha a titkosítás be van kapcsolva, false egyébként
     */
    public boolean isEncryptionEnabled() {
        return encryptionEnabled;
    }
}