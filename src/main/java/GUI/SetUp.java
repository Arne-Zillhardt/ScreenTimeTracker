package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SetUp {
    public void start(Stage stage) {
        GUI.stage = stage;

        GUI.pane = new GridPane();
        GUI.pane.setAlignment(Pos.CENTER);

        GUI.scene = new Scene(GUI.pane, 300, 300);

        GUI.stage.setScene(GUI.scene);

        GUI.stage.show();
    }
}
