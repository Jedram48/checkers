package Model;

import java.io.Serializable;

public class Field implements Serializable {
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

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Piece_color getPieceColor(){
        return this.piece.piececolor;
    }
}