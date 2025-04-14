package hu.petloc.ui;

import javafx.scene.control.Button;

public class CycleButton extends Button {

    private final int[] values;
    private int index;
    private final String prefix;
    private final String suffix;

    public CycleButton(int... values) {
        this("", "", values);
    }

    public CycleButton(String prefix, String suffix, int... values) {
        this.values = values;
        this.index = 0;
        this.prefix = prefix;
        this.suffix = suffix;

        this.setText(format(values[index]));
        this.setOnAction(e -> cycle());
    }

    private void cycle() {
        index = (index + 1) % values.length;
        this.setText(format(values[index]));
    }

    public int getValue() {
        return values[index];
    }

    public void setValue(int val) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == val) {
                index = i;
                this.setText(format(val));
                return;
            }
        }
    }

    private String format(int val) {
        return prefix + val + suffix;
    }
}