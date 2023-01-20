package Model;

import java.io.Serializable;


public class Board implements Serializable {

    Field[][] Fields;
    int sizeX;
    int sizeY;
    boolean A1isWhite;
    public boolean whiteTurn;
    boolean gameIsOn;


    /***
     * Creates new board with given size
     * @param sizeX columns
     * @param sizeY rows
     * @param A1isWhite first field is white
     */
    public Board(int sizeX, int sizeY, boolean A1isWhite)
    {
        this.Fields = new Field[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.A1isWhite = A1isWhite;
        giveColorsToFields(A1isWhite);
        gameIsOn = true;
    }


    /***
     * Displays current game state to terminal
     */
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

    /***
     * Generates grid for board based on board size
     * @param A1isWhite first field is white
     */
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

    /***
     * Check if field with given position exists
     * @param x column
     * @param y row
     * @return boolean
     */
    public boolean validIndex(int x, int y)
    {
        if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) return false;
        else return true;
    }

    /***
     * Calculate amount of fields between start position and end position
     * @param startField start position
     * @param endField end position
     * @return Integer
     */
    public int distance(Field startField, Field endField)

    {
        if(Math.abs(startField.x - endField.x) != Math.abs(startField.y - endField.y)) return -1;
        else return Math.abs(startField.x - endField.x);
    }

    /***
     * Gets number of columns on board
     * @return Integer
     */
    public int getSizeX() {
        return this.sizeX;
    }

    /***
     * Gets number of rows on board
     * @return Integer
     */
    public int getSizeY(){
        return this.sizeY;
    }

    /***
     * Gets array with all fields on board
     * @return boolean
     */
    public Field[][] getFields(){
        return this.Fields;
    }

    /***
     * Get current status of game
     * @return boolean
     */
    public boolean gameOver(){
        return !this.gameIsOn;
    }

}