package com.casti.server;

import com.casti.resources.StringResourceManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.function.Consumer;
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

    /**
     * @param port
     * @param setContentInFrame Function must set given HTML string inside display frame
     */
    public Server(int port, final Consumer<String> setContentInFrame) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.getAddress().getPort();
            server.createContext("/start", new HttpHandlerWrapper((HttpExchange he) -> {
                if ("POST".equals(he.getRequestMethod())) {
                } else {
                    he.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
                }
                he.close();
            }));
            server.createContext("/display",  new HttpHandlerWrapper((HttpExchange he) -> {
                if ("POST".equals(he.getRequestMethod())) {
                    String content = HttpHandlerWrapper.getRequestBody(he);
                    he.getResponseHeaders().add("Content-type", "text/html");
                    he.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
                    setContentInFrame.accept(content);
                } else {
                    he.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
                }
                he.close();
            }));
            server.createContext("/waiting",  new HttpHandlerWrapper((HttpExchange he) -> {
                String content = StringResourceManager.getContent("web/waiting.html");
                he.getResponseHeaders().add("Content-type", "text/html");
                he.sendResponseHeaders(HttpURLConnection.HTTP_OK, content.length());
                try (OutputStream out = he.getResponseBody()) {
                    out.write(content.getBytes());
                }
                he.close();
            }));
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