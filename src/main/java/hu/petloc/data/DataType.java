package hu.petloc.data;

/**
 * Az adattípusok enumerációja.
 * A játékhoz kapcsolódó különböző statikus adatok típusait tartalmazza.
 */
public enum DataType {
    RACE("race", "Fajok"),
    CLASS("class", "Kasztok"),
    SUBCLASS("subclass", "Alkasztok"),
    ALIGNMENT("alignment", "Jellemek"),
    RELIGION("religion", "Vallások"),
    CLASS_RACE("class_race", "Faj-Kaszt kapcsolatok");

    private final String key;
    private final String displayName;

    /**
     * Konstruktor az adattípus enum értékeihez.
     *
     * @param key Az adattípus kulcsa (azonosítója, fájlnév)
     * @param displayName Az adattípus megjelenítési neve
     */
    DataType(String key, String displayName) {
        this.key = key;
        this.displayName = displayName;
    }

    /**
     * Visszaadja az adattípus kulcsát.
     *
     * @return Az adattípus kulcs
     */
    public String getKey() {
        return key;
    }

    /**
     * Visszaadja az adattípus megjelenítési nevét.
     *
     * @return Az adattípus megjelenítési neve
     */
    public String getDisplayName() {
        return displayName;
    }
}