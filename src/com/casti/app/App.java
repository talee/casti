package com.casti.app;

import com.casti.server.Server;
import com.casti.ui.OutputFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

/**
 *
 * @author tlee5
 */
public class App {

    private static final Logger LOG = Logger.getLogger("App");
    private static final int PORT = 62001;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        LOG.info("Port: "+server.getPort());
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