package hu.petloc.view;

import hu.petloc.controller.CharacterCreationPanelController;
import hu.petloc.model.GameCharacter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Karakter létrehozó panel nézete.
 * Ez az osztály felelős a karakter létrehozási folyamat megjelenítéséért.
 */
public class CharacterCreationPanelView {

    private VBox root;
    private final CharacterCreationPanelController controller;

    /**
     * Konstruktor a karakter létrehozó panel nézetéhez.
     *
     * @param controller A karakter létrehozó panel vezérlője
     */
    public CharacterCreationPanelView(CharacterCreationPanelController controller) {
        this.controller = controller;
        createUI();
    }

    /**
     * Felhasználói felület létrehozása.
     */
    private void createUI() {
        // Konténer a teljes tartalomnak
        root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Főcím
        Label titleLabel = new Label("Új karakter létrehozása");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        // Instrukciók
        Label instructionsLabel = new Label("Kérjük, töltsd ki a bal oldali képességpontok elosztását és add meg az alapvető információkat a karakteredről.");
        instructionsLabel.setWrapText(true);
        instructionsLabel.setMaxWidth(400);

        // Létrehozás gomb
        Button createButton = new Button("Karakter létrehozása");
        createButton.setStyle("-fx-font-weight: bold;");
        createButton.setPrefWidth(200);
        createButton.setOnAction(e -> createCharacter());

        // Elemek hozzáadása a konténerhez
        root.getChildren().addAll(titleLabel, instructionsLabel, createButton);
    }

    /**
     * Karakter létrehozása.
     */
    private void createCharacter() {
        controller.createCharacter();
    }

    /**
     * A gyökérelem lekérése.
     *
     * @return A gyökérelem
     */
    public VBox getRoot() {
        return root;
    }
}