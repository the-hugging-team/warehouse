package com.the.hugging.team.utils.wizard;

import com.the.hugging.team.utils.Window;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

public /*abstract*/ class WizardStep implements Listener<StepEvent> {
    private final int stepNumber;
    private final String stepName;
    private final Button fillerButton = new Button();
    private final Button stepLabelButton = new Button();
    private final Button stepNameButton = new Button();
    private final String fxmlPath;
    private AnchorPane anchor;
    private HBox stepHBox;
    private Window window;
    private final EventSource eventSource = EventSource.getInstance();

    public WizardStep(int stepNumber, String stepName, String fxmlPath) {
        this.stepNumber = stepNumber;
        this.stepName = stepName;
        this.fxmlPath = fxmlPath;
    }

    public void initStep(AnchorPane anchor, Window current, HBox parent) {
        this.anchor = anchor;
        this.window = current;
        this.stepHBox = new HBox();
        showStep(parent);
        setButtonClick();

        eventSource.addListener(EventType.STEP_EVENT_TYPE, this);
    }

    public void showStep(HBox parent) {
        renderStep();
        parent.getChildren().add(stepHBox);
        HBox.setHgrow(stepHBox, Priority.ALWAYS);
    }

    private void renderStep() {

        stepLabelButton.setText(stepNumber + "");
        stepNameButton.setText(stepName);

        //TODO: Style Buttons and set appropriate FXML to load when clicked
        stepLabelButton.setPrefHeight(70);
        stepLabelButton.setPrefWidth(70);
        stepLabelButton.setMinHeight(70);
        stepLabelButton.setMinWidth(70);
        stepLabelButton.setMaxHeight(70);
        stepLabelButton.setMaxWidth(70);
        stepLabelButton.setTextAlignment(TextAlignment.CENTER);
        stepLabelButton.setAlignment(Pos.CENTER);
        stepLabelButton.getStyleClass().add("stepLabel");

        stepNameButton.setPrefHeight(30);
        stepNameButton.setPrefWidth(1000);
        stepNameButton.setMaxWidth(1000);
        stepNameButton.setAlignment(Pos.BASELINE_LEFT);
        stepNameButton.getStyleClass().add("stepName");

        fillerButton.setPrefHeight(30);
        fillerButton.setPrefWidth(30);
        fillerButton.setMaxWidth(30);
        fillerButton.getStyleClass().add("filler");

        stepHBox.getChildren().addAll(fillerButton, stepLabelButton, stepNameButton);
        HBox.setHgrow(fillerButton, Priority.SOMETIMES);
        HBox.setHgrow(stepLabelButton, Priority.NEVER);
        HBox.setHgrow(stepNameButton, Priority.ALWAYS);
        stepHBox.alignmentProperty().setValue(Pos.CENTER);
    }

    public void loadStep(AnchorPane anchor, Window current) {
        Window stepWindow = new Window(fxmlPath);
        stepWindow.setAsAnchorPane(anchor, current);
    }

    public void setButtonClick() {
        stepLabelButton.setOnAction(e -> eventSource.fire(EventType.WIZARD_EVENT_EVENT_TYPE, new WizardEvent(stepNumber)));
        stepNameButton.setOnAction(e -> eventSource.fire(EventType.WIZARD_EVENT_EVENT_TYPE, new WizardEvent(stepNumber)));
        fillerButton.setOnAction(e -> eventSource.fire(EventType.WIZARD_EVENT_EVENT_TYPE, new WizardEvent(stepNumber)));
    }

    @Override
    public void handle(StepEvent event) {
        stepHBox.getStyleClass().removeAll("previousStep", "currentStep", "nextStep");
        if (event.getStepNumber() == stepNumber) {
            stepHBox.getStyleClass().add("currentStep");
            loadStep(anchor, window);
        } else if (event.getStepNumber() > stepNumber) {
            stepHBox.getStyleClass().add("previousStep");
        } else if (event.getStepNumber() < stepNumber) {
            stepHBox.getStyleClass().add("nextStep");
        }
    }
}
