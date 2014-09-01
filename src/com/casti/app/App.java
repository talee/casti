package com.casti.app;

import com.casti.server.Server;
import com.casti.ui.OutputFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author tlee5
 */
public class App {

    private static final Logger LOG = LogManager.getLogger(App.class.getCanonicalName());
    private static final int PORT = 62001;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
        String initialUri = "http://localhost:"+server.getPort()+"/waiting";
        OutputFrame frame = new OutputFrame(initialUri, new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                LOG.info("server.stopping");
                server.stop(5);
            }
        });
    }

}