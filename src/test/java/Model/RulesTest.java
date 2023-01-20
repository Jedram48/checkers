package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RulesTest {

    Game game = new Game();

    Rules rules = new Rules();

    @Test
    void isLegal() {
        Field field1 = game.getBoard().Fields[3][2];
        Field field2 = game.getBoard().Fields[4][3];
        assertTrue(rules.isLegal(game.getBoard(), field1, field2));
    }

    @Test
    void isLegalforCHECKER() {
        Field field1 = game.getBoard().Fields[3][2];
        Field field2 = game.getBoard().Fields[4][3];
        assertTrue(rules.isLegalforCHECKER(game.getBoard(), field1, field2));
    }

    @Test
    void isAttackPossible() {
        game.move(game.getBoard().Fields[3][2], game.getBoard().Fields[4][3]);
        game.move(game.getBoard().Fields[4][5], game.getBoard().Fields[3][4]);
        game.move(game.getBoard().Fields[4][3], game.getBoard().Fields[5][4]);
        assertTrue(rules.isAttackPossible(game.getBoard()));
    }

    @Test
    void CHECKERCanAtack() {
        game.move(game.getBoard().Fields[3][2], game.getBoard().Fields[4][3]);
        game.move(game.getBoard().Fields[4][5], game.getBoard().Fields[3][4]);
        game.move(game.getBoard().Fields[4][3], game.getBoard().Fields[5][4]);
        assertTrue(rules.CHECKERCanAtack(game.getBoard().Fields[6][5], game.getBoard()));
    }

    @Test
    void canAttack() {
        game.move(game.getBoard().Fields[3][2], game.getBoard().Fields[4][3]);
        game.move(game.getBoard().Fields[4][5], game.getBoard().Fields[3][4]);
        game.move(game.getBoard().Fields[4][3], game.getBoard().Fields[5][4]);
        assertTrue(rules.canAttack(game.getBoard().Fields[6][5], game.getBoard()));
    }

    @Test
    void distance() {
        assertEquals(1, rules.distance(game.getBoard().Fields[3][2], game.getBoard().Fields[4][3]));
    }

    @Test
    void enemyPiecesOnPath() {
        game.move(game.getBoard().Fields[3][2], game.getBoard().Fields[4][3]);
        game.move(game.getBoard().Fields[4][5], game.getBoard().Fields[3][4]);
        game.move(game.getBoard().Fields[4][3], game.getBoard().Fields[5][4]);
        assertEquals(1 ,rules.enemyPiecesOnPath(game.getBoard().Fields[6][5], game.getBoard().Fields[4][3], game.getBoard()));
    }

    @Test
    void getFieldOfEnemyPieceOnPath() {
        game.move(game.getBoard().Fields[3][2], game.getBoard().Fields[4][3]);
        game.move(game.getBoard().Fields[4][5], game.getBoard().Fields[3][4]);
        game.move(game.getBoard().Fields[4][3], game.getBoard().Fields[5][4]);
        assertEquals(game.getBoard().Fields[5][4] ,rules.getFieldOfEnemyPieceOnPath(game.getBoard().Fields[6][5], game.getBoard().Fields[4][3], game.getBoard()));
    }

    @Test
    void didWhiteLost() {
        assertFalse(rules.didWhiteLost(game.getBoard()));
    }

    @Test
    void didBlackLost() {
        assertFalse(rules.didWhiteLost(game.getBoard()));
    }

    @Test
    void pieceCanMove() {
        assertTrue(rules.pieceCanMove(game.getBoard().Fields[3][2], game.getBoard()));
    }
}