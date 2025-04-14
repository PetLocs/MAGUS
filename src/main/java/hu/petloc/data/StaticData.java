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
import java.util.Optional;
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
     * Visszaadja az adott fajhoz elérhető kasztok listáját.
     *
     * @param raceName A faj neve, amihez a kasztokat keressük
     * @return A fajhoz elérhető kasztok listája, vagy az összes kaszt, ha a fajhoz nincs specifikus korlátozás
     */
    public List<String> getClassesForRace(String raceName) {
        if (raceName == null || raceName.isEmpty()) {
            return Collections.emptyList();
        }

        // Hibakeresési célból kiírjuk a keresett faj nevét és a CLASS_RACE adatokat
        System.out.println("Keresett faj: " + raceName);

        for (StaticDataItem item : getItems(DataType.CLASS_RACE)) {
            System.out.println("CLASS_RACE item: " + item.getName() + " (id: " + item.getId() + ")");
        }

        // A CLASS_RACE adatokból kikeressük a megfelelő fajhoz tartozó elemet
        // Az összehasonlításhoz a faj nevét használjuk
        Optional<StaticDataItem> raceClassItem = getItems(DataType.CLASS_RACE).stream()
                .filter(item -> raceName.equalsIgnoreCase(item.getName()))
                .findFirst();

        // Ha találtunk megfelelő elemet, akkor annak az availableClasses mezőjét használjuk
        if (raceClassItem.isPresent()) {
            System.out.println("Találat: " + raceClassItem.get().getName());

            // A JSON-ból származó adatokban az availableClasses mező tartalmazza a fajhoz elérhető kasztokat
            List<String> availableClasses = raceClassItem.get().getAvailableClasses();
            if (availableClasses != null && !availableClasses.isEmpty()) {
                System.out.println("Fajspecifikus kasztok: " + availableClasses);
                return availableClasses;
            }

            // Ha nincs availableClasses, próbáljuk az attributes Map-ből (a kompatibilitás miatt)
            Map<String, Object> attributes = raceClassItem.get().getAttributes();
            if (attributes != null) {
                System.out.println("Attribútumok: " + attributes.keySet());

                if (attributes.containsKey("availableClasses")) {
                    Object availableClassesObj = attributes.get("availableClasses");
                    System.out.println("availableClasses típusa: " + availableClassesObj.getClass().getName());

                    if (availableClassesObj instanceof List) {
                        // Castoljuk a listát String listává
                        @SuppressWarnings("unchecked")
                        List<String> classes = (List<String>) availableClassesObj;
                        System.out.println("Fajspecifikus kasztok (attributes-ból): " + classes);
                        return classes;
                    }
                }
            }
        } else {
            // Ha a faj neve alapján nem találtuk meg, próbáljuk meg az id mező alapján (pl. "elf_kasztok"-hoz tartozik az "Elf" faj)
            String raceNameLower = raceName.toLowerCase().replace(' ', '_');
            String raceClassId = raceNameLower + "_kasztok";

            System.out.println("Keresés id alapján: " + raceClassId);

            raceClassItem = getItems(DataType.CLASS_RACE).stream()
                    .filter(item -> raceClassId.equals(item.getId()))
                    .findFirst();

            if (raceClassItem.isPresent()) {
                System.out.println("Találat id alapján: " + raceClassItem.get().getName());

                // Először próbáljuk közvetlenül az availableClasses mezőből
                List<String> availableClasses = raceClassItem.get().getAvailableClasses();
                if (availableClasses != null && !availableClasses.isEmpty()) {
                    System.out.println("Fajspecifikus kasztok (id alapján): " + availableClasses);
                    return availableClasses;
                }

                // Ha nincs availableClasses, próbáljuk az attributes Map-ből (a kompatibilitás miatt)
                Map<String, Object> attributes = raceClassItem.get().getAttributes();
                if (attributes != null && attributes.containsKey("availableClasses")) {
                    Object availableClassesObj = attributes.get("availableClasses");
                    if (availableClassesObj instanceof List) {
                        // Castoljuk a listát String listává
                        @SuppressWarnings("unchecked")
                        List<String> classes = (List<String>) availableClassesObj;
                        System.out.println("Fajspecifikus kasztok (id alapján, attributes-ból): " + classes);
                        return classes;
                    }
                }
            }
        }

        // Ha nem találtunk faj-specifikus korlátozást, akkor az összes kasztot visszaadjuk
        System.out.println("Nem találtunk specifikus kasztokat, összes kaszt visszaadása");
        return getCharacterClasses();
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
        String typeKey = type.getKey();
        String fileName = "data/" + typeKey;
        // Ellenőrizzük, hogy a fájlnév már tartalmazza-e a .json kiterjesztést
        if (!fileName.endsWith(".json")) {
            fileName = fileName + ".json";
        }
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