package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board = new Board(8,8, true);
    @Test
    void validIndex() {
        assertTrue(board.validIndex(3,2));
    }

    @Test
    void distance() {
        assertEquals(2, board.distance(board.getFields()[3][2], board.getFields()[5][4]));
    }

    @Test
    void getSizeX() {
        assertEquals(8, board.getSizeX());
    }

    @Test
    void getSizeY() {
        assertEquals(8, board.getSizeY());
    }

    @Test
    void getFields() {
        assertEquals(board.Fields, board.getFields());
    }

    @Test
    void gameOver() {
        assertFalse(board.gameOver());
    }
}