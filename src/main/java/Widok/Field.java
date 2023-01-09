package Widok;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class Field extends StackPane {

    public int row;
    public int col;
    private final int size = 100;
    private Rectangle rect = new Rectangle(size,size,Color.BLACK);
    private Circle circle;
    boolean occupied = false;

    Field(int x, int y){
        super();
        this.row = x;
        this.col = y;
        this.getChildren().add(rect);
    }
    Field(int x, int y, Color color){
        super();
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

    public void setCircle(Color color){
        this.circle = new Circle((int)(size / 2) - 5, color);

        if(color == Color.WHITE){this.circle.setStroke(Color.BLACK);}
        else if(color == Color.BLACK){this.circle.setStroke(Color.WHITE);}

        this.getChildren().add(this.circle);
    }

    public void removeCircle(){
        this.getChildren().remove(this.circle);
        this.circle = null;
    }

    public void switchOccupation(){
        this.occupied = !this.occupied;
    }

    public boolean isOccupied(){
        return occupied;
    }

/*    public void handle(boolean[][] isOccupied) {
        setOnMouseClicked(event -> {
            if (circle != null) {
                // If the square has a circle, select it
                selectedSquare = this;
            } else if (selectedSquare != null) {
                // If a square is already selected, try to move its circle to this square
                if (!isOccupied[row][col]) {
                    // Remove the circle from the selected square and add it to this square
                    setCircle(selectedSquare.getColor());
                    selectedSquare.removeCircle();
                    // Update the isOccupied array
                    isOccupied[selectedSquare.row][selectedSquare.col] = false;
                    isOccupied[row][col] = true;
                    // Deselect the selected square
                    selectedSquare = null;
                }
            }
        });
    }

    private static Field selectedSquare = null;
*/
}