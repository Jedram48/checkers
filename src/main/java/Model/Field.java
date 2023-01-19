package Model;

import java.io.Serializable;

public class Field implements Serializable {
    /**
     * zmienna piece informuje o tym jaka fogura stoi na polu ( jesli piece == null to pole jest puste)
     * x i y to wspolrzedne pola
     */
    Piece piece;
    Piece_color color;

    int x;
    int y;


    public Field(Piece_color piececolor, int x, int y)
    {
        this.color = piececolor;
        this.piece = null;
        this.x = x;
        this.y = y;
    }

    public Piece_color getColor(){
        return this.color;
    }
    public PieceType getType(){return this.piece.pieceType;}

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Piece_color getPieceColor(){
        if(this.piece == null){return null;}
        else{return this.piece.color;}
    }
}