package Widok;

import Model.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window {

    Group root = new Group();
    Stage stage = new Stage();
    Scene scene = new Scene(root);

    public Window(Board board){
        this.root.getChildren().add(new Play_board(board).getBoard());
        this.stage.setScene(scene);
        this.stage.show();
    }

}
