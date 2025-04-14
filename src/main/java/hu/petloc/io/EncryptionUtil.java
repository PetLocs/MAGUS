package hu.petloc.io;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Titkosítási segédosztály AES algoritmussal.
 */
public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256; // 256 bites kulcs
    private static final byte[] DEFAULT_IV = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // Alapértelmezett IV

    /**
     * Jelszóból generál AES kulcsot.
     *
     * @param password A jelszó
     * @return SecretKey objektum
     * @throws Exception Ha hiba történik a kulcs létrehozása során
     */
    public static SecretKey generateKeyFromPassword(String password) throws Exception {
        byte[] keyBytes = password.getBytes(StandardCharsets.UTF_8);
        // Biztosítjuk, hogy a kulcs megfelelő hosszúságú legyen (32 byte a 256 bites kulcshoz)
        byte[] keyBytes32 = new byte[32];
        System.arraycopy(keyBytes, 0, keyBytes32, 0, Math.min(keyBytes.length, 32));
        return new SecretKeySpec(keyBytes32, 0, 32, ALGORITHM);
    }

    /**
     * Generál egy véletlenszerű AES kulcsot.
     *
     * @return Az új kulcs
     * @throws NoSuchAlgorithmException Ha az AES algoritmus nem elérhető
     */
    public static SecretKey generateRandomKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        return keyGenerator.generateKey();
    }

    /**
     * AES kulcsot Base64 formátumú szövegként ad vissza.
     *
     * @param key Az AES kulcs
     * @return A Base64 kódolt kulcs
     */
    public static String keyToString(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * Base64 szövegből visszaállít egy AES kulcsot.
     *
     * @param keyString A Base64 kódolt kulcs
     * @return Az AES kulcs
     */
    public static SecretKey stringToKey(String keyString) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(decodedKey, ALGORITHM);
    }

    /**
     * Titkosít egy szöveget a megadott kulccsal.
     *
     * @param data A titkosítandó szöveg
     * @param key A titkosítási kulcs
     * @return A titkosított szöveg Base64 kódolva
     * @throws Exception Ha hiba történik a titkosítás során
     */
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // A fix IV használata helyett véletlenszerű IV-t is lehet használni,
        // azt is mellékelve az eredményhez
        IvParameterSpec ivSpec = new IvParameterSpec(DEFAULT_IV);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Visszafejt egy titkosított szöveget a megadott kulccsal.
     *
     * @param encryptedData A titkosított szöveg Base64 kódolva
     * @param key A visszafejtési kulcs
     * @return Az eredeti szöveg
     * @throws Exception Ha hiba történik a visszafejtés során
     */
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(DEFAULT_IV);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedData);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Generál egy véletlenszerű initialization vector-t (IV).
     *
     * @return A 16 byte-os IV
     */
    public static byte[] generateRandomIV() {
        byte[] iv = new byte[16]; // 16 byte a CBC módhoz
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    /**
     * Biztonságos titkosítás véletlenszerű IV használatával.
     * Az IV beágyazásra kerül a titkosított adatba.
     *
     * @param data A titkosítandó szöveg
     * @param key A titkosítási kulcs
     * @return A titkosított adat Base64 kódolva, beleértve az IV-t
     * @throws Exception Ha hiba történik a titkosítás során
     */
    public static String encryptWithRandomIV(String data, SecretKey key) throws Exception {
        byte[] iv = generateRandomIV();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Kombináljuk az IV-t és a titkosított adatot
        byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * Visszafejti a véletlenszerű IV-vel titkosított adatokat.
     *
     * @param encryptedData A titkosított adat Base64 kódolva, beleértve az IV-t
     * @param key A visszafejtési kulcs
     * @return Az eredeti szöveg
     * @throws Exception Ha hiba történik a visszafejtés során
     */
    public static String decryptWithRandomIV(String encryptedData, SecretKey key) throws Exception {
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);

        // Külön választjuk az IV-t és a titkosított adatot
        byte[] iv = new byte[16];
        byte[] encrypted = new byte[decodedData.length - 16];
        System.arraycopy(decodedData, 0, iv, 0, 16);
        System.arraycopy(decodedData, 16, encrypted, 0, encrypted.length);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(encrypted);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}