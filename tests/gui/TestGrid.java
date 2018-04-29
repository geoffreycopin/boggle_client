package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestGrid extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BoggleGrid grid = new BoggleGrid();
        Scene s = new Scene(grid, 500, 500);
        grid.setPrefHeight(200);
        grid.setPrefWidth(200);
        primaryStage.setScene(s);
        primaryStage.show();
    }
}
