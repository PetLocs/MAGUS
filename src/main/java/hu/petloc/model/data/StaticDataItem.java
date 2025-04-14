package hu.petloc.model.data;

/**
 * Statikus adatelem osztály.
 * Egy játékhoz kapcsolódó adatelemet reprezentál, pl. egy faj, kaszt stb.
 */
public class StaticDataItem {
    private String id;
    private String name;

    /**
     * Üres konstruktor.
     */
    public StaticDataItem() {
        // Üres konstruktor a Jackson deserializációhoz
    }

    /**
     * Egyszerű konstruktor id és név alapján.
     *
     * @param id Az elem azonosítója
     * @param name Az elem megjelenítési neve
     */
    public StaticDataItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Visszaadja az elem azonosítóját.
     *
     * @return Az elem azonosítója
     */
    public String getId() {
        return id;
    }

    /**
     * Beállítja az elem azonosítóját.
     *
     * @param id Az új azonosító
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Visszaadja az elem megjelenítési nevét.
     *
     * @return Az elem megjelenítési neve
     */
    public String getName() {
        return name;
    }

    /**
     * Beállítja az elem megjelenítési nevét.
     *
     * @param name Az új megjelenítési név
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * String reprezentáció.
     *
     * @return Az elem string reprezentációja
     */
    @Override
    public String toString() {
        return name;
    }
}