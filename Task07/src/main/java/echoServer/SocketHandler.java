package echoServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandler implements Runnable {
    private Socket socket;
    private Logger logger;
    private final String END_MESSAGE = "Bue.";

    public SocketHandler(Socket socket) {
        this.socket = socket;
         logger = LogManager.getLogger(SocketHandler.class.getName());
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            while (true) {
                String message = reader.readLine();
                if (message.equals(END_MESSAGE)) {
                    break;
                }
                writer.println(message);
                writer.flush();
            }
        } catch (IOException ioe) {
            logger.info(ioe.getMessage());
        }
    }
}
