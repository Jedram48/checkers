package Widok;


import Model.*;
import Network.Client;
import javafx.application.Platform;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class Play_board extends Thread{

    private Client connection;
    private TilePane pane;
    private Black_square[][] grid;
    private Black_square selected;
    private Game game;
    private Board board;

    /***
     * Connects with server and creates pane with fields and checkers on it based board state received from server
     */
    public Play_board() {
        try {
            this.connection = new Client();
            this.board = this.connection.loadBoard();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(connection.white)System.out.println("white");
        else{System.out.println("black");}

        this.pane = new TilePane();
        this.pane.setPrefColumns(board.getSizeX());
        this.pane.setPrefRows(board.getSizeY());
        genBoard(board);
        start();
    }

    /***
     * Thread that receives refreshed board from server
     */
    @Override
    public void run(){
        while(this.connection.isConnected()){
            try {
                this.board = this.connection.loadBoard();
                if(this.board.gameOver()){
                    System.out.println("Game over");
                }
                else{refreshBoard();
                    board.displayGamestate();
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Connection lost");
                return;
            }
            System.out.println("Board received");
        }
    }

    /***
     * Set event handler on field to mark selected field and perform move
     * @param blacksquare field for which event will be set
     */
    private void setEvent(Black_square blacksquare){
        blacksquare.setOnMouseClicked(event -> {
            if (blacksquare.isOccupied() && this.selected == null) {
                this.selected = blacksquare;
            } else if (this.selected != null) {
                    if((connection.white&& board.whiteTurn)||(!connection.white&&!board.whiteTurn)){
                    Field[] fields = new Field[2];
                    fields[0] = this.board.getFields()[this.selected.row][this.selected.col];
                    fields[1] = this.board.getFields()[blacksquare.row][blacksquare.col];
                    try {
                        this.connection.sendMoves(fields);
                        System.out.println(fields[0].getX() + " " + fields[0].getY() + " " + fields[1].getX() + " " + fields[1].getY());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{System.out.println("Not your turn");}
                this.selected = null;
            }
        });
    }

    /***
     * Mages changes in board state based on board received from server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void refreshBoard() throws IOException, ClassNotFoundException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int x = board.getSizeX();
                int y = board.getSizeY();
                Field[][] fields = board.getFields();

                for(int i = 0; i < y; i++) {
                    for (int j = 0; j < x; j++) {
                        Field field = fields[j][i];

                        if(field.getColor() == Piece_color.BLACK){
                            if(!(field.getPieceColor()==grid[j][i].getPieceColor())){
                                grid[j][i].setCircle(field.getPieceColor());
                                try{if(field.getType() == PieceType.KING)grid[j][i].changeToKing();}
                                catch (NullPointerException ignore){}
                            }
                        }
                    }
                }
            }
        });
    }

    /***
     * Generates board based on board state given in parameter
     * @param board board state that will be generated
     */
    private void genBoard(Board board){
        int x = board.getSizeX();
        int y = board.getSizeY();

        this.grid = new Black_square[x][y];
        Field[][] fields = board.getFields();

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                Field field = fields[j][i];

                if(field.getColor() == Piece_color.BLACK) {
                    Black_square black = new Black_square(field.getX(), field.getY(), field.getPieceColor());

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

    /***
     * Closes connection with server
     */
    public void closeConnection(){
        try {
            this.connection.close();
            System.out.println("Socket closed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Gets pane with received board state
     * @return
     */
    public TilePane getBoard(){
        return this.pane;
    }

    /***
     * Gets side which related to board
     * @return
     */
    public boolean isWhite(){
        return connection.white;
    }

}


