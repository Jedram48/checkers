package Model;

import java.io.Serializable;

public class Board implements Serializable {
    Field[][] Fields;
    int sizeX;
    int sizeY;
    boolean A1isWhite;
    boolean whiteTurn;


    public Board(int sizeX, int sizeY, boolean A1isWhite)
    {
        this.Fields = new Field[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.A1isWhite = A1isWhite;
        giveColorsToFields(A1isWhite);
    }

    public void displayBoardColors()
    {
        for(int i = sizeY -1 ; i >= 0; i--)
        {
            for(int j = 0; j < sizeX; j++)
            {
                System.out.print(Fields[j][i].color + " ");
            }
            System.out.println();
        }
    }

    public void displayGamestate()
    {
        for(int i = sizeY -1 ; i >= 0; i--)
        {
            for(int j = 0; j < sizeX; j++)
            {
                if (Fields[j][i].piece == null) System.out.print("0 ");
                else if ( Fields[j][i].piece.piececolor == Piece_color.WHITE) System.out.print("1 ");
                else if ( Fields[j][i].piece.piececolor == Piece_color.BLACK) System.out.print("2 ");

            }
            System.out.println();
        }
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
    {
        if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) return false;
        else return true;
    }

    public int distance(Field startField, Field endField)
    {
        if(Math.abs(startField.x - endField.x) != Math.abs(startField.y - endField.y)) return -1;
        else return Math.abs(startField.x - endField.x);
    }

    public int getSizeX(){
        return this.sizeX;
    }

    public int getSizeY(){
        return this.sizeY;
    }

    public Field[][] getFields(){
        return this.Fields;
    }
}