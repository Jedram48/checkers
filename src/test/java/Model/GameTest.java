package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game = new Game();
    @Test
    void isLegalInString() {
        assertTrue(game.isLegalInString(3,2, 4,3));
    }

    @Test
    void moveInString() {
        game.moveInString(3,2, 4,3);
        assertSame(game.getBoard().Fields[4][3].getPieceColor(), Piece_color.WHITE);
    }

    @Test
    void isLegal() {
        assertTrue(game.isLegal(game.getBoard().Fields[3][2], game.getBoard().Fields[4][3]));
    }

    @Test
    void move() {
        game.move(game.getBoard().Fields[3][2], game.getBoard().Fields[4][3]);
        assertSame(game.getBoard().Fields[4][3].getPieceColor(), Piece_color.WHITE);
    }

    @Test
    void changeToKing() {
        game.changeToKing(game.getBoard().Fields[3][2]);
        assertNotSame(game.getBoard().Fields[3][2].getType(), PieceType.KING);
    }


    @Test
    void getBoard() {
        assertEquals(game.board, game.getBoard());
    }
}