package hu.petloc.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hu.petloc.model.GameCharacter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Titkosított JSON karaktertároló osztály.
 * Ez az osztály felelős a karakterek titkosított mentéséért és visszafejtéséért.
 */
public class EncryptedJsonCharacterStorage implements CharacterStorage {

    private static final String DEFAULT_PASSWORD = "magus";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_LENGTH = 128;
    private static final int ITERATION_COUNT = 65536;

    private String password;
    private final Gson gson;

    /**
     * Konstruktor a titkosított JSON tárolóhoz.
     */
    public EncryptedJsonCharacterStorage() {
        this.password = DEFAULT_PASSWORD;

        // Gson inicializálása a szép formázással
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Titkosítási jelszó beállítása.
     *
     * @param password Az új jelszó
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Karakter betöltése titkosított fájlból.
     *
     * @param filePath A fájl útvonala
     * @return A betöltött karakter
     * @throws Exception Ha hiba történik a betöltés során
     */
    @Override
    public GameCharacter loadCharacter(String filePath) throws Exception {
        try {
            // Fájl beolvasása
            byte[] encryptedData = readFile(filePath);

            // Adatok visszafejtése
            String json = decrypt(encryptedData, password);

            // JSON deszerializálása Karakter objektummá
            return gson.fromJson(json, GameCharacter.class);

        } catch (IOException e) {
            throw new Exception("Hiba a fájl olvasása közben: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Hiba a karakter visszafejtése közben: " + e.getMessage());
        }
    }

    /**
     * Karakter mentése titkosított fájlba.
     *
     * @param character A mentendő karakter
     * @param filePath A fájl útvonala
     * @throws Exception Ha hiba történik a mentés során
     */
    @Override
    public void saveCharacter(GameCharacter character, String filePath) throws Exception {
        try {
            // Karakter szerializálása JSON-ba
            String json = gson.toJson(character);

            // Adatok titkosítása
            byte[] encryptedData = encrypt(json, password);

            // Titkosított adatok fájlba írása
            writeFile(encryptedData, filePath);

        } catch (IOException e) {
            throw new Exception("Hiba a fájl írása közben: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Hiba a karakter titkosítása közben: " + e.getMessage());
        }
    }

    /**
     * Adatok titkosítása.
     *
     * @param data A titkosítandó adat
     * @param password A titkosítási jelszó
     * @return A titkosított adat
     * @throws Exception Ha hiba történik a titkosítás során
     */
    private byte[] encrypt(String data, String password) throws Exception {
        try {
            // Só generálása
            byte[] salt = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            // Titkosító kulcs létrehozása a jelszóból és a sóból
            SecretKey secretKey = generateKey(password, salt);

            // Titkosító inicializálása
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(javax.crypto.spec.IvParameterSpec.class).getIV();

            // Adatok titkosítása
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // Só, IV és titkosított adatok összeállítása egy tömbben
            byte[] result = new byte[salt.length + iv.length + encryptedData.length];
            System.arraycopy(salt, 0, result, 0, salt.length);
            System.arraycopy(iv, 0, result, salt.length, iv.length);
            System.arraycopy(encryptedData, 0, result, salt.length + iv.length, encryptedData.length);

            return result;

        } catch (Exception e) {
            throw new Exception("Titkosítási hiba: " + e.getMessage());
        }
    }

    /**
     * Titkosított adatok visszafejtése.
     *
     * @param encryptedData A titkosított adat
     * @param password A titkosítási jelszó
     * @return A visszafejtett adat
     * @throws Exception Ha hiba történik a visszafejtés során
     */
    private String decrypt(byte[] encryptedData, String password) throws Exception {
        try {
            // Só kinyerése
            byte[] salt = new byte[16];
            System.arraycopy(encryptedData, 0, salt, 0, salt.length);

            // IV kinyerése
            byte[] iv = new byte[16];
            System.arraycopy(encryptedData, salt.length, iv, 0, iv.length);

            // Titkosított adatok kinyerése
            byte[] encrypted = new byte[encryptedData.length - salt.length - iv.length];
            System.arraycopy(encryptedData, salt.length + iv.length, encrypted, 0, encrypted.length);

            // Titkosító kulcs létrehozása a jelszóból és a sóból
            SecretKey secretKey = generateKey(password, salt);

            // Visszafejtő inicializálása
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new javax.crypto.spec.IvParameterSpec(iv));

            // Adatok visszafejtése
            byte[] decryptedData = cipher.doFinal(encrypted);

            return new String(decryptedData, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new Exception("Visszafejtési hiba: " + e.getMessage());
        }
    }

    /**
     * Titkosító kulcs generálása jelszóból és sóból.
     *
     * @param password A jelszó
     * @param salt A só
     * @return A generált kulcs
     * @throws NoSuchAlgorithmException Ha a titkosító algoritmus nem elérhető
     * @throws InvalidKeySpecException Ha a kulcs specifikáció érvénytelen
     */
    private SecretKey generateKey(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // PBKDF2 kulcsszármaztatás
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
    }

    /**
     * Fájl beolvasása.
     *
     * @param filePath A fájl útvonala
     * @return A beolvasott adat
     * @throws IOException Ha hiba történik a beolvasás során
     */
    private byte[] readFile(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] data = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(data);
        }

        return data;
    }

    /**
     * Adat kiírása fájlba.
     *
     * @param data A kiírandó adat
     * @param filePath A fájl útvonala
     * @throws IOException Ha hiba történik a kiírás során
     */
    private void writeFile(byte[] data, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
            fos.flush();
        }
    }
}