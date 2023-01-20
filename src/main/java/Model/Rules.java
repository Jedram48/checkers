package Model;

public class Rules {


    /***
     * Check if move is valid based on selected fields and board state
     * @param board current board state
     * @param startField start of move
     * @param endField end of move
     * @return boolean
     */
    public boolean isLegal(Board board, Field startField, Field endField)
    {
        if(startField.piece == null) return false;
        else if (board.whiteTurn && startField.piece.color == Piece_color.BLACK) return false;
        else if (!board.whiteTurn && startField.piece.color == Piece_color.WHITE) return false;
        else if (startField.piece.pieceType == PieceType.CHECKER) return isLegalforCHECKER(board, startField, endField);
        else if(startField.piece.pieceType == PieceType.KING) return isLegalforKING(board, startField, endField);
        else {return false;}
    }

    /***
     * Check if move is valid for common checker
     * @param board current board state
     * @param startField start of move
     * @param endField end of move
     * @return boolean
     */
    public boolean isLegalforCHECKER(Board board, Field startField, Field endField)
    {
        int distance = distance(startField, endField);
        if(distance == -1) return false;
        else if(distance == 1 && moveForward(startField, endField)){
            return !isAttackPossible(board) && !CHECKERCanAtack(startField,board) && endField.piece == null;
        }
        else if(distance == 2){
            if(startField.piece.pieceType == PieceType.CHECKER && CHECKERCanAtack(startField, board)){
                try{if(getFieldOfEnemyPieceOnPath(startField, endField, board).getPieceColor() != startField.getPieceColor()) return endField.piece == null;}
                catch (NullPointerException ignore){}
            }
        }
        return false;
    }

    /***
     * Check if move is valid for king
     * @param board current board state
     * @param startField start of move
     * @param endField end of move
     * @return boolean
     */
    public boolean isLegalforKING(Board board, Field startField, Field endField)
    {
        if (isAttackPossible(board))
        {
            if(KINGcanAttack(startField,board))
            {
                return enemyPiecesOnPath(startField, endField, board) == 1;
            }
            else return false;
        }
        else
        {
            return enemyPiecesOnPath(startField, endField, board) == 0;
        }

    }

    /***
     * Check if active side can perform any attack
     * @param board current board state
     * @return boolean
     */
    public boolean isAttackPossible(Board board)
    {
        for(int i = 0; i < board.sizeY; i++)
        {
            for (int j = 0; j < board.sizeX; j++)
            {
                if(board.whiteTurn &&
                        board.Fields[j][i].getPieceColor() == Piece_color.WHITE &&
                        ((CHECKERCanAtack(board.Fields[j][i], board) && board.Fields[j][i].piece.pieceType == PieceType.CHECKER) ||
                                (KINGcanAttack(board.Fields[j][i], board) && board.Fields[j][i].piece.pieceType == PieceType.KING))) return true;
                else if(!board.whiteTurn &&
                        board.Fields[j][i].getPieceColor() == Piece_color.BLACK &&
                        ((CHECKERCanAtack(board.Fields[j][i], board) && board.Fields[j][i].piece.pieceType == PieceType.CHECKER) ||
                                (KINGcanAttack(board.Fields[j][i], board) && board.Fields[j][i].piece.pieceType == PieceType.KING))) return true;
            }
        }
        return false;
    }

    /***
     * Check if given checker can perform attack
     * @param startField field with checker
     * @param board current board state
     * @return boolean
     */
    public boolean CHECKERCanAtack(Field startField, Board board)
    {
        for(int a = -1; a < 2; a += 2){
            for(int b = -1; b < 2; b += 2){
                try{if(board.Fields[startField.getX() + 2 * a][startField.getY() + 2 * b].piece == null){
                    if(board.Fields[startField.getX() + a][startField.getY() + b].piece != null &&
                            board.Fields[startField.getX() + a][startField.getY() + b].getPieceColor() != startField.getPieceColor()) return true;
                    }
                }
                catch (IndexOutOfBoundsException ignored){}
            }
        }
        return false;
    }

