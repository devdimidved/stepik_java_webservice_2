package servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resources.ResourceServerI;
import resources.TestResource;
import sax.ReadXMLFileSAX;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourcesPageServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(ResourcesPageServlet.class.getName());
    public static final String PAGE_URL = "/resources";
    private final ResourceServerI resourceServer;

    public ResourcesPageServlet(ResourceServerI resourceServer) {
        this.resourceServer = resourceServer;
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        try {
            String testResourcePath = request.getParameter("path");
            TestResource testResource = (TestResource) ReadXMLFileSAX.readXML(testResourcePath);
            resourceServer.setResource(testResource);

            String name = resourceServer.getName();
            int age = resourceServer.getAge();
            logger.info("testResource: name = {}, age = {}.", name, age);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NullPointerException npe) {
            logger.info("Could not get resource attribute(s).");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
