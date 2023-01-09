package Network;

import Model.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private static final int port = 4444;
    private static Game game;

    public static void main(String [] args) throws IOException {
        boolean wMove = true;
        ServerSocket listener = new ServerSocket(port);

        Socket socketW = listener.accept();
        System.out.println("First Client connected!");
//        Socket socketB = listener.accept();
//        System.out.println("Second Client connected!");

        BufferedReader inW = new BufferedReader(new InputStreamReader(socketW.getInputStream()));
//        BufferedReader inB = new BufferedReader(new InputStreamReader(socketB.getInputStream()));
        PrintWriter outW = new PrintWriter(socketW.getOutputStream(),true);
//        PrintWriter outB = new PrintWriter(socketB.getOutputStream(), true);

        outW.println("You are playing White!");
//        outB.println("You are playing Black!");


        for(int i = 0; i < 10; i++){
            String str = inW.readLine();
            System.out.println(str);
        }
/*
        game = new Game();

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
                if(s == null) break;
                System.out.println(s);
                outW.println("Black moved " + s);
            }
            wMove = !wMove;
        }
*/
//        inB.close();
        inW.close();
//        outB.close();
        outW.close();
//        socketB.close();
        socketW.close();
        }
    }

