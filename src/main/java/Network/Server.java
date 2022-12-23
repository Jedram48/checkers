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


    public static void main(String [] args) throws IOException {
        boolean wMove = true;
        ServerSocket listener = new ServerSocket(port);

        Socket socketW = listener.accept();
        System.out.println("First Client connected!");
        Socket socketB = listener.accept();
        System.out.println("Second Client connected!");

        BufferedReader inW = new BufferedReader(new InputStreamReader(socketW.getInputStream()));
        BufferedReader inB = new BufferedReader(new InputStreamReader(socketB.getInputStream()));
        PrintWriter outW = new PrintWriter(socketW.getOutputStream(),true);
        PrintWriter outB = new PrintWriter(socketB.getOutputStream(), true);

        outW.println("You are playing White!");
        outB.println("You are playing Black!");

        while(true)
        {
            if(wMove)
            {
                outW.println("Your turn!");
                outB.println("Waiting for White...");
                String s = inW.readLine();
                System.out.println(s);
                outB.println("White moved " + s);
            }
            else
            {
                outB.println("Your turn!");
                outW.println("Waiting for Black...");
                String s = inB.readLine();
                if(s.equals("null")) break;
                System.out.println(s);
                outW.println("Black moved " + s);
            }
            wMove = !wMove;
        }

        inB.close();
        inW.close();
        outB.close();
        outW.close();
        socketB.close();
        socketW.close();
        }
    }

