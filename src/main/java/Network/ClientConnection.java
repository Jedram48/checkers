package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader input;

    public ClientConnection(){

    }

    public void sendRequest(){
        this.out.println("move");
    }

    public void connect() throws IOException {
        this.socket = new Socket("localhost", 4444);
        this.out = new PrintWriter(socket.getOutputStream(),true);
        this.input = new BufferedReader(new InputStreamReader( socket.getInputStream() ));
    }
    public void disconnect() throws IOException {
        this.input.close();
        this.out.close();
        this.socket.close();
    }
}
