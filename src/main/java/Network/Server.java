package Network;

import Model.Board;
import Model.Field;
import Model.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    private ServerSocket serverSocket;
    Socket white;
    ObjectInputStream inW;
    ObjectOutputStream outW;
    Socket black;
    ObjectInputStream inB;
    ObjectOutputStream outB;

    private boolean requestedW = true;
    private boolean requestedB = true;
    private Game game;
    private boolean whiteTurn;

    /***
     * Create new server socket and wait for players to connect
     * @throws IOException
     */
    public Server() throws IOException
    {
        this.serverSocket = new ServerSocket(1234);
        System.out.println("Waiting for players to connect...");
        this.white = serverSocket.accept();
        System.out.println("Player 1 connected");
        this.black = serverSocket.accept();
        System.out.println("Player 2 connected");

        outW = new ObjectOutputStream(white.getOutputStream());
        outB = new ObjectOutputStream(black.getOutputStream());
        inW = new ObjectInputStream(white.getInputStream());
        inB = new ObjectInputStream(black.getInputStream());

        System.out.println("Connection finished");
    }

    /***
     * Start the game and send newly generated board to players
     * @throws IOException
     */
    public void startTheGame() throws IOException {
        this.game = new Game();
        this.whiteTurn = true;
        this.game.displayGameState();

        this.outW.writeBoolean(true);
        this.outW.flush();
        this.outW.reset();

        this.outW.writeObject(boardState());
        this.outW.flush();
        this.outW.reset();

        this.outB.writeBoolean(false);
        this.outB.flush();
        this.outB.reset();

        this.outB.writeObject(boardState());
        this.outB.flush();
        this.outB.reset();

        listenToWhite();
        listenToBlack();
    }

    /***
     * Gets current board state of the game
     * @return
     */
    private Board boardState(){
        return game.getBoard();
    }

    /***
     * Receive and send all changes on board from player on white side
     */
    public void listenToWhite()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    while(true)
                    {
                        Field[] fields = (Field[]) inW.readObject();

                        System.out.println(fields[0].getX() + " " + fields[0].getY() + " " + fields[1].getX() + " " + fields[1].getY());

                        if(whiteTurn){
                            System.out.println("check");
                            if(game.isLegal(fields[0], fields[1])){
                                System.out.println("making move");
                                game.moveInString(fields[0].getX(), fields[0].getY(), fields[1].getX(), fields[1].getY());
                                game.displayGameState();
                                whiteTurn = game.getBoard().whiteTurn;
                            }
                        }
                        broadcastGameState();
                    }
                } catch (IOException e)
                {
                    System.err.println("Problem with White's connection...");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }

    /***
     * Receive and send all changes on board from player on black side
     */
    public void listenToBlack()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    while(true)
                    {
                        Field[] fields = (Field[]) inB.readObject();
                        System.out.println(fields[0].getX() + " " + fields[0].getY() + " " + fields[1].getX() + " " + fields[1].getY());

                        if(!whiteTurn){
                            System.out.println("check");
                            if(game.isLegal(fields[0], fields[1])){
                                System.out.println("making move");
                                game.moveInString(fields[0].getX(), fields[0].getY(), fields[1].getX(), fields[1].getY());
                                game.displayGameState();
                                whiteTurn = game.getBoard().whiteTurn;
                            }
                        }
                        broadcastGameState();
                    }
                } catch (IOException e)
                {
                    System.err.println("Problem with Black's connection...");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }

    /***
     * Send board state to both players
     * @throws IOException
     */
    public void broadcastGameState() throws IOException
    {
        outW.writeObject(boardState());
        outB.writeObject(boardState());

        outW.flush();
        outW.reset();

        outB.flush();
        outB.reset();
    }



    public static void main(String[] args) throws IOException
    {
        Server server = new Server();
        server.startTheGame();

    }
}
