package com.example.checkers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CheckersController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}