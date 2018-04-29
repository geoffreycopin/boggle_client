package gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class BoggleGrid extends GridPane {
    private SimpleObjectProperty<Character>[] letters;

    public BoggleGrid() {
        createCells();
        addListeners();
    }

    private void createCells() {
        letters = new SimpleObjectProperty[16];
        for (int i = 0; i < 16; i++) {
            letters[i] = new SimpleObjectProperty<>('A');
            GridCell cell = new GridCell(letters[i]);
            cell.prefHeightProperty().bind(heightProperty().divide(4));
            cell.prefWidthProperty().bind(widthProperty().divide(4));
            int row = i / 4;
            int col = i % 4;
            setConstraints(cell, row, col);
            getChildren().add(cell);
        }
    }

    private void addListeners() {
        setOnMouseDragged((e) -> {
            if (true) {
                GridCell c = cellAt(e.getX(), e.getY());
                if (c != null) {
                    c.setSelected(true);
                }
            }
        });
    }

    private GridCell cellAt(double x, double y) {
        for (Node n: getChildren()) {
            GridCell cell = (GridCell) n;
            cell.getWidth();
            if (cell.getLayoutX() <= x && cell.getLayoutX() + cell.getWidth() >= x &&
                    cell.getLayoutY() <= y && cell.getLayoutY() + cell.getHeight() >= y) {
                return cell;
            }
        }
        return null;
    }

    public SimpleObjectProperty<Character>[] lettersProperty() {
        return letters;
    }
}
