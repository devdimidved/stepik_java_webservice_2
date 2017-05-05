package main;

import echoServer.EchoServer;
import echoServer.EchoServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());
    static final int SERVER_PORT = 5050;

    public static void main(String[] args) {
        EchoServerI echoServer = new EchoServer(SERVER_PORT);
        logger.info("Server started");
        echoServer.go();
    }
}
