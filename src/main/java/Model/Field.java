package Model;

public class Field {
    /**
     * zmienna piece informuje o tym jaka fogura stoi na polu ( jesli piece == null to pole jest puste)
     * x i y to wspolrzedne pola
     */
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