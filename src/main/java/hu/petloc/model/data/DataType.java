package hu.petloc.model.data;

/**
 * Az adattípusok enumerációja.
 * A játékhoz kapcsolódó különböző statikus adatok típusait tartalmazza.
 */
public enum DataType {
    RACE("race", "Fajok"),
    CLASS("class", "Kasztok"),
    SUBCLASS("subclass", "Alkasztok"),
    ORIGIN("origin", "Származások"),
    ALIGNMENT("alignment", "Jellemek"),
    RELIGION("religion", "Vallások"),
    ABILITY("ability", "Képességek"),
    SKILL("skill", "Képzettségek");

    private final String key;
    private final String displayName;

    /**
     * Konstruktor az adattípus enum értékeihez.
     *
     * @param key Az adattípus kulcsa (azonosítója)
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