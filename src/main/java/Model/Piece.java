package Model;

import java.io.Serializable;

public class Piece implements Serializable {
    Piece_color color;
    PieceType pieceType;

    public Piece(Piece_color piececolor, PieceType pieceType)
    {
        this.color = piececolor;
        this.pieceType = pieceType;
    }
}
