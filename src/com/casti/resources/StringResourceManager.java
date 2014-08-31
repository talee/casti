package com.casti.resources;

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Returns strings for resources.
 * @author tlee5
 */
public class StringResourceManager {
    private static final String BASE_URI = "/com/casti/resources/";
    private static final Logger LOG = Logger.getLogger("StringResourceManager");

    public static String getContents(String path) {
        InputStream is = StringResourceManager.class.getResourceAsStream(BASE_URI+path);
        if (is==null) {
            LOG.severe("No resource found at "+BASE_URI+path);
            return "";
        }
        try (Scanner scan = new Scanner(is).useDelimiter("\\A")) {
            return scan.hasNext() ? scan.next() : "";
        }
    }
}