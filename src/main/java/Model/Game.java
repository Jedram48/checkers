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
        startingPosition();
    }


    public void move(Field startField, Field endField)
    {
        if(rules.isLegal(board, startField, endField))
        {
            if (board.distance(startField, endField) == 1)
            {
                endField.piece = startField.piece;
                startField.piece = null;

            }
            else if (board.distance(startField, endField) == 2)
            {
                endField.piece = startField.piece;
                startField.piece = null;
                int middleFieldX = (startField.x - endField.x)/2 + endField.x;
                int middleFieldY = (startField.y - endField.y)/2 + endField.y;
                board.Fields[middleFieldX][middleFieldY].piece = null;
            }
        }

        if (!rules.pieceCanAtack(endField, board))
        {
            board.whiteTurn = !board.whiteTurn;
            if ( endField.y == board.sizeY-1 &&
                    endField.piece.pieceType == PieceType.CHECKER &&
                    endField.piece.piececolor == Piece_color.WHITE) endField.piece.pieceType = PieceType.KING;
            else if ( endField.y == 0 &&
                    endField.piece.pieceType == PieceType.CHECKER &&
                    endField.piece.piececolor == Piece_color.BLACK) endField.piece.pieceType = PieceType.KING;
        }

    }

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



}