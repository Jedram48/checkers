package Network;

import Model.Gamestate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static Gamestate game = new Gamestate();
    private static final int port = 4444;
    private static ClientHandler White = null;
    private static ClientHandler Black = null;
    private static ExecutorService pool = Executors.newFixedThreadPool(2);
    public static void main(String [] args) throws IOException {
        ServerSocket listener = new ServerSocket(port);

        while (Black == null)
        {
            System.out.println("Server is waitintg for connection");
            Socket client = listener.accept();
            if(White == null)
            {
                White = new ClientHandler(client);
                pool.execute(White);
                System.out.println("First Client Connected");
            }
            else
            {
                Black = new ClientHandler(client);
                pool.execute(Black);
                System.out.println("Second Client Connected");
            }
        }








    }
}
