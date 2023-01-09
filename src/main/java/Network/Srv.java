package Network;

import Model.Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


public class Srv {

    private static ServerSocket listener;
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

    static Logic logic = new Logic();
    private static final int port = 4444;

    private static void start() throws IOException {
        listener = new ServerSocket(port);
        socket = listener.accept();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);
    }

    private static void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    private static String request() throws IOException {
        return in.readLine();
    }

    private static void send(String msg){
        out.println(msg);
    }

    private static int[] args(String[] nums){
        int size = nums.length;
        int[] arr = new int[size];
        for(int i = 0; i < size; i++){
            arr[i] = Integer.parseInt(nums[i]);
        }
        return arr;
    }
    public static void main(String [] args) throws IOException {
        start();

        while (true){
            String[] str = request().split(" ", 2);
            String cmd = str[0];
            int[] param = args(str[1].split(" "));

            if(cmd.equals("MOVE")){
                send(Boolean.toString(logic.move(param[0], param[1], param[2], param[3])));
            }



            if(cmd.equals("DISCONNECT")){break;}
        }

        close();
    }
}

