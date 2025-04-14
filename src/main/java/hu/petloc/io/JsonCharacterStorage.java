package hu.petloc.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.petloc.model.character.Character;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * JSON alapú karakter tárolási implementáció.
 * Ez az osztály a karaktereket JSON formátumban tárolja.
 */
public class JsonCharacterStorage implements CharacterStorage {

    private final ObjectMapper objectMapper;

    /**
     * Konstruktor, amely inicializálja a Jackson ObjectMapper-t.
     */
    public JsonCharacterStorage() {
        this.objectMapper = new ObjectMapper();
        // Egyedi modul regisztrálása a JavaFX property-k kezeléséhez
        objectMapper.registerModule(new JavaFXPropertyModule());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveCharacter(Character character, String filepath) throws IOException {
        // Objektum szerializálása JSON-ba
        objectMapper.writeValue(new File(filepath), character);
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

        // JSON deszerializálása objektummá
        return objectMapper.readValue(new File(filepath), Character.class);
    }
}