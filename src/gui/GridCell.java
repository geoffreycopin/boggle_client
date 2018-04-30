package gui;

import errors.ParsingError;
import game.Grid;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class GridCell extends StackPane {
    private SimpleObjectProperty<Paint> color;
    private SimpleObjectProperty<Grid> grid;
    private Label l;
    private char line;
    private int column;

    public GridCell(char line, int column) {
        this.line = line;
        this.column = column;
        try {
            grid = new SimpleObjectProperty<>(Grid.fromString("AZERTYUIOPQSDFGH"));
        } catch (ParsingError parsingError) {
            parsingError.printStackTrace();
        }
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

        l = new Label();
        l.setFont(new Font(24));
        l.setTextFill(Color.WHITE);
        l.setText(getLetter().toString());
        getChildren().add(l);
    }

    public void setSelected(boolean selected) {
        if (selected) {
            color.setValue(Color.web("#00695C"));
        } else {
            color.setValue(Color.web("#424242"));
        }
    }

    public SimpleObjectProperty<Grid> gridProperty() {
        return grid;
    }

    public Character getLetter() {
        return grid.getValue().getLetter(line, column);
    }
}
