package Model;

public class Game {

    Board board;
    boolean gameIsOn;
    Rules rules;

    public Game()
    {
        this.rules = new Rules();
        this.board = new Board(8,8,true);
        this.gameIsOn = true;
        testPosition();
    }


    public void move(Field startField, Field endField)
    {
        if(rules.isLegal(board, startField, endField))
        {
            if (board.distance(startField, endField) == 1)
            {
                endField.piece = startField.piece;
                startField.piece = null;
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
                endField.piece = startField.piece;
                rules.getFieldOfEnemyPieceOnPath(startField,endField,board).piece = null;
            }
        }
        else
        {
            System.out.println("BAD MOVE");
            return;
        }

        if (!rules.CHECKERCanAtack(endField, board) && !rules.KINGcanAttack(endField, board))
        {
            board.whiteTurn = !board.whiteTurn;
            if ( endField.y == board.sizeY-1 &&
                    endField.piece.pieceType == PieceType.CHECKER &&
                    endField.piece.color == Color.WHITE) endField.piece.pieceType = PieceType.KING;
            else if ( endField.y == 0 &&
                    endField.piece.pieceType == PieceType.CHECKER &&
                    endField.piece.color == Color.BLACK) endField.piece.pieceType = PieceType.KING;
        }

        if (rules.didBlackLost(board))
        {
            gameIsOn = false;
            System.out.println("WHITE WIN!");
        }
        else if (rules.didWhiteLost(board))
        {
            gameIsOn = false;
            System.out.println("BLACK WIN!");
        }

    }

    public void startingPosition()
    {
        for(int i = board.sizeY - 3; i < board.sizeY; i++)
        {
            for(int j = 0; j < board.sizeX; j++)
            {
                if (board.Fields[j][i].color == Color.BLACK)
                {
                    board.Fields[j][i].piece = new Piece(Color.BLACK, PieceType.CHECKER);
                }

            }
        }

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < board.sizeX; j++)
            {
                if (board.Fields[j][i].color == Color.BLACK)
                {
                    board.Fields[j][i].piece = new Piece(Color.WHITE, PieceType.CHECKER);
                }

            }
        }

        board.whiteTurn = true;
    }

    public void testPosition()
    {
       // board.Fields[0][0].piece = new Piece(Color.WHITE, PieceType.KING);
        board.Fields[5][5].piece = new Piece(Color.WHITE, PieceType.CHECKER);
        board.Fields[6][6].piece = new Piece(Color.BLACK, PieceType.CHECKER);

        board.whiteTurn = true;
    }




}