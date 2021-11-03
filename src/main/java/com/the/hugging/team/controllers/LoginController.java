package com.the.hugging.team.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class LoginController {

    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane backgroundpane;
    @FXML
    private TextField usernamefield;
    @FXML
    private TextField passwordfield;
    @FXML
    private Button loginbutton;
    @FXML
    private Pane formpane;

    public LoginController() {
    }

    @FXML
    private void initialize() {
        anchor.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> formpane.setLayoutX((newSceneWidth.doubleValue() / 2) - (backgroundpane.getPrefWidth() / 2)));
        anchor.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> formpane.setLayoutY((newSceneHeight.doubleValue() / 2) - (backgroundpane.getPrefHeight() / 2)));
    }

    public void login(ActionEvent event) {
      try{
          Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/dashboard/dashboard-template.fxml")));
          Scene scene = new Scene(root);
          Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
          primaryStage.setMinHeight(620);
          primaryStage.setMinWidth(1150);
          primaryStage.setScene(scene);
          primaryStage.show();

      }catch(Exception e){
          e.printStackTrace();

      }
    }
}
