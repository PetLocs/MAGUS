package hu.petloc.ui;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class GroupedComboBox extends ComboBox<String> {

    private double maxPixelWidth = 0;
    private final Font font = new Text().getFont();

    public GroupedComboBox(List<String> items) {
        super();
        this.setCellFactory(this::createCell);
        this.setButtonCell(createCell(null));
        this.getItems().addAll(items);

        Text text = new Text();
        text.setFont(font);
        for (String item : items) {
            if (!item.startsWith("--")) {
                text.setText(item);
                maxPixelWidth = Math.max(maxPixelWidth, text.getLayoutBounds().getWidth());
            }
        }
    }

    private ListCell<String> createCell(ListView<String> listView) {
        return new ListCell<>() {
            final Text textMeasurer = new Text();

            {
                textMeasurer.setFont(font);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setDisable(false);
                } else if (item.startsWith("--")) {
                    String stripped = item.replaceAll("-", "").trim();
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
                    setText(item);
                    setStyle("");
                    setDisable(false);
                }
            }
        };
    }
}