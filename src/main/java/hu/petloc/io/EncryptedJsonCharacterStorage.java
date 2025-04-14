package hu.petloc.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.petloc.model.character.Character;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Titkosított JSON karakter tárolási implementáció.
 * Ez az osztály a karaktereket titkosított JSON formátumban tárolja.
 */
public class EncryptedJsonCharacterStorage implements CharacterStorage {

    private final ObjectMapper objectMapper;
    private final String encryptionPassword;

    /**
     * Konstruktor a titkosítási jelszóval.
     *
     * @param encryptionPassword A titkosítási jelszó
     */
    public EncryptedJsonCharacterStorage(String encryptionPassword) {
        this.objectMapper = new ObjectMapper();
        this.encryptionPassword = encryptionPassword;
        // Egyedi modul regisztrálása a JavaFX property-k kezeléséhez
        objectMapper.registerModule(new JavaFXPropertyModule());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveCharacter(Character character, String filepath) throws IOException {
        // Objektum szerializálása JSON-ba
        String json = objectMapper.writeValueAsString(character);

        // JSON titkosítása
        String encryptedJson = EncryptionUtil.encrypt(json, encryptionPassword);

        // Titkosított JSON mentése fájlba
        Files.write(Paths.get(filepath), encryptedJson.getBytes());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character loadCharacter(String filepath) throws IOException {
        // Ellenőrizzük, hogy a fájl létezik-e
        if (!Files.exists(Paths.get(filepath))) {
            throw new IOException("A megadott karakterfájl nem létezik: " + filepath);
        }

        try {
            // Titkosított tartalom beolvasása
            String encryptedJson = new String(Files.readAllBytes(Paths.get(filepath)));

            // Tartalom visszafejtése
            String decryptedJson = EncryptionUtil.decrypt(encryptedJson, encryptionPassword);

            // JSON deszerializálása objektummá
            return objectMapper.readValue(decryptedJson, Character.class);
        } catch (Exception e) {
            throw new IOException("Hiba a karakterfájl visszafejtése során. Ellenőrizd a jelszót!", e);
        }
    }
}