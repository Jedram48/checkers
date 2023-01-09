package Model;

import Widok.Field;

import static Model.Squares.WHITE;
import static Model.Squares.BLACK;
import static Model.Squares.BLACK_P1;
import static Model.Squares.BLACK_P2;

public class Logic {

    private static final Squares[][] board = new Squares[8][8];

    public Logic(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if((i+j)%2==0){
                    board[i][j] = WHITE;
                }
                else{
                    if(i<3){
                        board[i][j] = BLACK_P1;
                    }
                    else if(i>4){
                        board[i][j] = BLACK_P2;
                    }
                    else{
                        board[i][j] = BLACK;
                    }
                }
            }
        }
    }

    public boolean move(int startRow, int startCol, int endRow, int endCol){
        return Math.abs(startRow - endRow) == 1 && Math.abs(startCol - endCol) == 1;
    }

}
