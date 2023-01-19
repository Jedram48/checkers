package Widok;

import Model.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window {

    Group root = new Group();
    Stage stage = new Stage();
    Scene scene = new Scene(root);

    public Window(){
        Play_board board = new Play_board();
        this.root.getChildren().add(board.getBoard());

        if(board.isWhite())this.stage.setTitle("White");
        else{this.stage.setTitle("Black");}

        this.stage.setScene(scene);
        this.stage.show();
    }

}
