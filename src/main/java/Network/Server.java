package Network;

import Model.Board;

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

    public void startTheGame()
    {
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
                        if(requestedB) {
                            broadcastGameState(outW);
                            requestedB = false;
                        }
                    }
                } catch (IOException e)
                {
                    System.err.println("Problem with White's connection...");
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
                        if(requestedW) {
                            broadcastGameState(outB);
                            requestedW = false;
                        }
                    }
                } catch (IOException e)
                {
                    System.err.println("Problem with Black's connection...");
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
