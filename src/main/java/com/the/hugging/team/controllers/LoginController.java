package com.the.hugging.team.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


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

    public void login(ActionEvent e) {

    }
}
