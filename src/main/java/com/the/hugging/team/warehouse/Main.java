package com.the.hugging.team.warehouse;

import com.the.hugging.team.utils.Window;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Window defaultWindow = new Window("views/login/login.fxml");
        defaultWindow.setAsDefaultStage();
        defaultWindow.showStage();
    }
}
