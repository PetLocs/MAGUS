package hu.petloc.ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Szám értékek módosítására szolgáló felületi komponens
 * növelő és csökkentő gombokkal.
 */
public class NumberAdjuster extends HBox {

    // Konstansok
    private static final int DEFAULT_SPACING = 5;
    private static final int DEFAULT_MIN_VALUE = 0;
    private static final int DEFAULT_MAX_VALUE = 100;
    private static final int DEFAULT_STEP = 1;

    private final IntegerProperty value = new SimpleIntegerProperty(0);
    private final int minValue;
    private final int maxValue;
    private final int step;
    private final Label nameLabel;
    private final Label valueLabel;

    /**
     * Konstruktor név és kezdeti érték megadásával.
     *
     * @param name A megjelenítendő név
     * @param initialValue Kezdeti érték
     */
    public NumberAdjuster(String name, int initialValue) {
        this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, initialValue, DEFAULT_STEP, name);
    }

    /**
     * Konstruktor alap értékekkel.
     *
     * @param minValue Minimális érték
     * @param maxValue Maximális érték
     * @param initialValue Kezdeti érték
     */
    public NumberAdjuster(int minValue, int maxValue, int initialValue) {
        this(minValue, maxValue, initialValue, DEFAULT_STEP);
    }

    /**
     * Konstruktor lépésközzel.
     *
     * @param minValue Minimális érték
     * @param maxValue Maximális érték
     * @param initialValue Kezdeti érték
     * @param step Lépésköz értéke
     */
    public NumberAdjuster(int minValue, int maxValue, int initialValue, int step) {
        this(minValue, maxValue, initialValue, step, null);
    }

    /**
     * Konstruktor lépésközzel és névvel.
     *
     * @param minValue Minimális érték
     * @param maxValue Maximális érték
     * @param initialValue Kezdeti érték
     * @param step Lépésköz értéke
     * @param name A megjelenítendő név (opcionális)
     */
    private NumberAdjuster(int minValue, int maxValue, int initialValue, int step, String name) {
        super(DEFAULT_SPACING);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.step = step;

        // Komponensek létrehozása
        Button minusButton = new Button("-");
        minusButton.setFont(new Font(12));
        minusButton.setMinWidth(25);

        // Név megjelenítése, ha van
        if (name != null && !name.isEmpty()) {
            nameLabel = new Label(name + ":");
            nameLabel.setPrefWidth(85);  // Fix szélesség a névnek
        } else {
            nameLabel = null;
        }

        valueLabel = new Label(String.valueOf(initialValue));
        valueLabel.setPrefWidth(35);
        valueLabel.setAlignment(Pos.CENTER);

        Button plusButton = new Button("+");
        plusButton.setFont(new Font(12));
        plusButton.setMinWidth(25);

        // Kezdeti érték beállítása
        setValue(initialValue);

        // Események kezelése
        minusButton.setOnAction(e -> decrease());
        plusButton.setOnAction(e -> increase());

        // Komponensek hozzáadása
        if (nameLabel != null) {
            getChildren().addAll(nameLabel, minusButton, valueLabel, plusButton);
        } else {
            getChildren().addAll(minusButton, valueLabel, plusButton);
        }

        setAlignment(Pos.CENTER_LEFT);
    }

    /**
     * Érték lekérése.
     *
     * @return A beállított érték
     */
    public int getValue() {
        return value.get();
    }

    /**
     * Érték beállítása.
     *
     * @param newValue Az új érték
     */
    public void setValue(int newValue) {
        // Érték ellenőrzése a korlátok között
        int validValue = Math.min(maxValue, Math.max(minValue, newValue));
        value.set(validValue);
        valueLabel.setText(String.valueOf(validValue));
    }

    /**
     * Érték növelése a lépésközzel.
     */
    public void increase() {
        setValue(getValue() + step);
    }

    /**
     * Érték csökkentése a lépésközzel.
     */
    public void decrease() {
        setValue(getValue() - step);
    }

    /**
     * Az érték property objektumának lekérése.
     * Property-k használata lehetővé teszi a reaktív programozást,
     * feliratkozást az érték változására.
     *
     * @return Az érték property objektuma
     */
    public IntegerProperty valueProperty() {
        return value;
    }
}