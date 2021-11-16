package com.the.hugging.team.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Window {

    public static final int DEFAULT_WIN_HEIGHT = 720;
    public static final int DEFAULT_WIN_WIDTH = 1280;
    public static final String DEFAULT_WIN_CAPTION = "CELLA";
    public static final String CELLABLUE_PATH = "assets/images/logos/cellablue_logo.png";

    private FXMLLoader fxmlLoader;

    private Parent root;
    private Stage stage;
    private Scene scene;

    public Window(String fxmlPath)
    {
        try
        {
            fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(fxmlPath)));
            root = fxmlLoader.load();
            WindowHandler wh = fxmlLoader.getController();
            if (wh != null) wh.setWindow(this);
            scene = new Scene(root);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setAsDefaultStage()
    {
        stage = new Stage();
        stage.getIcons().add(new Image(CELLABLUE_PATH));
        stage.setTitle(DEFAULT_WIN_CAPTION);
    }

    public void setAsNextStage(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    }

    public void setAsAnchorPane(AnchorPane parent, Window window)
    {
        window.fxmlLoader.setController(this.fxmlLoader.getController());
        parent.getChildren().setAll(root.getChildrenUnmodifiable());
    }

    public void showStage()
    {
        stage.setMinWidth(DEFAULT_WIN_WIDTH);
        stage.setMinHeight(DEFAULT_WIN_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}
