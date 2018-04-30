package gui;

import game.Grid;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class BoggleGrid extends GridPane {
    private SimpleObjectProperty<Grid> gridProperty;

    public BoggleGrid() {
        char letters[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};
        gridProperty = new SimpleObjectProperty<>(new Grid(letters));
        createCells();
        addListeners();
    }

    private void createCells() {
        for (int i = 0; i < 16; i++) {
            Pair<Character, Integer> coordinates = Grid.coordinatesOfIndex(i);
            GridCell cell = new GridCell(coordinates.getKey(), coordinates.getValue());
            cell.gridProperty().bind(gridProperty());
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
        double H_PADDING = getWidth() / 16;
        double V_PADDING = getHeight() / 16;

        for (Node n: getChildren()) {
            GridCell cell = (GridCell) n;
            cell.getWidth();
            if (cell.getLayoutX() + H_PADDING <= x && cell.getLayoutX() + cell.getWidth() - H_PADDING >= x &&
                    cell.getLayoutY() + V_PADDING <= y && cell.getLayoutY() + cell.getHeight() - V_PADDING >= y) {
                return cell;
            }
        }

        return null;
    }

    public SimpleObjectProperty<Grid> gridProperty() {
        return gridProperty;
    }
}
