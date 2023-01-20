package Model;

import java.io.Serializable;

/**
 * Klasa Board reprezentuje aktualny stan gry.
 */
public class Board implements Serializable {
    /**
     *
     * @param sizeX szerokosc planszy
     * @param sizeY wysokosc planszy
     * @param A1isWhite jesli = true to uklad przekatnych bedzie taki, ze pole w lewym dolnym rogu pole jest biale.
     *                  jesli = false to uklad przekatnych bedzie taki, ze pole w lewym dolnym rogu pole jest czarne.
     * @param gameIsOn informuje o stanie gry.
     */

    Field[][] Fields;
    int sizeX;
    int sizeY;
    boolean A1isWhite;
    public boolean whiteTurn;
    boolean gameIsOn;


    public Board(int sizeX, int sizeY, boolean A1isWhite)
    {
        this.Fields = new Field[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.A1isWhite = A1isWhite;
        giveColorsToFields(A1isWhite);
        gameIsOn = true;
    }


    public void displayGamestate()
    {
        for(int i = sizeY -1 ; i >= 0; i--)
        {
            for(int j = 0; j < sizeX; j++)
            {
                if (Fields[j][i].piece == null) System.out.print("0 ");
                else if (Fields[j][i].piece.color == Piece_color.WHITE)
                {
                    if (Fields[j][i].piece.pieceType == PieceType.CHECKER)
                        System.out.print("1 ");
                    else
                        System.out.print("3 ");
                }
                else
                {
                    if (Fields[j][i].piece.pieceType == PieceType.CHECKER)
                        System.out.print("2 ");
                    else
                        System.out.print("4 ");
                }
            }
            System.out.println();
        }
        System.out.println("White turn: " + whiteTurn);
    }

    public void giveColorsToFields(boolean A1isWhite)
    {
        if(A1isWhite)
        {
            for (int i = 0 ; i < sizeY; i++)
            {
                for ( int j = 0 ; j < sizeX; j++)
                {
                    if ((i+j)%2 == 0) Fields[j][i] = new Field(Piece_color.WHITE,j , i);
                    else Fields[j][i] = new Field(Piece_color.BLACK, j, i);
                }
            }
        }
        else
        {
            for (int i = 0 ; i < sizeY; i++)
            {
                for ( int j = 0 ; j < sizeX; j++)
                {
                    if ((i+j)%2 == 0) Fields[j][i] = new Field(Piece_color.BLACK, j, i);
                    else Fields[j][i] = new Field(Piece_color.WHITE, j, i);
                }
            }
        }
    }

    public boolean validIndex(int x, int y)
    /**
     * sprawdza czy na planszy istnieje pole o wspolrzednych x i y
     */
    {
        if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) return false;
        else return true;
    }

    public int distance(Field startField, Field endField)
    /**
     * zwraca ilosc pol pomiedzy dwoma polami na przekatnej
     */
    {
        if(Math.abs(startField.x - endField.x) != Math.abs(startField.y - endField.y)) return -1;
        else return Math.abs(startField.x - endField.x);
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY(){
        return this.sizeY;
    }

    public Field[][] getFields(){
        return this.Fields;
    }

    public boolean gameOver(){
        return !this.gameIsOn;
    }

}