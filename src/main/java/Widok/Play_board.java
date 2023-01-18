package Widok;


import Model.Board;
import Model.Piece_color;
import Model.Field;
import Network.Client;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;


public class Play_board extends Thread{

    private Client connection;
    private TilePane pane;
    private Black_square[][] grid;
    private Black_square selected;
    private Board board;
    public Play_board() {
        try {
            this.connection = new Client();
            this.board = this.connection.loadBoard();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.pane = new TilePane();
        this.pane.setPrefColumns(8);
        this.pane.setPrefRows(8);
        genBoard(board);
        start();
    }

    @Override
    public void run(){
        try {
            refreshBoard(this.connection.loadBoard());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEvent(Black_square blacksquare){
        blacksquare.setOnMouseClicked(event -> {
            if (blacksquare.isOccupied() && this.selected == null) {
                this.selected = blacksquare;
            } else if (this.selected != null) {

                 boolean valid = false;

                do{Field[] fields = new Field[2];
                    fields[0] = this.board.getFields()[this.selected.row][this.selected.col];
                    fields[1] = this.board.getFields()[blacksquare.row][blacksquare.col];
                    try {
                        this.connection.sendMoves(fields);
                        valid = this.connection.isValid();
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(valid);
                    }
                while(!valid);

                this.selected = null;
            }
        });
    }

    private void refreshBoard(Board board) throws IOException, ClassNotFoundException {

        board.displayGamestate();
        int x = board.getSizeX();
        int y = board.getSizeY();
        Field[][] fields = board.getFields();

        for(int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Field field = fields[j][i];

                if(field.getColor() == Piece_color.BLACK){
                    if(!(field.getPieceColor()==grid[j][i].getPieceColor())){
                        grid[j][i].setCircle(field.getPieceColor());
                    }
                }
            }
        }
    }

    private void genBoard(Board board){
        int x = board.getSizeX();
        int y = board.getSizeY();

        this.grid = new Black_square[x][y];
        Field[][] fields = board.getFields();

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                Field field = fields[j][i];


                if(field.getColor() == Piece_color.BLACK) {
                    Black_square black = null;

                    if(field.getPieceColor() == null){black = new Black_square(field.getX(), field.getY());}
                    else{
                            black = new Black_square(field.getX(), field.getY(), field.getPieceColor());
                    }
                    setEvent(black);
                    this.pane.getChildren().add(black);
                    this.grid[j][i] = black;
                }
                else if(field.getColor() == Piece_color.WHITE){
                    Rectangle white = new Rectangle(100,100, Color.WHITE);
                    this.pane.getChildren().add(white);
                    this.grid[j][i] = null;
                }
            }
        }
    }


    public TilePane getBoard(){
        return this.pane;
    }

}


