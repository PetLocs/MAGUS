package hu.petloc.ui;

/**
 * Szám állító komponens.
 * Ez az osztály egy speciális Adjuster, ami számértékek beállítására szolgál.
 */
public class NumberAdjuster extends Adjuster {

    private String labelFormat = "%d";

    /**
     * Konstruktor a szám állítóhoz.
     *
     * @param minValue A minimális érték
     * @param maxValue A maximális érték
     * @param initialValue A kezdeti érték
     */
    public NumberAdjuster(int minValue, int maxValue, int initialValue) {
        super(minValue, maxValue, initialValue);
    }

    /**
     * Értékcímke frissítése.
     */
    @Override
    protected void updateValueLabel() {
        valueLabel.setText(String.format(labelFormat, value.get()));
    }

    /**
     * Eseménykezelő az érték változásakor.
     *
     * @param oldValue A régi érték
     * @param newValue Az új érték
     */
    @Override
    protected void handleValueChange(int oldValue, int newValue) {
        // Alapértelmezetten nincs speciális kezelés
    }

    /**
     * A címke formátum beállítása.
     *
     * @param format A formátum, amit String.format-tal használunk
     */
    public void setLabelFormat(String format) {
        this.labelFormat = format;
        updateValueLabel();
    }
}