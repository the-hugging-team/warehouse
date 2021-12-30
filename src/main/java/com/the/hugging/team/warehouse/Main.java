package com.the.hugging.team.warehouse;

import com.the.hugging.team.utils.Connection;
import com.the.hugging.team.utils.Window;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {

    private static final Logger Log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Window defaultWindow = new Window("views/login/login.fxml");
        defaultWindow.setAsDefaultStage();
        defaultWindow.showStage();
    }

    @Override
    public void stop() {

        try {
            Connection.closeEMF();
        } catch (Exception e) {
            Log.info("Could not close EMF");
        }
    }
}
