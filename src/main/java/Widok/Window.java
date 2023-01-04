package Widok;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window {

    Group root = new Group();
    Stage stage = new Stage();
    Scene scene = new Scene(root);

    Window(){
        this.root.getChildren().add(new Board().getBoard());
        this.stage.setScene(scene);
        this.stage.show();
    }

}
