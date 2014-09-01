package com.casti.server;

import com.casti.resources.StringResourceManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lightweight server instance.
 * @author tlee5
 */
public class Server {

    private HttpServer server;
    private static final Logger LOG = LogManager.getLogger(Server.class.getCanonicalName());

    public Server(int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.getAddress().getPort();
            server.createContext("/start", new HttpHandlerWrapper((HttpExchange he) -> {
                LOG.info("Received: "+he.getRequestMethod());
                if ("POST".equals(he.getRequestMethod())) {
                } else {
                    he.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                    he.getResponseBody().close();
                }
            }));
            server.createContext("/display",  new HttpHandlerWrapper((HttpExchange he) -> {
            }));
            server.createContext("/waiting",  new HttpHandlerWrapper((HttpExchange he) -> {
                String contents = StringResourceManager.getContents("web/waiting.html");
                he.getResponseHeaders().add("Content-type", "text/html");
                he.sendResponseHeaders(HttpURLConnection.HTTP_OK, contents.length());
                try (OutputStream out = he.getResponseBody()) {
                    out.write(contents.getBytes());
                }
            }));
            server.setExecutor(null);
        } catch (IOException ex) {
            LOG.log(Level.FATAL, "Failed to start server with given port "+port, ex);
        }
    }
    public void start() {
        server.start();
    }
    public void stop(int waitTime) {
        server.stop(waitTime);
    }
    public int getPort() {
        return server.getAddress().getPort();
    }
}