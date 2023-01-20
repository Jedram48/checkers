package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    Field blank = new Field(Piece_color.WHITE, 4,5);
    @Test
    void getColor() {
        assertEquals(Piece_color.WHITE, blank.getColor());
    }

    @Test
    void getType() {
        assertNull(blank.getType());
        blank.piece = new Piece(Piece_color.WHITE, PieceType.CHECKER);
        assertEquals(PieceType.CHECKER, blank.getType());
    }

    @Test
    void getX() {
        assertEquals(4, blank.getX());
    }

    @Test
    void getY() {
        assertEquals(5, blank.getY());
    }

    @Test
    void getPieceColor() {
        assertNull(blank.getPieceColor());
        blank.piece = new Piece(Piece_color.WHITE, PieceType.CHECKER);
        assertEquals(Piece_color.WHITE, blank.getPieceColor());
    }
}