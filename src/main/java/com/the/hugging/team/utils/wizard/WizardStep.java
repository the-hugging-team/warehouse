package com.the.hugging.team.utils.wizard;

import com.the.hugging.team.utils.Window;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

public /*abstract*/ class WizardStep {
    private final int stepNumber;
    private final String stepName;
    private final Button fillerButton = new Button();
    private final Button stepLabelButton = new Button();
    private final Button stepNameButton = new Button();
    private String fxmlPath;
    private AnchorPane anchor;
    private Window window;

    public WizardStep(int stepNumber, String stepName) {
        this.stepNumber = stepNumber;
        this.stepName = stepName;
    }

    public void initStep(AnchorPane anchor, Window current, HBox parent, int currentStep) {
        this.anchor = anchor;
        this.window = current;
        showStep(parent, currentStep);
        setButtonClick();
    }

    public void showStep(HBox parent, int currentStep) {
        HBox stepHBox = renderStep(currentStep);
        parent.getChildren().add(stepHBox);
        HBox.setHgrow(stepHBox, Priority.ALWAYS);

    }

    private HBox renderStep(int currentStep) {
        HBox stepHBox = new HBox();

        stepLabelButton.setText(stepNumber + "");
        stepNameButton.setText(stepName);

        //TODO: Style Buttons and set appropriate FXML to load when clicked
        stepLabelButton.setPrefHeight(80);
        stepLabelButton.setPrefWidth(80);
        stepLabelButton.setMinHeight(80);
        stepLabelButton.setMinWidth(80);
        stepLabelButton.setMaxHeight(80);
        stepLabelButton.setMaxWidth(80);
        stepLabelButton.setTextAlignment(TextAlignment.CENTER);
        stepLabelButton.setAlignment(Pos.CENTER);

        stepNameButton.setPrefHeight(30);
        stepNameButton.setPrefWidth(1000);
        stepNameButton.setMaxWidth(1000);
        stepNameButton.setAlignment(Pos.BASELINE_LEFT);

        fillerButton.setPrefHeight(30);
        fillerButton.setPrefWidth(30);
        fillerButton.setMaxWidth(30);

        stepHBox.getChildren().addAll(fillerButton, stepLabelButton, stepNameButton);
        HBox.setHgrow(fillerButton, Priority.SOMETIMES);
        HBox.setHgrow(stepLabelButton, Priority.NEVER);
        HBox.setHgrow(stepNameButton, Priority.ALWAYS);
        stepHBox.alignmentProperty().setValue(Pos.CENTER);
        return stepHBox;
    }

    private void selectStep() {
    }

    public void loadStep(AnchorPane anchor, Window current) {
//        Window stepWindow = new Window("fxml");
//        stepWindow.setAsAnchorPane(anchor, current);
    }

    public void setButtonClick() {
        stepLabelButton.setOnAction(e -> {
            selectStep();
            loadStep(anchor, window);
        });
        stepNameButton.setOnAction(e -> {
            selectStep();
            loadStep(anchor, window);
        });
        fillerButton.setOnAction(e -> {
            selectStep();
            loadStep(anchor, window);
        });
    }
}
