package hu.petloc.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Statikus adatokat kezelő osztály.
 * Az osztály lusta betöltést alkalmaz, és csak akkor tölti be az adatokat,
 * amikor először szükség van rájuk.
 */
public class StaticData {

    // Adatgyűjtemény típusonként
    private static Map<DataType, List<StaticDataItem>> dataCache;

    // Singleton instance
    private static StaticData instance;

    /**
     * Privát konstruktor a singleton mintához.
     */
    private StaticData() {
        dataCache = new EnumMap<>(DataType.class);
    }

    /**
     * Singleton instance lekérése.
     *
     * @return A StaticData singleton példánya
     */
    public static StaticData getInstance() {
        if (instance == null) {
            instance = new StaticData();
        }
        return instance;
    }

    /**
     * Visszaadja a fajok listáját.
     *
     * @return A fajok listája
     */
    public List<String> getRaces() {
        return getItems(DataType.RACE).stream()
                .map(StaticDataItem::getName)
                .collect(Collectors.toList());
    }

    /**
     * Visszaadja a kasztok listáját,
     * csoportosítva a GroupedComboBox számára megfelelő formátumban.
     *
     * @return A kasztok listája
     */
    public List<String> getCharacterClasses() {
        return getItems(DataType.CLASS).stream()
                .map(StaticDataItem::getName)
                .collect(Collectors.toList());
    }

    /**
     * Visszaadja egy adott kaszthoz tartozó alkasztok listáját.
     *
     * @param className A kaszt neve
     * @return Az alkasztok listája
     */
    public List<String> getSubclasses(String className) {
        if (className == null || className.isEmpty()) {
            return Collections.emptyList();
        }

        // Megkeressük a kaszt ID-t a név alapján
        String parentId = null;
        for (StaticDataItem classItem : getItems(DataType.CLASS)) {
            if (classItem.getName().equals(className)) {
                parentId = classItem.getId();
                break;
            }
        }

        if (parentId == null) {
            return Collections.emptyList();
        }

        // Lekérjük az ehhez a kaszthoz tartozó alkasztokat
        final String finalParentId = parentId;
        return getItems(DataType.SUBCLASS).stream()
                .filter(item -> finalParentId.equals(item.getParentId()))
                .map(StaticDataItem::getName)
                .collect(Collectors.toList());
    }


    /**
     * Visszaadja a jellemek listáját.
     *
     * @return A jellemek listája
     */
    public List<String> getAlignments() {
        return getItems(DataType.ALIGNMENT).stream()
                .map(StaticDataItem::getName)
                .collect(Collectors.toList());
    }

    /**
     * Visszaadja a vallások listáját.
     *
     * @return A vallások listája
     */
    public List<String> getReligions() {
        return getItems(DataType.RELIGION).stream()
                .map(StaticDataItem::getName)
                .collect(Collectors.toList());
    }

    /**
     * Adott típusú elemek lekérése a cache-ből.
     * Ha még nincsenek betöltve, akkor betölti őket.
     *
     * @param type Az adattípus
     * @return Az adott típusú elemek listája
     */
    private List<StaticDataItem> getItems(DataType type) {
        if (!dataCache.containsKey(type)) {
            loadData(type);
        }
        return dataCache.getOrDefault(type, Collections.emptyList());
    }

    /**
     * Adott típusú adatok betöltése a cache-be.
     *
     * @param type Az adattípus
     */
    private void loadData(DataType type) {
        String fileName = "data/" + type.getKey() + ".json";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            System.err.println("Adatfájl nem található: " + fileName);
            dataCache.put(type, Collections.emptyList());
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<StaticDataItem> items = mapper.readValue(inputStream, new TypeReference<List<StaticDataItem>>() {});
            dataCache.put(type, items);
        } catch (IOException e) {
            System.err.println("Hiba az adatok betöltésekor: " + e.getMessage());
            dataCache.put(type, Collections.emptyList());
        }
    }
}