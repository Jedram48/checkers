package Widok;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class Board {

    boolean in_action = false;
    int[] pos = new int[2];
    Color color;
    TilePane board;
    Field[][] fields = new Field[8][4];
    boolean[][] isOccupied = new boolean[8][4];
    Board() {
        this.board = new TilePane();
        this.board.setPrefColumns(8);
        this.board.setPrefRows(8);
        genBoard();
    }

    void setEvent(Field field){
        field.handle(fields, isOccupied);
    }

    void genBoard(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if((i+j)%2==0){
                    Rectangle white = new Rectangle(100,100, Color.WHITE);
                    this.board.getChildren().add(white);
                }
                else{
                    Field black;
                    if(i<3){
                        black = new Field(i,j,Color.BLACK);
                        this.isOccupied[i][(j-1)/2] = true;
                    }
                    else if(i>4){
                        black = new Field(i,j,Color.WHITE);
                        this.isOccupied[i][(j-1)/2] = true;
                    }
                    else{
                        black = new Field(i,j);
                        this.isOccupied[i][(j-1)/2] = false;
                    }
                    setEvent(black);
                    this.board.getChildren().add(black);
                    this.fields[i][(j-1)/2] = black;
                }
            }
        }
    }


    TilePane getBoard(){
        return this.board;
    }

}


