package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) throws IOException
    {
        this.client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);
    }

    @Override
    public void run() {

        try
        {
            while(true)
            {
                String request = in.readLine();
                System.out.println("Client says : " + request);
                out.println(request);
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        finally
        {
            out.close();
            try
            {
                in.close();
                client.close();
            }
            catch (IOException e)
            {

            }

        }

    }
}
