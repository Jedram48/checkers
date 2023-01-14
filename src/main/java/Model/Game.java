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
    /**
     * ustawia pionki na szachownicy w pozycji startowej
     */
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