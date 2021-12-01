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

    private final UserService userService = UserService.getInstance();
    private final String invalidDataMessage = "Invalid username or password";
    private final Label invalidDataLabel = new Label(invalidDataMessage);
    Session session = Session.getInstance();
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
    }

    public void login(ActionEvent event) {
        User authUser = userService.getAuthUser(usernamefield.getText(), passwordfield.getText());

        if (authUser != null) {
            session.setUser(authUser);
            Window dashboardWindow = new Window("views/dashboard/dashboard-template.fxml", (Object[]) null);
            dashboardWindow.setAsNextStage(event);
            dashboardWindow.showStage();
        } else {
            if (!formpane.getChildren().contains(invalidDataLabel)) {
                formpane.getChildren().add(invalidDataLabel);
                invalidDataLabel.getStyleClass().addAll("errorlabel", "font-italic");
                invalidDataLabel.setLayoutX(150);
                invalidDataLabel.setLayoutY(150);
            }
        }
    }
}
