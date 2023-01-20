package Model;

import java.io.Serializable;

public class Field implements Serializable {
    Piece piece;
    Piece_color color;

    int x;
    int y;


    /***
     * Creates new field without checker
     * @param piececolor color of field
     * @param x columnt of field
     * @param y row of field
     */
    public Field(Piece_color piececolor, int x, int y)
    {
        this.color = piececolor;
        this.piece = null;
        this.x = x;
        this.y = y;
    }

    /***
     * Gets color of field
     * @return Piece_color
     */
    public Piece_color getColor(){
        return this.color;
    }

    /***
     * Gets type of unite on field if unite is on field
     * @return PieceType
     */
    public PieceType getType(){
        if(piece!=null)return this.piece.pieceType;
        else return null;
    }

    /***
     * Gets column of field
     * @return Integer
     */
    public int getX(){
        return this.x;
    }

    /***
     * Gets row of field
     * @return Integer
     */
    public int getY(){
        return this.y;
    }

    /***
     * Gets color of unite of field if unite is on field
     * @return Piece_color
     */
    public Piece_color getPieceColor(){
        if(this.piece == null){return null;}
        else{return this.piece.color;}
    }
}