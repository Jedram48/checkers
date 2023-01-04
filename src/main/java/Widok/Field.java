package Widok;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.event.MouseEvent;

public class Field extends StackPane {

    int row;
    int col;
    int size = 100;
    Rectangle rect = new Rectangle(size,size,Color.BLACK);
    Circle circle;
    boolean occupated = false;

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
        this.occupated = true;

    }

    int getPosX(){
        return this.row;
    }

    int getPosY(){
        return this.col;
    }

    void setColor(Color color){
        this.circle.setFill(color);
    }
    Color getColor(){
        return (Color) this.circle.getFill();
    }

    void setCircle(Color color){
        this.circle = new Circle(45, color);

        if(color == Color.WHITE){this.circle.setStroke(Color.BLACK);}
        else if(color == Color.BLACK){this.circle.setStroke(Color.WHITE);}

        this.getChildren().add(this.circle);
    }

    void removeCircle(){
        this.getChildren().remove(this.circle);
        this.circle = null;
    }

    void switchOccupation(){
        this.occupated = !this.occupated;
    }

    boolean isOccupated(){
        return occupated;
    }

    public void handle(Field[][] board, boolean[][] isOccupied) {
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

}
