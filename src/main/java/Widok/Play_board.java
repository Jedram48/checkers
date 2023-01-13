package Widok;


import Model.Board;
import Model.Piece_color;
import Model.Field;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Play_board {


    TilePane pane;
    Black_square[][] blacksquares = new Black_square[8][4];
    boolean[][] isOccupied = new boolean[8][8];
    private Black_square selected;

    private Board board;
    Play_board(Board board) {
        this.pane = new TilePane();
        this.pane.setPrefColumns(8);
        this.pane.setPrefRows(8);
        this.board = board;
        genBoard(board);
    }

    void setEvent(Black_square blacksquare){
        blacksquare.setOnMouseClicked(event -> {
            if (blacksquare.isOccupied() && this.selected == null) {
                this.selected = blacksquare;
            } else if (this.selected != null) {
                String req;

                if (!isOccupied[blacksquare.getPosX()][blacksquare.getPosY()]) {
                    req = "MOVE ";
                }
                else{
                    req = "BEAT ";
                }

                req = req.concat((this.selected.getPosX())+" ");
                req = req.concat((this.selected.getPosY())+" ");
                req = req.concat((blacksquare.getPosX())+" ");
                req = req.concat((blacksquare.getPosY())+" ");


            }
        });
    }

    void genBoard(Board board){
        int x = board.getSizeX();
        int y = board.getSizeY();
        Field[][] fields = board.getFields();

        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                Field field = fields[i][j];
                Color color = null;

                if(field.getPieceColor() == Piece_color.BLACK){
                    color = Color.BLACK;
                } else if (field.getPieceColor() == Piece_color.WHITE) {
                    color = Color.WHITE;
                }

                if(field.getColor() == Piece_color.BLACK) {
                    Black_square black = new Black_square(field.getX(), field.getY(), color);
                    this.pane.getChildren().add(black);
                }
                else if(field.getColor() == Piece_color.WHITE){
                    Rectangle white = new Rectangle(100,100, color);
                    this.pane.getChildren().add(white);
                }
            }
        }
    }


    TilePane getBoard(){
        return this.pane;
    }

}


