package echoServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer implements EchoServerI {
    final private int ECHO_PORT;
    private Logger logger;

    public EchoServer(int port) {
        ECHO_PORT = port;
        logger = LogManager.getLogger(EchoServer.class.getName());
    }

    @Override
    public void go() {
        try (ServerSocket serverSocket = new ServerSocket(ECHO_PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new SocketHandler(socket)).start();
            }
        } catch (IOException ioe) {
            logger.info(ioe.getMessage());
        }
    }
}
