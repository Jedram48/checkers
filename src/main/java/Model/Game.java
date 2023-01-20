package Model;

/**
 * Klasa game laczy metody klasy rules z klasa Board
 */
public class Game {

    public Board board;
    public boolean gameIsOn;
    public Rules rules;

    /**
     * Klasa rules uwzglednia zasady gry i pozwala na wykonywanie okreslonych ruchow.
     */
    public Game()
    {
        this.rules = new Rules();
        this.board = new Board(8,8,true);
        this.gameIsOn = true;
        startingPosition();
    }
    public void displayGameState()
    {
        board.displayGamestate();
    }

    public boolean isLegalInString(int x, int y, int x2, int y2)
    /**
     * Wykonuje metode isLegal() z klasy rules, przekazujac odpowiednie liczby x,y,x2,y2 jako wspolrzedne pol.
     */
    {
        return rules.isLegal(board, board.Fields[x][y], board.Fields[x2][y2]);
    }

    public void moveInString(int x, int y, int x2, int y2)
    /**
     * Wykonuje metode move(), przekazujac odpowiednie liczby x,y,x2,x2 jako wspolrzedne pol.
     */
    {
        move(board.Fields[x][y], board.Fields[x2][y2]);
    }

    public boolean isLegal(Field startField, Field endField){
        return rules.isLegal(board, startField, endField);
    }
    public void move(Field startField, Field endField)
    /**
     * Sprawdza czy ruch z pola startField na pole endField jest zgodny z zasadami.
     * Jesli tak - wykonuje ruch zmieniajac stany poszczegolnych pol.
     * Jesli nie - wyswietla komunikat "BAD MOVE"
     * Jesli po wykonaniu ruchu nie ma kolejnej mozliwosci zbicia to zmienia wartosc zmiennej whiteTurn
     * Jesli metoda rules.didWhiteLost(Board board) lub rules.didBlackLost(Board board) zwroci true to parametr gameIsOn zmienia wartosc na false
     */
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

    public void changeToKing(Field endField){
        if ( endField.y == board.sizeY-1 &&
                endField.piece.pieceType == PieceType.CHECKER &&
                endField.piece.color == Piece_color.WHITE) endField.piece.pieceType = PieceType.KING;
        else if ( endField.y == 0 &&
                endField.piece.pieceType == PieceType.CHECKER &&
                endField.piece.color == Piece_color.BLACK) endField.piece.pieceType = PieceType.KING;
    }

    public void startingPosition()
    /**
     * ustawia pionki na szachownicy w pozycji startowej
     */
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


    public Board getBoard(){
        return this.board;
    }




}