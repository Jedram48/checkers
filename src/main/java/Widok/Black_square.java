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

    /***
     * Creates black field with or without checker
     * @param x horizontal position of field on board
     * @param y vertical position of field on board
     * @param color color of checker on field
     */
    Black_square(int x, int y, Piece_color color){
        super();
        this.row = x;
        this.col = y;

        this.getChildren().add(rect);
        setCircle(color);
        this.occupied = true;

    }

    /***
     * Gets the value of column
     * @return Returns horizontal position of field
     */
    public int getPosX(){
        return this.row;
    }

    /***
     * Gets the value of row
     * @return Returns vertical position of field
     */
    public int getPosY(){
        return this.col;
    }

    /***
     * Set color of checker
     * @param color new color of checker
     */
    public void setColor(Color color){
        this.circle.setFill(color);
    }

    /***
     * Gets color of checker
     * @return Returns instance of class Color
     */
    Color getColor(){
        return (Color) this.circle.getFill();
    }

    /***
     * Gets color of checker
     * @return Return instance of enum class Piece_color
     */
    Piece_color getPieceColor(){
        return this.color;
    }

    /***
     * Set a new circle on field
     * @param pieceColor Color of checker that will be on field
     */
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

    /***
     * Removes checker from field
     */
    public void removeCircle(){
        if(this.circle!=null){
            this.getChildren().remove(this.circle);
            this.circle = null;
            this.occupied = false;
        }
    }

    /***
     * Changes common checker to king
     */
    public void changeToKing(){
        this.circle.setStroke(Color.RED);
        this.circle.setStrokeWidth(10);
    }

    /***
     * Switches occupation of field
     */
    public void switchOccupation(){
        this.occupied = !this.occupied;
    }

    /***
     * Get field occupation state
     * @return boolean state of field occupation
     */
    public boolean isOccupied(){
        return occupied;
    }



}
