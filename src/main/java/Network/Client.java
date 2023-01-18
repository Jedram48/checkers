package Network;

import Model.Board;
import Model.Field;
import Model.Game;
import Widok.Window;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean turn;

    public Client() throws IOException
    {
        this.socket = new Socket("localhost", 1234);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    private void close() throws IOException {

        this.out.close();
        this.in.close();
        this.socket.close();
    }

    public void sendMoves(Field[] fields) throws IOException
    {
        this.out.writeObject(fields);
        this.out.flush();
        this.out.reset();
    }

    public Board loadBoard() throws IOException, ClassNotFoundException {
        return (Board) in.readObject();
    }

    public boolean isValid() throws IOException, ClassNotFoundException {
        return (boolean) in.readObject();
    }


    public Board getNewGameState()
    {
        final Board[] board = new Board[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        board[0] = (Board) in.readObject();
                        break;

                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start();
        return board[0];
    }



}

