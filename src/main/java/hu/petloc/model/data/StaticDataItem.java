package hu.petloc.model.data;

/**
 * Statikus adatelem osztály.
 * Egy játékhoz kapcsolódó adatelemet reprezentál, pl. egy faj, kaszt stb.
 */
public class StaticDataItem {
    private String id;
    private String name;
    private String group;
    private String parentId;
    private String description;

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
     * Teljes konstruktor minden adattal.
     *
     * @param id Az elem azonosítója
     * @param name Az elem megjelenítési neve
     * @param group Az elem csoportja (opcionális)
     * @param parentId A szülő elem azonosítója (opcionális)
     * @param description Az elem leírása (opcionális)
     */
    public StaticDataItem(String id, String name, String group, String parentId, String description) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.parentId = parentId;
        this.description = description;
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
     * Visszaadja az elem csoportját.
     *
     * @return Az elem csoportja
     */
    public String getGroup() {
        return group;
    }

    /**
     * Beállítja az elem csoportját.
     *
     * @param group Az új csoport
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Visszaadja a szülő elem azonosítóját.
     *
     * @return A szülő elem azonosítója
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Beállítja a szülő elem azonosítóját.
     *
     * @param parentId Az új szülő azonosító
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Visszaadja az elem leírását.
     *
     * @return Az elem leírása
     */
    public String getDescription() {
        return description;
    }

    /**
     * Beállítja az elem leírását.
     *
     * @param description Az új leírás
     */
    public void setDescription(String description) {
        this.description = description;
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