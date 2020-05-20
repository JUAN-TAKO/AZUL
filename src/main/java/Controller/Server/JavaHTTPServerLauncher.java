package Controller.Server;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.util.Enumeration;
import Model.GlobalBoard;

public class JavaHTTPServerLauncher implements Runnable {

    // port to listen connection
    static final int PORT = 8000;

    @Override
    public void run() {
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + "...\n");

            // we listen until user halts server execution
            while (true) {
                JavaHTTPServer myServer = new JavaHTTPServer(serverConnect.accept());
                // create dedicated thread to manage the client connection
                Thread thread = new Thread(myServer);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }

    public static JavaHTTPServerLauncher start() {
        JavaHTTPServerLauncher j = new JavaHTTPServerLauncher();
        Thread thread = new Thread(j);
        thread.start();
        return j;
    }
}
