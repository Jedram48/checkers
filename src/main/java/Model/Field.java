package Model;

public class Field {
    Piece piece;
    Color color;

    int x;
    int y;


    public Field(Color color, int x, int y)
    {
        this.color = color;
        this.piece = null;
        this.x = x;
        this.y = y;
    }
}