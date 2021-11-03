package com.the.hugging.team.warehouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/login/login.fxml")));
            Stage primaryStage = new Stage();
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(new Image("assets/images/logos/cellablue_logo.png"));
            primaryStage.setTitle("CELLA");
            primaryStage.setMinHeight(620);
            primaryStage.setMinWidth(1150);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
