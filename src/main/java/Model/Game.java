package Model;


public class Game {

    public Board board;
    public boolean gameIsOn;
    public Rules rules;

    /***
     * Create new game and puts unites on start position
     */
    public Game()
    {
        this.rules = new Rules();
        this.board = new Board(8,8,true);
        this.gameIsOn = true;
        startingPosition();
    }

    /***
     * Displays current game state in console
     */
    public void displayGameState()
    {
        board.displayGamestate();
    }

    /***
     * Check if move is valid based on position of fields
     * @param x column of start field
     * @param y row of start field
     * @param x2 column of end field
     * @param y2 row of end field
     * @return boolean
     */
    public boolean isLegalInString(int x, int y, int x2, int y2)
    {
        return rules.isLegal(board, board.Fields[x][y], board.Fields[x2][y2]);
    }

    /***
     * Perform move for given field positions
     * @param x column of start field
     * @param y row of start field
     * @param x2 column of end field
     * @param y2 row of end field
     */
    public void moveInString(int x, int y, int x2, int y2)
    {
        move(board.Fields[x][y], board.Fields[x2][y2]);
    }

    /***
     * Check if move is valid based on instances of Field class
     * @param startField start field
     * @param endField end field
     * @return boolean
     */
    public boolean isLegal(Field startField, Field endField){
        return rules.isLegal(board, startField, endField);
    }

    /***
     * Perform move for given fields if move is valid and don't perform if move isn't valid
     * Based on performed move changes whole board state
     * @param startField start position of unite
     * @param endField end position of unite
     */
    public void move(Field startField, Field endField)
    {
        if(rules.isLegal(board, startField, endField))
        {
            if (board.distance(startField, endField) == 1)
            {
                endField.piece = startField.piece;
                startField.piece = null;
                changeToKing(endField);
                board.whiteTurn = !board.whiteTurn;
                return;
            }
            else if (startField.piece.pieceType == PieceType.CHECKER &&
                    board.distance(startField, endField) == 2 )
            {
                endField.piece = startField.piece;
                startField.piece = null;
                int middleFieldX = (startField.x - endField.x)/2 + endField.x;
                int middleFieldY = (startField.y - endField.y)/2 + endField.y;
                board.Fields[middleFieldX][middleFieldY].piece = null;
            }
            else if (startField.piece.pieceType == PieceType.KING)
            {
                if(rules.enemyPiecesOnPath(startField, endField, board) == 0){
                    endField.piece = startField.piece;
                    startField.piece = null;
                }
                else if(rules.enemyPiecesOnPath(startField, endField, board) == 1){
                    endField.piece = startField.piece;
                    rules.getFieldOfEnemyPieceOnPath(startField,endField,board).piece = null;
                    startField.piece = null;
                }
            }
        }
        else
        {
            System.out.println("BAD MOVE");
            return;
        }

        if (!rules.canAttack(endField, board))
        {
            board.whiteTurn = !board.whiteTurn;
            changeToKing(endField);
        }



        if (rules.didBlackLost(board))
        {
            board.gameIsOn = false;
            System.out.println("WHITE WIN!");
        }
        else if (rules.didWhiteLost(board))
        {
            board.gameIsOn = false;
            System.out.println("BLACK WIN!");
        }
    }

    /***
     * Change checker to king if it is allowed
     * @param endField end position of unite
     */
    public void changeToKing(Field endField){
        if ( endField.y == board.sizeY-1 &&
                endField.piece.pieceType == PieceType.CHECKER &&
                endField.piece.color == Piece_color.WHITE) endField.piece.pieceType = PieceType.KING;
        else if ( endField.y == 0 &&
                endField.piece.pieceType == PieceType.CHECKER &&
                endField.piece.color == Piece_color.BLACK) endField.piece.pieceType = PieceType.KING;
    }


    /***
     * Puts checkers to start position on board
     */
    public void startingPosition()
    {
        for(int i = board.sizeY - 3; i < board.sizeY; i++)
        {
            for(int j = 0; j < board.sizeX; j++)
            {
                if (board.Fields[j][i].color == Piece_color.BLACK)
                {
                    board.Fields[j][i].piece = new Piece(Piece_color.BLACK, PieceType.CHECKER);
                }

            }
        }

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < board.sizeX; j++)
            {
                if (board.Fields[j][i].color == Piece_color.BLACK)
                {
                    board.Fields[j][i].piece = new Piece(Piece_color.WHITE, PieceType.CHECKER);
                }

            }
        }

        board.whiteTurn = true;
    }


    /***
     * Gets current board state of the game
     * @return Board
     */
    public Board getBoard(){
        return this.board;
    }




}