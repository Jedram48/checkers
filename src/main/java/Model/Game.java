package Model;

import java.util.ArrayList;

public class Game {

    Board board;
    ArrayList<Piece> WhitePieces;
    ArrayList<Piece> BlackPieces;

    public Game()
    {
        startingPosition();
    }

    public void updateGame(String s)
    {

    }


    public void startingPosition()
    {
        for(int i = board.sizeY - 4; i < board.sizeY; i++)
        {
            for(int j = 0; j < board.sizeX; j++)
            {
                if (board.Fields[j][i].color == Color.BLACK)
                    board.Fields[j][i].piece = new Piece(Color.BLACK);
            }
        }

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < board.sizeX; j++)
            {
                if (board.Fields[j][i].color == Color.BLACK)
                    board.Fields[j][i].piece = new Piece(Color.WHITE);
            }
        }

    }

}
