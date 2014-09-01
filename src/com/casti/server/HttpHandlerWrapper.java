package com.casti.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
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
}