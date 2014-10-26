package com.casti.server;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Basic CORS.
 * @author tlee5
 */
public class CorsFilter extends Filter {

    private final String corsDomain;

    public CorsFilter(String corsDomain) {
        this.corsDomain = corsDomain;
    }

    @Override
    public void doFilter(HttpExchange he, Filter.Chain chain) throws IOException {
        if (CorsFilter.getDomain(he.getRequestHeaders().getFirst("Origin")).endsWith(corsDomain)) {
            he.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            chain.doFilter(he);
        } else {
            he.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, -1);
        }
    }

    private static String getDomain(String origin) {
        int protocolIndex = origin.indexOf(":");
        if (protocolIndex == -1) {
            return "";
        }
        int portIndex = origin.indexOf(":", protocolIndex+1);
        if (portIndex != -1) {
            origin = origin.substring(0, portIndex);
        }
        return origin;
    }

    @Override
    public String description() {
        return "CORS filter";
    }
}