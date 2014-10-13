package com.casti.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Adds logging etc.
 * @author tlee5
 */
public class HttpHandlerWrapper implements HttpHandler {
    private final HttpHandler wrapped;
    private static final Logger LOG = LogManager.getLogger(HttpHandlerWrapper.class.getCanonicalName());

    public HttpHandlerWrapper(HttpHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        LOG.info(he.getRequestMethod()+": "+he.getRequestURI().getPath());
        wrapped.handle(he);
    }
    
    public static String getRequestBody(HttpExchange he) {
        try (Scanner scanner = new Scanner(he.getRequestBody())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}