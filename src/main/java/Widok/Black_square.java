package Widok;

import Model.Piece_color;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class Black_square extends StackPane {

    public int row;
    public int col;
    private final int size = 100;
    private final Rectangle rect = new Rectangle(size,size,Color.BLACK);
    private Circle circle;
    boolean occupied = false;
    private Piece_color color;

    Black_square(int x, int y){
        super();
        this.row = x;
        this.col = y;
        this.getChildren().add(rect);
    }
    Black_square(int x, int y, Piece_color color){
        super();
        this.row = x;
        this.col = y;

        this.getChildren().add(rect);
        setCircle(color);
        this.occupied = true;

    }

    public int getPosX(){
        return this.row;
    }

    public int getPosY(){
        return this.col;
    }

    public void setColor(Color color){
        this.circle.setFill(color);
    }
    Color getColor(){
        return (Color) this.circle.getFill();
    }
    Piece_color getPieceColor(){
        return this.color;
    }

    public void setCircle(Piece_color pieceColor){
        Color color = null;

        if(pieceColor == Piece_color.BLACK)color = Color.BLACK;
        else if(pieceColor == Piece_color.WHITE)color = Color.WHITE;

        if(color!=null){
            this.circle = new Circle((int)(size / 2) - 5, color);

            if(color == Color.WHITE){this.circle.setStroke(Color.BLACK);}
            else if(color == Color.BLACK){this.circle.setStroke(Color.WHITE);}

            this.getChildren().add(this.circle);
            this.occupied = true;
        }
        else{removeCircle();}

        this.color = pieceColor;

    }

    public void removeCircle(){
        if(this.circle!=null){
            this.getChildren().remove(this.circle);
            this.circle = null;
            this.occupied = false;
        }
    }

    public void changeToKing(){
        this.circle.setStroke(Color.RED);
        this.circle.setStrokeWidth(10);
    }

    public void switchOccupation(){
        this.occupied = !this.occupied;
    }

    public boolean isOccupied(){
        return occupied;
    }



}
