package com.casti.app;

import com.casti.server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initializes components and starts the app.
 * @author tlee5
 */
public class App extends Application {

    private static final Logger LOG = LogManager.getLogger(App.class.getCanonicalName());
    private static final int PORT = 62001;
    private static Server server;

    public static void main(String[] args) {
        server = new Server(PORT);
        server.start();
        App.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Casti");

        WebView browser = new WebView();
        String initialUri = "http://localhost:"+server.getPort()+"/waiting";
        browser.getEngine().load(initialUri);

        StackPane root = new StackPane();
        root.getChildren().clear();
        root.getChildren().add(browser);

        Scene scene = new Scene(root, 400, 100, Color.BLACK);
        stage.setScene(scene);
        stage.setOnCloseRequest((WindowEvent evt) -> {
            LOG.info("Stopping server");
            long startTime = System.currentTimeMillis();
            new Thread(() -> {
                // Server.stop is a blocking op
                server.stop(0);
                LOG.info("Stopped server successfully in "+(System.currentTimeMillis()-startTime)/1000+"s");
                Platform.runLater(() -> {
                    Platform.exit();
                });
            }).start();
            evt.consume();
        });
        stage.show();
    }
}