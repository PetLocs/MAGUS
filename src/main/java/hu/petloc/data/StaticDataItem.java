package hu.petloc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Statikus adatelemek, amelyek a játékrendszer alapvető elemeit reprezentálják,
 * mint például kasztok, fajok, képzettségek, stb.
 */
public class StaticDataItem {

    private String id;
    private String name;
    private String description;
    private DataType type;
    private String group;
    private String parentId;
    private Map<String, Object> attributes;
    private List<StaticDataItem> children;
    private List<String> availableClasses;  // A class_race.json fájlban használt "availableClasses" mezőhöz

    /**
     * Alapértelmezett konstruktor
     */
    public StaticDataItem() {
        this.attributes = new HashMap<>();
        this.children = new ArrayList<>();
    }

    /**
     * Teljes konstruktor
     *
     * @param id Egyedi azonosító
     * @param name Név
     * @param description Leírás
     * @param type Adattípus
     */
    public StaticDataItem(String id, String name, String description, DataType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.attributes = new HashMap<>();
        this.children = new ArrayList<>();
    }

    /**
     * Visszaadja, hogy az elemnek vannak-e gyermekei.
     *
     * @return Igaz, ha vannak gyermekei
     */
    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    /**
     * Visszaadja, hogy van-e az elemnek adott nevű attribútuma.
     *
     * @param key Az attribútum neve
     * @return Igaz, ha létezik a megadott attribútum
     */
    public boolean hasAttribute(String key) {
        return attributes != null && attributes.containsKey(key);
    }

    /**
     * Attribútum lekérése adott kulcs alapján.
     *
     * @param key Az attribútum neve
     * @param <T> Az attribútum típusa
     * @return Az attribútum értéke, vagy null ha nem létezik
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        if (hasAttribute(key)) {
            return (T) attributes.get(key);
        }
        return null;
    }

    /**
     * Lokalizált név lekérése erőforráscsomag használatával.
     * Ha nincs lokalizált név, a normál név kerül visszaadásra.
     *
     * @param resourceBundle Az erőforráscsomag
     * @return A lokalizált név
     */
    public String getLocalizedName(ResourceBundle resourceBundle) {
        if (resourceBundle == null || id == null || type == null) {
            return name;
        }

        String localizedKey = "item." + type.name().toLowerCase() + "." + id + ".name";
        try {
            return resourceBundle.getString(localizedKey);
        } catch (MissingResourceException e) {
            return name;
        }
    }

    /**
     * Lokalizált leírás lekérése erőforráscsomag használatával.
     * Ha nincs lokalizált leírás, a normál leírás kerül visszaadásra.
     *
     * @param resourceBundle Az erőforráscsomag
     * @return A lokalizált leírás
     */
    public String getLocalizedDescription(ResourceBundle resourceBundle) {
        if (resourceBundle == null || id == null || type == null) {
            return description;
        }

        String localizedKey = "item." + type.name().toLowerCase() + "." + id + ".description";
        try {
            return resourceBundle.getString(localizedKey);
        } catch (MissingResourceException e) {
            return description;
        }
    }

    // Getter és setter metódusok

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<StaticDataItem> getChildren() {
        return children;
    }

    public void setChildren(List<StaticDataItem> children) {
        this.children = children;
    }

    public List<String> getAvailableClasses() {
        return availableClasses;
    }

    public void setAvailableClasses(List<String> availableClasses) {
        this.availableClasses = availableClasses;
    }

    /**
     * Attribútum beállítása
     *
     * @param key Az attribútum neve
     * @param value Az attribútum értéke
     */
    public void setAttribute(String key, Object value) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(key, value);
    }

    /**
     * Gyermek elem hozzáadása
     *
     * @param child A gyermek elem
     */
    public void addChild(StaticDataItem child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    @Override
    public String toString() {
        return name;
    }
}