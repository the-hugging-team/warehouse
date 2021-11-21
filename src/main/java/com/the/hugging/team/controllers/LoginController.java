package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.User;
import com.the.hugging.team.services.UserService;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.Window;
import com.the.hugging.team.utils.WindowHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class LoginController extends WindowHandler {

    Session session = Session.getInstance();
    private UserService userService = UserService.getInstance();
    private String invalidDataMessage = "Invalid username or password";
    private Label invalidDataLabel = new Label(invalidDataMessage);
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane backgroundpane;
    @FXML
    private TextField usernamefield;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private Button loginbutton;
    @FXML
    private Pane formpane;

    public LoginController() {
    }

    @FXML
    private void initialize() {
        anchor.widthProperty().addListener(
                (observableValue, oldSceneWidth, newSceneWidth) -> formpane.setLayoutX((newSceneWidth.doubleValue() / 2) - (backgroundpane.getPrefWidth() / 2)));
        anchor.heightProperty().addListener(
                (observableValue, oldSceneHeight, newSceneHeight) -> formpane.setLayoutY((newSceneHeight.doubleValue() / 2) - (backgroundpane.getPrefHeight() / 2)));
    }

    public void login(ActionEvent event) {
        User authUser = userService.getAuthUser(usernamefield.getText(), passwordfield.getText());

        if (authUser != null)
        {
            session.setUser(authUser);
            Window dashboardWindow = new Window("views/dashboard/dashboard-template.fxml");
            dashboardWindow.setAsNextStage(event);
            dashboardWindow.showStage();
        }
        else
        {
            formpane.getChildren().add(invalidDataLabel);
            invalidDataLabel.getStyleClass().addAll("errorlabel", "font-italic");
            invalidDataLabel.setLayoutX(150);
            invalidDataLabel.setLayoutY(150);
        }
    }
}
