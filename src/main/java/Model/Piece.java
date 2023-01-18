package Model;

import java.io.Serializable;

public class Piece implements Serializable {
    /**
     * zmienna pieceType informuje o tym czy figura jest zwyklym pionkiem(CHECKER) czy krolem(KING)
     */
    Piece_color color;
    PieceType pieceType;

    public Piece(Piece_color piececolor, PieceType pieceType)
    {
        this.color = piececolor;
        this.pieceType = pieceType;
    }
}
