package Widok;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Checkers extends Application {
    private static final int BOARD_SIZE = 8;
    private static final int TILE_SIZE = 50;

    private Button[][] board;
    private Circle[][] pieces;
    private boolean[][] isOccupied;

    private boolean playerTurn = true; // true for white, false for black

    @Override
    public void start(Stage stage) {
        // Create the game board
        GridPane gridPane = new GridPane();
        board = new Button[BOARD_SIZE][BOARD_SIZE];
        pieces = new Circle[BOARD_SIZE][BOARD_SIZE];
        isOccupied = new boolean[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                // Create a button for the current square on the board
                Button button = new Button();
                button.setPrefSize(TILE_SIZE, TILE_SIZE);
                button.setOnAction(event -> handleMove(button));
                board[row][col] = button;

                // Add a piece to the button if it is the starting position of a piece
                if ((row + col) % 2 == 0 && row < 3) {
                    Circle circle = new Circle(TILE_SIZE / 2 - 5, Color.BLACK);
                    button.setGraphic(new StackPane(circle));
                    pieces[row][col] = circle;
                    isOccupied[row][col] = true;
                } else if ((row + col) % 2 == 0 && row > 4) {
                    Circle circle = new Circle(TILE_SIZE / 2 - 5, Color.WHITE);
                    button.setGraphic(new StackPane(circle));
                    pieces[row][col] = circle;
                    isOccupied[row][col] = true;
                }

                // Add the button to the grid pane
                gridPane.add(button, col, row);
            }
        }

        // Create the scene and add the grid pane to it
        Scene scene = new Scene(gridPane, TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    private void handleMove(Button button) {
        // Find the position of the button in the board array
        int row = -1;
        int col = -1;
        search:
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == button) {
                    row = i;
                    col = j;
                    break search;
                }
            }
        }
        if (row == -1 || col == -1) {
            return; // button not found in board
        }

        if (!isOccupied[row][col]) {
            // If the selected square is not occupied, try to move the current piece to it
            movePiece(row, col);
        } else {
            // If the selected square is occupied, try to capture the piece on it
            capturePiece(row, col);
        }
    }

    private void movePiece(int row, int col) {
        // Find the position of the current piece
        int currentRow = -1;
        int currentCol = -1;
        search:
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (pieces[i][j] != null && pieces[i][j].getFill() == (playerTurn ? Color.WHITE : Color.BLACK)) {
                    currentRow = i;
                    currentCol = j;
                    break search;
                }
            }
        }

        if (currentRow == -1 || currentCol == -1) {
            return; // current piece not found
        }

        // Check if the move is valid
        if (Math.abs(currentRow - row) == 1 && Math.abs(currentCol - col) == 1 && !isOccupied[row][col]) {
            // Move the piece
            board[currentRow][currentCol].setGraphic(null);
            pieces[currentRow][currentCol] = null;
            isOccupied[currentRow][currentCol] = false;

            Circle circle = new Circle(TILE_SIZE / 2 - 5, playerTurn ? Color.WHITE : Color.BLACK);
            board[row][col].setGraphic(new StackPane(circle));
            pieces[row][col] = circle;
            isOccupied[row][col] = true;

            playerTurn = !playerTurn; // switch turns
        }
    }

    private void capturePiece(int row, int col) {
        // Find the position of the current piece
        int currentRow = -1;
        int currentCol = -1;
        search:
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (pieces[i][j] != null && pieces[i][j].getFill() == (playerTurn ? Color.WHITE : Color.BLACK)) {
                    currentRow = i;
                    currentCol = j;
                    break search;
                }
            }
        }

        if (currentRow == -1 || currentCol == -1) {
            return; // current piece not found
        }

        // Check if the capture is valid
        if (Math.abs(currentRow - row) == 2 && Math.abs(currentCol - col) == 2) {
            int capturedRow = (currentRow + row) / 2;
            int capturedCol = (currentCol + col) / 2;

            // Capture the piece
            board[currentRow][currentCol].setGraphic(null);
            pieces[currentRow][currentCol] = null;
            isOccupied[currentRow][currentCol] = false;

            board[capturedRow][capturedCol].setGraphic(null);
            pieces[capturedRow][capturedCol] = null;
            isOccupied[capturedRow][capturedCol] = false;

            Circle circle = new Circle(TILE_SIZE / 2 - 5, playerTurn ? Color.WHITE : Color.BLACK);
            board[row][col].setGraphic(new StackPane(circle));
            pieces[row][col] = circle;
            isOccupied[row][col] = true;

            playerTurn = !playerTurn; // switch turns
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

