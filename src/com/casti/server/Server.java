package com.casti.server;

import com.casti.app.App;
import com.casti.resources.StringResourceManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lightweight server instance.
 * @author tlee5
 */
public class Server {

    private HttpServer server;
    private static final Logger LOG = Logger.getLogger("Server");

    public Server(int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.getAddress().getPort();
            server.createContext("/start", (HttpExchange he) -> {
                LOG.info("Received: "+he.getRequestMethod());
                if ("POST".equals(he.getRequestMethod())) {
                } else {
                    he.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                    he.getResponseBody().close();
                }
            });
            server.createContext("/display", (HttpExchange he) -> {
            });
            server.createContext("/waiting", (HttpExchange he) -> {
                LOG.info(he.getRequestURI().getPath());
                String contents = StringResourceManager.getContents("web/waiting.html");
                he.sendResponseHeaders(HttpURLConnection.HTTP_OK, contents.length());
                OutputStream out = he.getResponseBody();
                out.write(contents.getBytes());
            });
            server.setExecutor(null);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
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