package hu.petloc.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Adjuster extends HBox {
    protected final int min;
    protected final int max;
    protected int value;

    protected final Label label;
    protected final Button minus;
    protected final Button plus;

    public Adjuster(int min, int max, int initialValue) {
        this.min = min;
        this.max = max;
        this.value = clamp(initialValue);

        label = new Label(String.valueOf(value));
        label.setMinWidth(40); // 5 digit fix
        label.setPrefWidth(40);
        label.setAlignment(Pos.CENTER);

        minus = new Button("-");
        plus = new Button("+");

        setSpacing(5);
        setAlignment(Pos.CENTER_LEFT);
        styleButton(minus);
        styleButton(plus);

        minus.setOnAction(e -> setValue(value - getStep()));
        plus.setOnAction(e -> setValue(value + getStep()));

        getChildren().addAll(minus, label, plus);
    }

    protected int clamp(int val) {
        return Math.max(min, Math.min(max, val));
    }

    protected int getStep() {
        return 1;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {
        value = clamp(newValue);
        label.setText(String.valueOf(value));
    }

    private void styleButton(Button button) {
        button.setPrefWidth(30);
        button.setPrefHeight(24);
        button.setStyle("-fx-font-weight: bold; -fx-alignment: center;");
    }
}
