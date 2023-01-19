package Model;

public class Rules {


    public boolean isLegal(Board board, Field startField, Field endField)
    {
        if (startField.piece == null) return false;
        if (endField.piece != null) return false;
        if (board.whiteTurn == true && startField.piece.color == Piece_color.BLACK) return false;
        if (board.whiteTurn == false && startField.piece.color == Piece_color.WHITE) return false;
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
        if (isAttackPossible(board))
        {
            if(KINGcanAttack(startField,board))
            {
                if(enemyPiecesOnPath(startField,endField,board) == 1)
                    return true;
            }
            else return false;
        }
        else
        {
            if (enemyPiecesOnPath(startField, endField, board) == 0) return true;
            else return false;
        }
        return false;

    }
    public boolean isAttackPossible(Board board)
    {
        for(int i = 0; i < board.sizeY; i++)
        {
            for (int j = 0; j < board.sizeX; j++)
            {
                if ( CHECKERCanAtack(board.Fields[j][i], board) || KINGcanAttack(board.Fields[j][i], board)) return true;
            }
        }
        return false;
    }

    public boolean CHECKERCanAtack(Field startField, Board board)
    {
        if (startField.piece == null) return false;
        if (startField.piece.pieceType == PieceType.KING) return false;
        if (startField.piece.color == Piece_color.WHITE)
        {
            if ( board.validIndex(startField.x - 2, startField.y + 2) &&
                    board.Fields[startField.x - 1][startField.y + 1].piece != null &&
                    board.Fields[startField.x - 1][startField.y + 1].piece.color == Piece_color.BLACK &&
                    board.Fields[startField.x - 2][startField.y + 2].piece == null) return true;
            else if ( board.validIndex(startField.x + 2, startField.y - 2) &&
                    board.Fields[startField.x + 1][startField.y + 1].piece != null &&
                    board.Fields[startField.x + 1][startField.y + 1].piece.color == Piece_color.BLACK &&
                    board.Fields[startField.x + 2][startField.y + 2].piece == null) return true;
            else return false;
        }
        else if (startField.piece.color == Piece_color.BLACK)
        {
            if ( board.validIndex(startField.x - 2, startField.y - 2) &&
                    board.Fields[startField.x - 1][startField.y - 1].piece != null &&
                    board.Fields[startField.x - 1][startField.y - 1].piece.color == Piece_color.WHITE &&
                    board.Fields[startField.x - 2][startField.y - 2].piece == null) return true;
            else if ( board.validIndex(startField.x + 2, startField.y - 2) &&
                    board.Fields[startField.x + 1][startField.y - 1].piece != null &&
                    board.Fields[startField.x + 1][startField.y - 1].piece.color == Piece_color.WHITE &&
                    board.Fields[startField.x + 2][startField.y - 2].piece == null) return true;
            else return false;
        }
        else return false;
    }

    public boolean KINGcanAttack(Field startField, Board board)
    {
        if (startField.piece == null) return false;
        if (startField.piece.pieceType == PieceType.CHECKER) return false;
        if ( KINGcanAttackinOneOfDiagonals(startField, board, 1) ||
        KINGcanAttackinOneOfDiagonals(startField, board, 2) ||
        KINGcanAttackinOneOfDiagonals(startField, board, 3) ||
        KINGcanAttackinOneOfDiagonals(startField, board, 4)) return true;
        return false;
    }

    public boolean canAttack(Field startField, Board board){
        if(startField.piece.pieceType == PieceType.CHECKER)return CHECKERCanAtack(startField, board);
        else if(startField.piece.pieceType == PieceType.KING)return  KINGcanAttack(startField, board);
        else return false;
    }

    public boolean KINGcanAttackinOneOfDiagonals(Field startField, Board board, int diagonalNumber)
    {
        int x = -1;
        int y = -1;

        switch (diagonalNumber)
        {
            case 1:
                x = startField.x + 1;
                y = startField.y + 1;
                break;
            case 2:
                x = startField.x - 1;
                y = startField.y + 1;
                break;
            case 3:
                x = startField.x - 1;
                y = startField.y - 1;
                break;
            case 4:
                x = startField.x + 1;
                y = startField.y - 1;
                break;
        }

        boolean enemyPieceFound = false;

        while (board.validIndex(x, y))
        {
            if (enemyPieceFound)
            {
                if (board.Fields[x][y].piece == null) return true;
                else return false;
            }
            else
            {
                if (board.Fields[x][y].piece != null)
                {
                    if (board.Fields[x][y].piece.color == startField.piece.color)
                        return false;
                    else enemyPieceFound = true;
                }
            }

            switch (diagonalNumber)
            {
                case 1:
                    x++;
                    y++;
                    break;
                case 2:
                    x--;
                    y++;
                    break;
                case 3:
                    x--;
                    y--;
                    break;
                case 4:
                    x++;
                    y--;
                    break;
            }
        }
        return false;
    }
    int distance(Board board, Field startField, Field endField)
    {
        if ( Math.abs(startField.x - endField.x) != Math.abs(startField.y - endField.y)) return -1;
        else return Math.abs(startField.x - endField.x);
    }

