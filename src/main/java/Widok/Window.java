package Widok;

import Model.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Window {

    Group root = new Group();
    Stage stage = new Stage();
    Scene scene = new Scene(root);

    public Window(){
        this.root.getChildren().add(new Play_board().getBoard());
        this.stage.setScene(scene);
        this.stage.show();
    }

}
