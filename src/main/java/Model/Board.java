package Model;

public class Board {
    Field[][] Fields;
    int sizeX;
    int sizeY;
    boolean A1isWhite;

    public Board(int sizeX, int sizeY, boolean A1isWhite)
    {
        Fields = new Field[sizeX][sizeY];
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

    public void giveColorsToFields(boolean A1isWhite)
    {
        if(A1isWhite)
        {
            for (int i = 0 ; i < sizeY; i++)
            {
                for ( int j = 0 ; j < sizeX; j++)
                {
                    if ((i+j)%2 == 0) Fields[j][i] = new Field(Color.WHITE);
                    else Fields[j][i] = new Field(Color.BLACK);
                }
            }
        }
        else
        {
            for (int i = 0 ; i < sizeY; i++)
            {
                for ( int j = 0 ; j < sizeX; j++)
                {
                    if ((i+j)%2 == 0) Fields[j][i] = new Field(Color.BLACK);
                    else Fields[j][i] = new Field(Color.WHITE);
                }
            }
        }
    }



}
