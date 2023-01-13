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
/*
    public void listenToServer()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        Board board = (Board) in.readObject();
                        Board b = new Game().getBoard();
                        Window window = new Window(b);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start();
    }
*/


}

