package Widok;


import Network.ClientConnection;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;


public class Board {

    ClientConnection client;
    TilePane board;
    Field[][] fields = new Field[8][4];
    boolean[][] isOccupied = new boolean[8][8];
    private Field selected;
    Board() {
        this.board = new TilePane();
        this.board.setPrefColumns(8);
        this.board.setPrefRows(8);
        genBoard();
        try {
            this.client = new ClientConnection();
            this.client.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setEvent(Field field){
        field.setOnMouseClicked(event -> {
            if (field.isOccupied() && this.selected == null) {
                this.selected = field;
            } else if (this.selected != null) {
                String req;

                if (!isOccupied[field.getPosX()][field.getPosY()]) {
                    req = "MOVE ";
                }
                else{
                    req = "BEAT ";
                }

                req = req.concat((this.selected.getPosX())+" ");
                req = req.concat((this.selected.getPosY())+" ");
                req = req.concat((field.getPosX())+" ");
                req = req.concat((field.getPosY())+" ");

                client.sendRequest(req);
                this.selected = null;
                try {
                    System.out.println(client.massage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
                        this.isOccupied[i][j] = true;
                    }
                    else if(i>4){
                        black = new Field(i,j,Color.WHITE);
                        this.isOccupied[i][j] = true;
                    }
                    else{
                        black = new Field(i,j);
                        this.isOccupied[i][j] = false;
                    }
                    setEvent(black);
                    this.board.getChildren().add(black);
//                    this.fields[i][j] = black;
                }
            }
        }
    }


    TilePane getBoard(){
        return this.board;
    }

}


