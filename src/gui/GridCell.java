package gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class GridCell extends StackPane {
    private SimpleObjectProperty<Paint> color;
    private SimpleObjectProperty<Character> letter;

    public GridCell(SimpleObjectProperty<Character> letter) {
        this.letter = letter;
        color = new SimpleObjectProperty<>(Color.web("#424242"));
        addViews();
    }

    private void addViews() {
        Rectangle rect = new Rectangle();
        rect.heightProperty().bind(heightProperty());
        rect.widthProperty().bind(widthProperty());
        rect.fillProperty().bind(color);
        rect.setStroke(Color.WHITE);
        getChildren().add(rect);

        Label l = new Label();
        l.setFont(new Font(24));
        l.setTextFill(Color.WHITE);
        l.textProperty().bind(letter.asString());
        getChildren().add(l);
    }

    public void setSelected(boolean selected) {
        if (selected) {
            color.setValue(Color.web("#00695C"));
        } else {
            color.setValue(Color.web("#424242"));
        }
    }
}
