package gui;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestGridCell extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridCell gc = new GridCell(new SimpleObjectProperty<>('A'));
        gc.setSelected(false);
        Scene s = new Scene(gc, 500, 500);
        primaryStage.setScene(s);
        primaryStage.show();
    }
}
