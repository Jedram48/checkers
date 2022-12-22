package Network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException
    {
        Socket socket = new Socket("localhost", 4444);
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader input = new BufferedReader(new InputStreamReader( socket.getInputStream() ));

        while (true)
        {
            System.out.println("> ");
            Scanner scan = new Scanner(System.in);
            String command = scan.nextLine();
            if (command.equals("quit")) break;
            out.println(command);

            String response = input.readLine();
            System.out.println("Server says: " + response);
        }


        socket.close();
        System.exit(0);
    }
}
