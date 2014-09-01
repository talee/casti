package com.casti.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Frame displayed on external display.
 * @deprecated Using JavaFx now. See {@link com.casti.app.App}
 * @author tlee5
 */
public class OutputFrame {

    private static final Logger LOG = LogManager.getLogger(OutputFrame.class.getCanonicalName());
    private final XHTMLPanel panel;
    private final Dimension INITAL_DIMENSION = new Dimension(400, 100);

    public OutputFrame(String uri, WindowAdapter windowAdapter) {
        panel = new XHTMLPanel();
        LOG.info("URI: "+uri);
        panel.setDocument(uri);
        JFrame frame = new JFrame("Casti");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setBackground(Color.BLACK);
        frame.setSize(INITAL_DIMENSION);
        frame.addWindowListener(windowAdapter);
        EventQueue.invokeLater(() -> {
            frame.setVisible(true);
        });
    }
    public void setUri(String uri) {
        panel.setDocument(uri);
    }
}