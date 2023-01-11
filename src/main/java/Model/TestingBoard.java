package Model;

public class TestingBoard {
    public static void main(String[] args)
    {
        Game game = new Game();
        game.board.displayGamestate();

        Field startField = game.board.Fields[5][2];
        Field endField = game.board.Fields[4][3];

        Field startField2 = game.board.Fields[2][5];
        Field endField2 = game.board.Fields[3][4];

        Field startField3 = game.board.Fields[4][3];
        Field endField3 = game.board.Fields[2][5];

        game.move(startField, endField);

        System.out.println();
        game.board.displayGamestate();

        game.move(startField2, endField2);

        System.out.println();
        game.board.displayGamestate();

        game.move(startField3, endField3);

        System.out.println();
        game.board.displayGamestate();

        System.out.println(game.rules.pieceCanAtack(game.board.Fields[1][6], game.board));


    }



}