package Model;

public class Piece {

    /**
     * zmienna pieceType informuje o tym czy figura jest zwyklym pionkiem(CHECKER) czy krolem(KING)
     */
    Color color;
    PieceType pieceType;

    public Piece(Color color, PieceType pieceType)
    {
        this.color = color;
        this.pieceType = pieceType;
    }
}
