package com.mf.util.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.http.HttpServlet;
import java.util.Map;

/**
 * Created by shixin on 4/2/14.
 * http://www.eclipse.org/jetty/documentation/
 * We are using jetty9.
 */
public class JettyServerUtil {
    /**
     * Create server using ServletContextHandler.
     *
     * @param threads
     * @param port
     * @param contextPath
     * @param attributes
     * @param servlets
     * @return
     */
    public static Server createServletContextServer(int threads, int port, String contextPath,
                                                    Map<String, Object> attributes, Map<String, HttpServlet> servlets) {
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(threads);

        Server server = new Server(queuedThreadPool);

        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(port);
        server.addConnector(serverConnector);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath(contextPath);

        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            String attrName = entry.getKey();
            Object attrObject = entry.getValue();
            servletContextHandler.setAttribute(attrName, attrObject);
        }

        for (Map.Entry<String, HttpServlet> entry : servlets.entrySet()) {
            String servletContextPath = entry.getKey();
            HttpServlet servlet = entry.getValue();
            servletContextHandler.addServlet(new ServletHolder(servlet), servletContextPath);
        }

        server.setHandler(servletContextHandler);
        return server;
    }
}
