package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BoardTest {

    @Test
    void simpleBoardTests()
    {
        Board board = new Board(5, 4, true);
        assertEquals(4, board.sizeY);
        assertEquals(5, board.sizeX);
        assertEquals(Piece_color.WHITE, board.Fields[0][0].color);
        assertEquals(Piece_color.BLACK, board.Fields[4][3].color);

        Board board2 = new Board(5, 4, false);
        assertEquals(Piece_color.BLACK, board2.Fields[0][0].color);
        assertEquals(Piece_color.WHITE, board2.Fields[4][3].color);
    }

}