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

    public Server(ServerSocket serverSocket) throws IOException
    {
        this.serverSocket = serverSocket;
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

    private void close() throws IOException {
        this.outW.close();
        this.outB.close();

        this.inW.close();
        this.inB.close();

        this.white.close();
        this.black.close();

        this.serverSocket.close();
    }

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

    private Board boardState(){
        return game.getBoard();
    }

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


                        if(whiteTurn){
                            if(game.isLegal(fields[0], fields[1])){
                                game.moveInString(fields[0].getX(), fields[0].getY(), fields[1].getX(), fields[1].getY());
                                game.displayGameState();
                            }

                            broadcastGameState();

                            whiteTurn = !whiteTurn;
                        }
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


                        if(!whiteTurn){
                            if(game.isLegal(fields[0], fields[1])){
                                game.moveInString(fields[0].getX(), fields[0].getY(), fields[1].getX(), fields[1].getY());
                                game.displayGameState();
                            }

                            broadcastGameState();

                            whiteTurn = !whiteTurn;
                        }
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
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startTheGame();

    }
}


//Board b = new Board(8,8, true);
//this.outW.writeObject(b);