    /***
     * Check if king on given field can perform attack
     * @param startField field with king
     * @param board current board state
     * @return boolean
     */
    public boolean KINGcanAttack(Field startField, Board board)
    {
        if ( KINGcanAttackinOneOfDiagonals(startField, board, 1) ||
        KINGcanAttackinOneOfDiagonals(startField, board, 2) ||
        KINGcanAttackinOneOfDiagonals(startField, board, 3) ||
        KINGcanAttackinOneOfDiagonals(startField, board, 4)) return true;
        return false;
    }

    /***
     * Check if any unite can attack from this field
     * @param startField field with unite
     * @param board current board state
     * @return boolean
     */
    public boolean canAttack(Field startField, Board board){
        if(startField.piece.pieceType == PieceType.CHECKER)return CHECKERCanAtack(startField, board);
        else if(startField.piece.pieceType == PieceType.KING)return  KINGcanAttack(startField, board);
        else return false;
    }

    /***
     * Check if king can attack on any diagonal
     * @param startField start position of king
     * @param board current board state
     * @param diagonalNumber diagonal of attack
     * @return boolean
     */
    public boolean KINGcanAttackinOneOfDiagonals(Field startField, Board board, int diagonalNumber)
    {
        int x = -1;
        int y = -1;

        switch (diagonalNumber) {
            case 1 -> {
                x = startField.x + 1;
                y = startField.y + 1;
            }
            case 2 -> {
                x = startField.x - 1;
                y = startField.y + 1;
            }
            case 3 -> {
                x = startField.x - 1;
                y = startField.y - 1;
            }
            case 4 -> {
                x = startField.x + 1;
                y = startField.y - 1;
            }
        }

        boolean enemyPieceFound = false;

        while (board.validIndex(x, y))
        {
            if (enemyPieceFound)
            {
                return board.Fields[x][y].piece == null;
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

            switch (diagonalNumber) {
                case 1 -> {
                    x++;
                    y++;
                }
                case 2 -> {
                    x--;
                    y++;
                }
                case 3 -> {
                    x--;
                    y--;
                }
                case 4 -> {
                    x++;
                    y--;
                }
            }
        }
        return false;
    }

    /***
     * Calculate amount of fields between start position and end position
     * @param startField start position
     * @param endField end positoin
     * @return Integer
     */
    int distance(Field startField, Field endField)
    {
        if(Math.abs(startField.getX() - endField.getX()) ==
                Math.abs(startField.getY() - endField.getY()))
            return Math.abs(startField.getX() - endField.getX());
        else return -1;
    }

    /***
     * Calculate how many unites between start position and end position
     * @param startField start position
     * @param endField end position
     * @param board current board state
     * @return Integer
     */
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

    /***
     * Get field with unite between start position and end position
     * @param startField start position
     * @param endField end position
     * @param board current board state
     * @return Field
     */
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
            if(board.Fields[x][y].piece != null){
                if(board.Fields[x][y].piece.color != startField.piece.color)return  board.Fields[x][y];
                else return null;
            }
            x = x + Xincrementer;
            y = y + Yincrementer;
        }
        return null;
    }


    /***
     * Check if white lost
     * @param board current board state
     * @return boolean
     */
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

    /***
     * Check if black lost
     * @param board current board state
     * @return boolean
     */
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

    /***
     * Check if checker moves in correct direction
     * @param startField start position
     * @param endField end position
     * @return boolean
     */
    private boolean moveForward(Field startField, Field endField){
        if(startField.getPieceColor() == Piece_color.WHITE)return startField.getY() < endField.getY();
        else if(startField.getPieceColor() == Piece_color.BLACK)return  startField.getY() > endField.getY();
        return false;
    }


    /***
     * Check if unite on given field can perform move
     * @param startField field with unite
     * @param board current board state
     * @return boolean
     */
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
