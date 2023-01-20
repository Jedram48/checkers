package Model;

import java.io.Serializable;

public class Piece implements Serializable {
    Piece_color color;
    PieceType pieceType;

    /***
     * Create unite of checker or king
     * @param piececolor color of unite
     * @param pieceType type of unite
     */
    public Piece(Piece_color piececolor, PieceType pieceType)
    {
        this.color = piececolor;
        this.pieceType = pieceType;
    }
}
