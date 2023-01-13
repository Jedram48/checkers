package Model;

public class Rules {


    public boolean isLegal(Board board, Field startField, Field endField)
    {
        if (startField.piece == null) return false;
        if (board.whiteTurn == true && startField.piece.piececolor == Piece_color.BLACK) return false;
        if (board.whiteTurn == false && startField.piece.piececolor == Piece_color.WHITE) return false;
        int d = distance(board, startField, endField);
        if (d == -1) return false;
        if (isAttackPossible(board))
        {
            if (d != 2) return false;
            if (pieceCanAtack(startField, board)) return true;
            else return false;
        }
        else
        {
            if (d != 1) return false;
            else if ( endField.piece == null) return true;
            else return false;
        }
    }

    public boolean isAttackPossible(Board board)
    {
        for(int i = 0; i < board.sizeY; i++)
        {
            for (int j = 0; j < board.sizeX; j++)
            {
                if ( pieceCanAtack(board.Fields[j][i], board) ) return true;
            }
        }
        return false;
    }

    public boolean pieceCanAtack(Field startField, Board board)
    {
        if (startField.piece == null) return false;
        if (board.whiteTurn == true && startField.piece.piececolor == Piece_color.BLACK) return false;
        if (board.whiteTurn == false && startField.piece.piececolor == Piece_color.WHITE) return false;
        if (startField.piece.piececolor == Piece_color.WHITE)
        {
            if ( board.validIndex(startField.x - 2, startField.y + 2) &&
                    board.Fields[startField.x - 1][startField.y + 1].piece.piececolor == Piece_color.BLACK &&
                    board.Fields[startField.x - 2][startField.y + 2].piece == null) return true;
            else if ( board.validIndex(startField.x + 2, startField.y - 2) &&
                    board.Fields[startField.x + 1][startField.y - 1].piece.piececolor == Piece_color.BLACK &&
                    board.Fields[startField.x + 2][startField.y - 2].piece == null) return true;
            else return false;
        }
        else if (startField.piece.piececolor == Piece_color.BLACK)
        {
            System.out.println("check");
            if ( board.validIndex(startField.x - 2, startField.y - 2) &&
                    board.Fields[startField.x - 1][startField.y - 1].piece.piececolor == Piece_color.WHITE &&
                    board.Fields[startField.x - 2][startField.y - 2].piece == null) return true;
            else if ( board.validIndex(startField.x + 2, startField.y - 2) &&
                    board.Fields[startField.x + 1][startField.y - 1].piece.piececolor == Piece_color.WHITE &&
                    board.Fields[startField.x + 2][startField.y - 2].piece == null) return true;
            else return false;

        }
        else return false;
    }

    int distance(Board board, Field startField, Field endField)
    {
        if ((startField.x - endField.x == 1 || startField.x - endField.x == -1) &&
                (startField.y - endField.y == 1 || startField.y - endField.y == -1))
            return 1;
        else if ((startField.x - endField.x == 2 || startField.x - endField.x == -2) &&
                (startField.y - endField.y == 2 || startField.y - endField.y == -2))
            return 2;
        else return -1;
    }
















}
