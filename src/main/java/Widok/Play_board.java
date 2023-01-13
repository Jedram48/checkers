package Widok;


import Model.Board;
import Model.Piece_color;
import Model.Field;
import Network.Client;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;


public class Play_board {

    private Client connection;
    TilePane pane;
    Black_square[][] blacksquares = new Black_square[8][4];
    private Black_square selected;
    private boolean white;
    private Board board;
    Play_board() {
        try {
            this.connection = new Client();
            this.board = this.connection.loadBoard();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.pane = new TilePane();
        this.pane.setPrefColumns(8);
        this.pane.setPrefRows(8);
        this.white = board.isWhite();
        genBoard(board);
    }

    void setEvent(Black_square blacksquare){
        blacksquare.setOnMouseClicked(event -> {
            if (blacksquare.isOccupied() && this.selected == null) {
                if(white && blacksquare.getColor() == Color.WHITE){
                    this.selected = blacksquare;
                }
                else if(!white &&blacksquare.getColor() == Color.BLACK){
                    this.selected = blacksquare;
                }
            } else if (this.selected != null) {

                Field[] fields = new Field[2];
                fields[0] = this.board.getFields()[this.selected.row][this.selected.col];
                fields[1] = this.board.getFields()[blacksquare.row][blacksquare.col];
                boolean valid = false;
                try {
                    this.connection.sendMoves(fields);
                    valid = this.connection.isValid();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Boolean.toString(valid));
                if(valid){
                    blacksquare.setCircle(selected.getColor());
                    selected.removeCircle();
                }

                this.selected = null;
            }
        });
    }

    void genBoard(Board board){
        int x = board.getSizeX();
        int y = board.getSizeY();
        Field[][] fields = board.getFields();

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                Field field = fields[j][i];


                if(field.getColor() == Piece_color.BLACK) {
                    Black_square black = null;

                    if(field.getPieceColor() == null){black = new Black_square(field.getX(), field.getY());}
                    else{
                        if(field.getPieceColor() == Piece_color.BLACK){
                            black = new Black_square(field.getX(), field.getY(), Color.BLACK);
                        }
                        else if(field.getPieceColor() == Piece_color.WHITE){
                            black = new Black_square(field.getX(), field.getY(), Color.WHITE);
                        }
                    }
                    setEvent(black);
                    this.pane.getChildren().add(black);
                }
                else if(field.getColor() == Piece_color.WHITE){
                    Rectangle white = new Rectangle(100,100, Color.WHITE);
                    this.pane.getChildren().add(white);
                }
            }
        }
    }


    TilePane getBoard(){
        return this.pane;
    }

}


