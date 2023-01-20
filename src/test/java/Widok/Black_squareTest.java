package Widok;

import Model.PieceType;
import Model.Piece_color;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class Black_squareTest {

    @Test
    void getPosX() {
        Black_square square = new Black_square(1, 5, Piece_color.BLACK);
        assertEquals(1, square.getPosX());
    }

    @Test
    void getPosY() {
        Black_square square = new Black_square(1, 5, Piece_color.BLACK);
        assertEquals(5, square.getPosY());
    }

    @Test
    void setColor() {
        Color color = Color.GRAY;

        Black_square square = new Black_square(1, 5, Piece_color.BLACK);
        square.setColor(color);

        assertEquals(color, square.getColor());
    }

    @Test
    void getColor() {
        Black_square square = new Black_square(1, 5, Piece_color.BLACK);

        assertEquals(Color.BLACK, square.getColor());
    }

    @Test
    void getPieceColor() {
        Black_square square = new Black_square(1, 5, Piece_color.BLACK);

        assertEquals(Piece_color.BLACK, square.getPieceColor());
    }

    @Test
    void switchOccupation() {
        Black_square square = new Black_square(1, 5, Piece_color.BLACK);
        square.switchOccupation();
        assertFalse(square.occupied);
    }

    @Test
    void isOccupied() {
        Black_square square = new Black_square(1, 5, Piece_color.BLACK);
        assertTrue(square.isOccupied());
    }
}