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

    Game game;
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
        this.game.displayGameState();

        this.outW.writeObject(game.getBoard());
        this.outW.flush();
        this.outW.reset();
        this.outB.writeObject(game.getBoard());
        this.outB.flush();
        this.outB.reset();

        listenToWhite();
        listenToBlack();
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
                        int x = fields[0].getX();
                        int y = fields[0].getY();
                        int x2 = fields[1].getX();
                        int y2 = fields[1].getY();

                        if(game.isLegalInString(x,y,x2,y2)) game.moveInString(x,y,x2,y2);
                        outW.writeObject(game.isLegalInString(x,y,x2,y2));
                        outW.flush();
                        outW.reset();
                        System.out.println(Boolean.toString(game.isLegal(fields[0], fields[1])));
                        System.out.println(Integer.toString(x));
                        System.out.println(Integer.toString(y));
                        System.out.println(Integer.toString(x2));
                        System.out.println(Integer.toString(y2));
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
                        if(game.isLegal(fields[0], fields[1])) game.move(fields[0], fields[1]);
                        outB.writeObject(game.isLegal(fields[0], fields[1]));
                        outB.flush();
                        outB.reset();
                        System.out.println("Success");
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

    public void broadcastGameState(ObjectOutputStream out) throws IOException
    {
        Board board = new Board(8,8, true);
        out.writeObject(board);
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