    int enemyPiecesOnPath(Field startField, Field endField, Board board)
    {
        if (startField == endField) return -1;
        int Xincrementer;
        int Yincrementer;

        if (startField.x > endField.x)
        {
            if (startField.y > endField.y)
            {
                Xincrementer = -1;
                Yincrementer = -1;
            }
            else
            {
                Xincrementer = -1;
                Yincrementer = 1;
            }
        }
        else
        {
            if (startField.y > endField.y)
            {
                Xincrementer = 1;
                Yincrementer = -1;
            }
            else
            {
                Xincrementer = 1;
                Yincrementer = 1;
            }
        }

        int x = startField.x + Xincrementer;
        int y = startField.y + Yincrementer;
        int enemyPiecesFound = 0;

        while(x != endField.x && y != endField.y)
        {
            if(board.Fields[x][y].piece != null && board.Fields[x][y].piece.color == startField.piece.color) return -1;
            if(board.Fields[x][y].piece != null && board.Fields[x][y].piece.color != startField.piece.color)
                enemyPiecesFound++;
            x = x + Xincrementer;
            y = y + Yincrementer;
        }

        return enemyPiecesFound;
    }

    public Field getFieldOfEnemyPieceOnPath(Field startField, Field endField, Board board)
    {
        if (startField == endField) return null;
        int Xincrementer;
        int Yincrementer;

        if (startField.x > endField.x)
        {
            if (startField.y > endField.y)
            {
                Xincrementer = -1;
                Yincrementer = -1;
            }
            else
            {
                Xincrementer = -1;
                Yincrementer = 1;
            }
        }
        else
        {
            if (startField.y > endField.y)
            {
                Xincrementer = 1;
                Yincrementer = -1;
            }
            else
            {
                Xincrementer = 1;
                Yincrementer = 1;
            }
        }

        int x = startField.x + Xincrementer;
        int y = startField.y + Yincrementer;

        while(x != endField.x && y != endField.y)
        {
            if(board.Fields[x][y].piece != null && board.Fields[x][y].piece.color == startField.piece.color) return null;
            if(board.Fields[x][y].piece != null && board.Fields[x][y].piece.color != startField.piece.color)
                return board.Fields[x][y];
            x = x + Xincrementer;
            y = y + Yincrementer;
        }
        return null;
    }



    public boolean didWhiteLost(Board board)
    {
        for (int i = 0 ; i < board.sizeY; i++)
        {
            for ( int j = 0 ; j < board.sizeX; j++)
            {
                if ( board.Fields[j][i].piece != null && board.Fields[j][i].piece.color == Piece_color.WHITE)
                {
                    if (pieceCanMove(board.Fields[j][i], board)) return false;
                }
            }
        }
        return true;
    }

    public boolean didBlackLost(Board board)
    {
        for (int i = 0 ; i < board.sizeY; i++)
        {
            for ( int j = 0 ; j < board.sizeX; j++)
            {
                if ( board.Fields[j][i].piece != null && board.Fields[j][i].piece.color == Piece_color.BLACK)
                {
                    if (pieceCanMove(board.Fields[j][i], board)) return false;
                }
            }
        }
        return true;
    }

    public boolean pieceCanMove(Field startField, Board board)
    {
        int x = startField.x;
        int y = startField.y;

        if (startField.piece.pieceType == PieceType.CHECKER)
        {
            if (CHECKERCanAtack(startField, board)) return true;
            if (startField.piece.color == Piece_color.WHITE)
            {
                if (board.validIndex(x + 1, y + 1) &&
                board.Fields[x + 1][y + 1] == null) return true;
                else if (board.validIndex(x - 1, y + 1) &&
                        board.Fields[x - 1][y + 1].piece == null) return true;
                else return false;
            }
            else
            {
                if (board.validIndex(x + 1, y - 1) &&
                        board.Fields[x + 1][y - 1].piece == null) return true;
                else if (board.validIndex(x - 1, y - 1) &&
                        board.Fields[x - 1][y - 1].piece == null) return true;
                else return false;
            }
        }
        else
        {
            KINGcanAttack(startField, board);
            for (int i = 0 ; i < board.sizeY; i++)
            {
                for ( int j = 0 ; j < board.sizeX; j++)
                {
                    if (isLegalforKING(board, startField, board.Fields[j][i])) return false;
                }
            }
            return false;
        }
    }













}
