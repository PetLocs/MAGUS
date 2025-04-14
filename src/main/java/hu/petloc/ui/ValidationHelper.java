//package hu.petloc.ui;
//
//import hu.petloc.panel.LoggerPanel;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Control;
//import javafx.scene.control.TextField;
//
///**
// * Segédosztály a beviteli mezők validálásához.
// */
//public class ValidationHelper {
//
//    private LoggerPanel loggerPanel;
//
//    /**
//     * Konstruktor.
//     *
//     * @param loggerPanel A LoggerPanel, amelyen a hibaüzeneteket megjelenítjük
//     */
//    public ValidationHelper(LoggerPanel loggerPanel) {
//        this.loggerPanel = loggerPanel;
//    }
//
//    /**
//     * Validálja, hogy a beviteli mező nem üres.
//     *
//     * @param control A validálandó mező
//     * @param errorMessage Hibaüzenet szövege
//     * @return true, ha valid, false ha hibás
//     */
//    public boolean validateNotEmpty(Control control, String errorMessage) {
//        boolean valid = false;
//
//        if (control instanceof TextField) {
//            TextField textField = (TextField) control;
//            valid = textField.getText() != null && !textField.getText().trim().isEmpty();
//        } else if (control instanceof ComboBox) {
//            ComboBox<?> comboBox = (ComboBox<?>) control;
//            valid = comboBox.getValue() != null;
//        }
//
//        if (!valid) {
//            loggerPanel.logError(errorMessage);
//
//            // Piros keret alkalmazása a hibás mezőre
//            control.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 3px;");
//        } else {
//            // Eredeti stílus visszaállítása
//            control.setStyle("");
//        }
//
//        return valid;
//    }
//
//    /**
//     * Validálja, hogy a ComboBox-ban kiválasztott érték nem kezdődik "--" karakterekkel.
//     *
//     * @param comboBox A validálandó ComboBox
//     * @param errorMessage Hibaüzenet szövege
//     * @return true, ha valid, false ha hibás
//     */
//    public boolean validateNotHeader(ComboBox<String> comboBox, String errorMessage) {
//        String value = comboBox.getValue();
//        boolean valid = value != null && !value.startsWith("--");
//
//        if (!valid && value != null) {
//            loggerPanel.logError(errorMessage);
//
//            // Piros keret alkalmazása a hibás mezőre
//            comboBox.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 3px;");
//        } else {
//            // Eredeti stílus visszaállítása
//            comboBox.setStyle("");
//        }
//
//        return valid;
//    }
//
//    /**
//     * Sikeres művelet jelzése.
//     *
//     * @param message A sikeres művelet üzenete
//     */
//    public void showSuccess(String message) {
//        loggerPanel.logSuccess(message);
//    }
//
//    /**
//     * Információs üzenet megjelenítése.
//     *
//     * @param message Az információs üzenet
//     */
//    public void showInfo(String message) {
//        loggerPanel.logInfo(message);
//    }
//
//    /**
//     * Figyelmeztető üzenet megjelenítése.
//     *
//     * @param message A figyelmeztető üzenet
//     */
//    public void showWarning(String message) {
//        loggerPanel.logWarning(message);
//    }
//
//    /**
//     * Hibaüzenet megjelenítése.
//     *
//     * @param message A hibaüzenet
//     */
//    public void showError(String message) {
//        loggerPanel.logError(message);
//    }
//
//    /**
//     * Üzenetek törlése.
//     */
//    public void clearMessages() {
//        loggerPanel.clear();
//    }
//}