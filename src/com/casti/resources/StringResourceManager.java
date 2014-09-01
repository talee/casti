package com.casti.resources;

import java.io.InputStream;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Returns strings for resources.
 * @author tlee5
 */
public class StringResourceManager {
    private static final String BASE_URI = "/com/casti/resources/";
    private static final Logger LOG = LogManager.getLogger(StringResourceManager.class.getCanonicalName());

    public static String getContents(String path) {
        InputStream is = StringResourceManager.class.getResourceAsStream(BASE_URI+path);
        if (is==null) {
            LOG.error("No resource found at "+BASE_URI+path);
            return "";
        }
        try (Scanner scan = new Scanner(is).useDelimiter("\\A")) {
            return scan.hasNext() ? scan.next() : "";
        }
    }
}