package com.the.hugging.team.utils;

import com.the.hugging.team.entities.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class Dialogs {
    public static void NotSelectedWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        alert.setGraphic(null);
        alert.setTitle("Warning: Item not selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item from the list!");
        alert.showAndWait();
    }

    public static Optional<String> singleTextInputDialog(String message, String title, String content) {
        TextInputDialog dialog = new TextInputDialog(message);
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);

        return dialog.showAndWait();
    }

    public static Optional<User> userDialog(User user, String title) {
        Dialog<User> dialog = new Dialog<>();
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle(title);
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstName = new TextField();
        firstName.setPromptText("First name");

        if (user.getFirstName() != null) {
            firstName.setText(user.getFirstName());
        }

        TextField lastName = new TextField();
        lastName.setPromptText("Last name");

        if (user.getLastName() != null) {
            lastName.setText(user.getLastName());
        }

        ChoiceBox sex = new ChoiceBox(FXCollections.observableArrayList("Male", "Female"));
        sex.getSelectionModel().selectFirst();

        if (user.getSex() != null) {
            sex.getSelectionModel().select(user.getSex() - 1);
        }

        TextField username = new TextField();
        username.setPromptText("Username");

        if (user.getUsername() != null) {
            username.setText(user.getUsername());
        }

        TextField password = new TextField();
        password.setPromptText("Password");
        Button randomPassword = new Button("Generate random password");
        randomPassword.setOnAction(event -> password.setText(Hasher.generateRandomPassword(8, 12)));

        if (user.getUsername() != null) {
            username.setText(user.getUsername());
        }

        grid.add(new Label("First name:"), 0, 0);
        grid.add(firstName, 1, 0);

        grid.add(new Label("Last name:"), 0, 1);
        grid.add(lastName, 1, 1);

        grid.add(new Label("Sex:"), 0, 2);
        grid.add(sex, 1, 2);

        grid.add(new Label("Username:"), 0, 4);
        grid.add(username, 1, 4);

        if (user.getPassword() == null) {
            grid.add(new Label("Password:"), 0, 5);
            grid.add(password, 1, 5);
            grid.add(randomPassword, 2, 5);
        }

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                user.setFirstName(firstName.getText());
                user.setLastName(lastName.getText());
                user.setSex(sex.getSelectionModel().getSelectedIndex() + 1);
                user.setUsername(username.getText());
                user.setPassword(Hasher.hash(password.getText()));
                return user;
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
