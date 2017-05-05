package main;

import accountServer.AccountServer;
import accountServer.AccountServerController;
import accountServer.AccountServerControllerMBean;
import accountServer.AccountServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resources.*;
import servlets.AdminPageServlet;
import servlets.HomePageServlet;
import servlets.ResourcesPageServlet;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {

        final int portNumber = 8080;
        final int INITIAL_USERS_LIMIT = 10;

        logger.info("Starting at http://127.0.0.1:" + portNumber);

        AccountServerI accountServer = new AccountServer(INITIAL_USERS_LIMIT);
        AccountServerControllerMBean serverAccountStatistics = new AccountServerController(accountServer);
        ObjectName accountSCName = new ObjectName("Admin:type=AccountServerController");

        TestResource testResource = new TestResource("init_name", 1);
        ResourceServerI resourceServer = new ResourceServer(testResource);
        ResourceServerControllerMBean serverResources = new ResourceServerController(resourceServer);
        ObjectName resourceSCName = new ObjectName("Admin:type=ResourceServerController");

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        mbs.registerMBean(serverAccountStatistics, accountSCName);
        mbs.registerMBean(serverResources, resourceSCName);

        Server server = new Server(portNumber);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new HomePageServlet(accountServer)), HomePageServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new AdminPageServlet(accountServer)), AdminPageServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new ResourcesPageServlet(resourceServer)), ResourcesPageServlet.PAGE_URL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        logger.info("Server started");

        server.join();

    }
}
