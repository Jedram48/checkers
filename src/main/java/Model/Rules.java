package Model;

import java.util.ArrayList;

public class Rules {


    public boolean isLegal(Board board, Field startField, Field endField)
    {
        if (startField.piece == null) return false;
        if (board.whiteTurn == true && startField.piece.color == Color.BLACK) return false;
        if (board.whiteTurn == false && startField.piece.color == Color.WHITE) return false;
        if (startField.piece.pieceType == PieceType.CHECKER) return isLegalforCHECKER(board, startField, endField);
        else return isLegalforKING(board, startField, endField);
    }

    public boolean isLegalforCHECKER(Board board, Field startField, Field endField)
    {
        int d = distance(board, startField, endField);
        if (d == -1) return false;
        if (isAttackPossible(board))
        {
            if (d != 2) return false;
            if (CHECKERCanAtack(startField, board)) return true;
            else return false;
        }
        else
        {
            if (d != 1) return false;
            else if ( endField.piece == null) return true;
            else return false;
        }
    }

    public boolean isLegalforKING(Board board, Field startField, Field endField)
    {
        int d = distance(board, startField, endField);


    }
    public boolean isAttackPossible(Board board)
    {
        for(int i = 0; i < board.sizeY; i++)
        {
            for (int j = 0; j < board.sizeX; j++)
            {
                if ( CHECKERCanAtack(board.Fields[j][i], board) ) return true;
            }
        }
        return false;
    }

    public boolean CHECKERCanAtack(Field startField, Board board)
    {
        if (startField.piece == null) return false;
        if (startField.piece.pieceType == PieceType.KING) return false;
        if (board.whiteTurn == true && startField.piece.color == Color.BLACK) return false;
        if (board.whiteTurn == false && startField.piece.color == Color.WHITE) return false;
        if (startField.piece.color == Color.WHITE)
        {
            if ( board.validIndex(startField.x - 2, startField.y + 2) &&
                    board.Fields[startField.x - 1][startField.y + 1].piece.color == Color.BLACK &&
                    board.Fields[startField.x - 2][startField.y + 2].piece == null) return true;
            else if ( board.validIndex(startField.x + 2, startField.y - 2) &&
                    board.Fields[startField.x + 1][startField.y + 1].piece.color == Color.BLACK &&
                    board.Fields[startField.x + 2][startField.y + 2].piece == null) return true;
            else return false;
        }
        else if (startField.piece.color == Color.BLACK)
        {
            System.out.println("check");
            if ( board.validIndex(startField.x - 2, startField.y - 2) &&
                    board.Fields[startField.x - 1][startField.y - 1].piece.color == Color.WHITE &&
                    board.Fields[startField.x - 2][startField.y - 2].piece == null) return true;
            else if ( board.validIndex(startField.x + 2, startField.y - 2) &&
                    board.Fields[startField.x + 1][startField.y - 1].piece.color == Color.WHITE &&
                    board.Fields[startField.x + 2][startField.y - 2].piece == null) return true;
            else return false;

        }
        else return false;
    }

    public boolean KINGcanAtack(Field startField, Board board)
    {
        if (startField.piece == null) return false;
        if (startField.piece.pieceType == PieceType.CHECKER) return false;
        if ( KINGcanAtackinOneOfDiagonals(startField, board, 1) ||
        KINGcanAtackinOneOfDiagonals(startField, board, 2) ||
        KINGcanAtackinOneOfDiagonals(startField, board, 3) ||
        KINGcanAtackinOneOfDiagonals(startField, board, 4)) return true;



    }

    public boolean KINGcanAtackinOneOfDiagonals(Field startField, Board board, int diagonalNumber)
    {
        int x = startField.x - 1;
        int y = startField.y + 1;
        boolean enemyPieceFound = false;

        while (board.validIndex(x, y))
        {
            if (enemyPieceFound)
            {
                if (board.Fields[x][y] == null) return true;
                else return false;
            }
            else
            {
                if (board.Fields[x][y].piece == null) continue;
                else
                {
                    if (board.Fields[x][y].piece.color == startField.piece.color)
                        return false;
                    else enemyPieceFound = true;
                }
            }

            if (diagonalNumber == 1)
            {
                x++;
                y++;
            }
            else if (diagonalNumber == 2)
            {
                x--;
                y++;
            }
            else if (diagonalNumber == 3)
            {
                x--;
                y--;
            }
            else
            {
                x++;
                y--;
            }
        }
        return false;
    }
    int distance(Board board, Field startField, Field endField)
    {
        if ( Math.abs(startField.x - endField.x) != Math.abs(startField.y - endField.y)) return -1;
        else return Math.abs(startField.x - endField.x);
    }
















}
