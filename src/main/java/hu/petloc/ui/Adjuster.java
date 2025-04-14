package hu.petloc.ui;

import hu.petloc.controller.BasePanelController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Absztrakt érték beállító.
 * Ez az osztály egy alap érték beállító komponens, amit a konkrét leszármazott osztályok
 * implementálhatnak különböző típusú értékekhez.
 */
public abstract class Adjuster extends HBox {

    protected IntegerProperty value = new SimpleIntegerProperty(0);
    protected int minValue;
    protected int maxValue;

    protected Button decreaseButton;
    protected Label valueLabel;
    protected Button increaseButton;

    /**
     * Konstruktor a beállítóhoz.
     *
     * @param minValue A minimális érték
     * @param maxValue A maximális érték
     * @param initialValue A kezdeti érték
     */
    public Adjuster(int minValue, int maxValue, int initialValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;

        // Kontroll értékének beállítása a megengedett határok között
        int validInitial = Math.max(minValue, Math.min(maxValue, initialValue));
        value.set(validInitial);

        // Alap UI elemek
        createUI();

        // Értékváltozás eseménykezelő
        value.addListener((obs, oldVal, newVal) -> {
            updateValueLabel();
            handleValueChange(oldVal.intValue(), newVal.intValue());
        });

        // Kezdeti állapot beállítása
        updateValueLabel();
        updateButtonStates();
    }

    /**
     * UI elemek létrehozása.
     */
    protected void createUI() {
        setSpacing(5);
        setAlignment(Pos.CENTER);

        // Csökkentő gomb - négyzetes forma
        decreaseButton = new Button("-");
        decreaseButton.setOnAction(e -> decreaseValue());
        // Négyzetes gomb beállítása - 25x25 méretű
        int buttonSize = 25;
        int labelWidth = 30;
        decreaseButton.setMinSize(buttonSize, buttonSize);
        decreaseButton.setPrefSize(buttonSize, buttonSize);
        decreaseButton.setMaxSize(buttonSize, buttonSize);
        decreaseButton.setStyle("-fx-font-weight: bold;");

        // Érték címke - BasePanelController.LABEL_WIDTH méretben
        valueLabel = new Label();
        valueLabel.setAlignment(Pos.CENTER);
        valueLabel.setMinWidth(labelWidth);
        valueLabel.setPrefWidth(labelWidth);
        valueLabel.setMaxWidth(labelWidth);
        HBox.setHgrow(valueLabel, Priority.NEVER); // Ne növekedjen

        // Növelő gomb - négyzetes forma
        increaseButton = new Button("+");
        increaseButton.setOnAction(e -> increaseValue());
        // Négyzetes gomb beállítása - 25x25 méretű
        increaseButton.setMinSize(buttonSize, buttonSize);
        increaseButton.setPrefSize(buttonSize, buttonSize);
        increaseButton.setMaxSize(buttonSize, buttonSize);
        increaseButton.setStyle("-fx-font-weight: bold;");

        // Elemek hozzáadása
        getChildren().addAll(decreaseButton, valueLabel, increaseButton);
    }

    /**
     * Érték csökkentése.
     */
    public void decreaseValue() {
        int currentValue = value.get();
        if (currentValue > minValue) {
            value.set(currentValue - 1);
            updateButtonStates();
        }
    }

    /**
     * Érték növelése.
     */
    public void increaseValue() {
        int currentValue = value.get();
        if (currentValue < maxValue) {
            value.set(currentValue + 1);
            updateButtonStates();
        }
    }

    /**
     * Értékcímke frissítése.
     */
    protected abstract void updateValueLabel();

    /**
     * Eseménykezelő az érték változásakor.
     *
     * @param oldValue A régi érték
     * @param newValue Az új érték
     */
    protected abstract void handleValueChange(int oldValue, int newValue);

    /**
     * Gombok állapotának frissítése az aktuális érték alapján.
     */
    protected void updateButtonStates() {
        int currentValue = value.get();
        decreaseButton.setDisable(currentValue <= minValue);
        increaseButton.setDisable(currentValue >= maxValue);
    }

    /**
     * Az aktuális érték lekérése.
     *
     * @return Az aktuális érték
     */
    public int getValue() {
        return value.get();
    }

    /**
     * Az érték beállítása.
     *
     * @param newValue Az új érték
     */
    public void setValue(int newValue) {
        if (newValue >= minValue && newValue <= maxValue) {
            value.set(newValue);
            updateButtonStates();
        }
    }

    /**
     * Az érték tulajdonság lekérése.
     *
     * @return Az érték tulajdonság
     */
    public IntegerProperty valueProperty() {
        return value;
    }
}