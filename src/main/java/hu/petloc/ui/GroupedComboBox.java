package hu.petloc.ui;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;
import java.util.function.Function;

/**
 * Csoportosított megjelenítésű ComboBox, amely támogatja a különböző kategóriák
 * és elemek vizuális elkülönítését.
 *
 * @param <T> A ComboBoxban tárolt elemek típusa
 */
public class GroupedComboBox<T> extends ComboBox<T> {

    private double maxPixelWidth = 0;
    private final Font font = new Text().getFont();
    private final Function<T, String> stringConverter;
    private final Function<T, Boolean> isGroupFunction;

    /**
     * Konstruktor a GroupedComboBox létrehozásához String típusú elemekkel.
     *
     * @param items A ComboBoxban megjelenítendő elemek listája
     */
    public static GroupedComboBox<String> createStringGroupedComboBox(List<String> items) {
        return new GroupedComboBox<>(
                items,
                item -> item,
                item -> item != null && item.startsWith("--")
        );
    }

    /**
     * Konstruktor a GroupedComboBox létrehozásához.
     *
     * @param items A ComboBoxban megjelenítendő elemek listája
     * @param stringConverter Konverter funkció, amely az elemek szöveges megjelenítését biztosítja
     * @param isGroupFunction Funkció, amely meghatározza, hogy egy elem csoport fejléc-e
     */
    public GroupedComboBox(List<T> items, Function<T, String> stringConverter, Function<T, Boolean> isGroupFunction) {
        super();
        this.stringConverter = stringConverter;
        this.isGroupFunction = isGroupFunction;

        this.setCellFactory(this::createCell);
        this.setButtonCell(createCell(null));
        if (items != null) {
            this.getItems().addAll(items);
        }

        Text text = new Text();
        text.setFont(font);

        if (items != null) {
            for (T item : items) {
                if (item != null && !isGroupFunction.apply(item)) {
                    String itemText = stringConverter.apply(item);
                    text.setText(itemText);
                    maxPixelWidth = Math.max(maxPixelWidth, text.getLayoutBounds().getWidth());
                }
            }
        }
    }

    /**
     * Cellagyár létrehozása, amely megfelelően formázza a csoport fejléceket és elemeket.
     *
     * @param listView A ListView, amelyhez a cellát létrehozzuk
     * @return A formázott ListCell
     */
    private ListCell<T> createCell(ListView<T> listView) {
        return new ListCell<>() {
            final Text textMeasurer = new Text();

            {
                textMeasurer.setFont(font);
            }

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setDisable(false);
                } else {
                    String itemText = stringConverter.apply(item);

                    if (isGroupFunction.apply(item)) {
                        // Csoport fejléc formázása
                        String stripped = itemText.replaceAll("-", "").trim();
                        textMeasurer.setText(stripped);
                        double textWidth = textMeasurer.getLayoutBounds().getWidth();
                        double paddingWidth = maxPixelWidth - textWidth;
                        int dashCount = Math.max(0, (int) (paddingWidth / 5));
                        int left = dashCount / 2;
                        int right = dashCount - left;

                        String centered = "-".repeat(left) + stripped + "-".repeat(right);
                        setText(centered);
                        setStyle("-fx-font-style: italic; -fx-text-fill: gray;");
                        setDisable(true);
                    } else {
                        // Normál elem formázása
                        setText(itemText);
                        setStyle("");
                        setDisable(false);
                    }
                }
            }
        };
    }
}
