package Model;

import java.util.Scanner;

public class TestingBoard {
    public static void main(String[] args)
    {
        Game game = new Game();

        while(game.gameIsOn)
        {
            game.board.displayGamestate();
            System.out.println("WhiteTurn: " + game.board.whiteTurn);
            Scanner scanner = new Scanner(System.in);
            String move = scanner.nextLine();
            if(move.equals("exit")) break;

            String[] splitMove = move.split(" ");

            int x = Integer.parseInt(splitMove[0]);
            int y = Integer.parseInt(splitMove[1]);
            int x2 = Integer.parseInt(splitMove[2]);
            int y2 = Integer.parseInt(splitMove[3]);

            game.move(game.board.Fields[x][y], game.board.Fields[x2][y2]);

        }


        /*Field startField = game.board.Fields[5][2];
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

        System.out.println(game.rules.CHECKERCanAtack(game.board.Fields[1][6], game.board));
*/

    }



}