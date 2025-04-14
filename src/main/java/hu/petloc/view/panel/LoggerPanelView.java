package hu.petloc.view.panel;

import hu.petloc.controller.panel.BasePanelController;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;

/**
 * A LoggerPanel nézete, amely megjeleníti a státuszüzeneteket.
 */
public class LoggerPanelView extends BasePanelView {

    private Label messageLabel;

    private HBox root;

    /**
     * Konstruktor.
     *
     * @param controller A nézethez tartozó kontroller
     */
    public LoggerPanelView(hu.petloc.controller.panel.LoggerPanelController controller) {
        super(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createUI() {
        // A gyökér panel létrehozása
        root = new HBox();
        root.setPadding(new Insets(BasePanelController.PANEL_PADDING));

        // Az üzenetet megjelenítő címke létrehozása
        messageLabel = new Label("Kész.");
        messageLabel.setTextFill(Color.BLACK);

        // A háttér beállítása
        HBox messageBox = new HBox(messageLabel);
        messageBox.setPadding(new Insets(5));
        messageBox.setBackground(new Background(
                new BackgroundFill(Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));

        // Hozzáadás a layout-hoz
        root.getChildren().add(messageBox);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Region getRoot() {
        return root;
    }

    /**
     * Üzenet megjelenítése.
     *
     * @param message A megjelenítendő üzenet
     */
    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    /**
     * Az üzenet címke lekérése.
     *
     * @return Az üzenet címke
     */
    public Label getMessageLabel() {
        return messageLabel;
    }
}