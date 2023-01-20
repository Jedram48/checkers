package Network;

import Model.Board;
import Model.Field;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public boolean white;

    /***
     * Create connection with server
     * @throws IOException
     */
    public Client() throws IOException
    {
        this.socket = new Socket("localhost", 1234);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        this.white = in.readBoolean();
    }

    /***
     * Close connection with server
     * @throws IOException
     */
    public void close() throws IOException {
        this.out.close();
        this.in.close();
        this.socket.close();
    }

    /***
     * Send move performed by player to server
     * @param fields fields which represents performed move
     * @throws IOException
     */
    public void sendMoves(Field[] fields) throws IOException
    {
        this.out.writeObject(fields);
        this.out.flush();
        this.out.reset();
    }

    /***
     * Receive new board state from server
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Board loadBoard() throws IOException, ClassNotFoundException {
        return (Board) in.readObject();
    }

    /***
     * Get connection status with server
     * @return
     */
    public boolean isConnected() {
        return this.socket.isConnected();
    }

}

