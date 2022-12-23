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
        Scanner scan = new Scanner(System.in);
        boolean myMove = true;
        Socket socket = new Socket("localhost", 4444);
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader input = new BufferedReader(new InputStreamReader( socket.getInputStream() ));

        if(input.readLine().equals("You are playing Black!")) myMove = false ;

        while (true)
        {
            System.out.println(input.readLine());

            if(myMove)
            {
                String move = scan.nextLine();
                if(move.equals("end")) break;
                out.println(move);
            }
            else
            {
                System.out.println(input.readLine());
            }
            myMove = !myMove;

        }

        input.close();
        out.close();
        socket.close();
        System.exit(0);
    }
}
