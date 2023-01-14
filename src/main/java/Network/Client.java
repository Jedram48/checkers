package Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public Client(Socket socket) throws IOException
    {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void sendMoves() throws IOException
    {
        try
        {
            while(socket.isConnected())
            {
                Scanner scanner = new Scanner(System.in);
                String msgToSend = scanner.nextLine(); //sending moves
                out.write(msgToSend);
                out.newLine();
                out.flush();
            }
        } catch (IOException e)
        {
            System.err.println("Problem with sending moves...");

        }
    }

    public void listenToServer()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    while(true)
                    {
                        String msg = in.readLine();
                        System.out.println("~~Gamestate from server~~");
                    }
                } catch (IOException e)
                {
                    System.err.println("Problem with White's connection...");
                }

            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket);
        client.listenToServer();
        client.sendMoves();

    }


}
