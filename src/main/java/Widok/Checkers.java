package Widok;

import Model.Board;
import javafx.application.Application;
import javafx.stage.Stage;

public class Checkers extends Application {

    @Override
    public void start(Stage stage) {
        Window win = new Window(new Board(8,8, true));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

