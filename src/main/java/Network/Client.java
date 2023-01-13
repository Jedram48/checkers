package Network;

import Model.Board;
import Widok.Window;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client(Socket socket) throws IOException
    {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    private void close() throws IOException {

        this.out.close();
        this.in.close();
        this.socket.close();
    }

    public void sendMoves() throws IOException
    {


    }

    public void listenToServer()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        Board board = (Board) in.readObject();
                        Window window = new Window(board);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start();
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket);
        client.listenToServer();
    }


}

