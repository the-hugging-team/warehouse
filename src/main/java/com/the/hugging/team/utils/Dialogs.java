package com.the.hugging.team.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

public class Dialogs {
    public static void NotSelectedWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        alert.setTitle("Warning: Item not selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item from the list!");
        alert.showAndWait();
    }

    public static Optional<String> singleTextInputDialog(String message, String title, String content) {
        TextInputDialog dialog = new TextInputDialog(message);
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);

        return dialog.showAndWait();
    }
}
