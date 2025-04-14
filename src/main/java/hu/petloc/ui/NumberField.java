package hu.petloc.ui;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

public class NumberField extends TextField {

    public NumberField() {
        this.setPrefColumnCount(4);
        this.setMaxWidth(Region.USE_PREF_SIZE);
        this.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput);
    }

    private void validateNumericInput(KeyEvent event) {
        String character = event.getCharacter();
        if (!character.matches("[0-9]")) {
            event.consume();
        }
    }

    public int getIntValue() {
        try {
            return Integer.parseInt(getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setIntValue(int value) {
        setText(String.valueOf(value));
    }
}