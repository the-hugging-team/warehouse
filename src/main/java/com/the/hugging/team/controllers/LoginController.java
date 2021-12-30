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
    private Session session;
    private UserService userService;
    private Label errorMessageLabel;
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
                (observableValue, oldAnchorWidth, newAnchorWidth) -> formpane.setLayoutX((newAnchorWidth.doubleValue() / 2) - (backgroundpane.getPrefWidth() / 2)));
        anchor.heightProperty().addListener(
                (observableValue, oldAnchorHeight, newAnchorHeight) -> formpane.setLayoutY((newAnchorHeight.doubleValue() / 2) - (backgroundpane.getPrefHeight() / 2)));

        try {
            userService = UserService.getInstance();
            session = Session.getInstance();
        } catch (Exception e) {
            String noConnectionMessage = "Unable to connect to Database";
            errorMessageLabel = new Label(noConnectionMessage);
            formpane.getChildren().add(errorMessageLabel);
            errorMessageLabel.getStyleClass().addAll("errorlabel", "font-extra-bold");
            errorMessageLabel.setLayoutX(140);
            errorMessageLabel.setLayoutY(150);
            usernamefield.setDisable(true);
            passwordfield.setDisable(true);
            loginbutton.setDisable(true);
        }
    }

    public void login(ActionEvent event) {
        User authUser = userService.getAuthUser(usernamefield.getText(), passwordfield.getText());

        if (authUser != null) {
            session.setUser(authUser);
            Window dashboardWindow = new Window("views/dashboard/dashboard-template.fxml");
            dashboardWindow.setAsNextStage(event);
            dashboardWindow.showStage();
        } else {
            if (!formpane.getChildren().contains(errorMessageLabel)) {
                String invalidDataMessage = "Invalid username or password";
                errorMessageLabel = new Label(invalidDataMessage);
                formpane.getChildren().add(errorMessageLabel);
                errorMessageLabel.getStyleClass().addAll("errorlabel", "font-italic");
                errorMessageLabel.setLayoutX(150);
                errorMessageLabel.setLayoutY(150);
            }
        }
    }
}
