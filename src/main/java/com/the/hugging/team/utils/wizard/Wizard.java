package com.the.hugging.team.utils.wizard;

import com.the.hugging.team.utils.Window;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class Wizard implements Listener<WizardEvent> {
    private final List<WizardStep> steps;
    private final int currentStep;
    private final AnchorPane mainAnchor;
    private final AnchorPane childAnchor;
    private final Window currentWindow;
    private final EventSource eventSource = EventSource.getInstance();

    public Wizard(AnchorPane anchor, List<WizardStep> steps, Window currentWindow) {
        this.steps = steps;
        this.currentStep = 1;
        this.mainAnchor = anchor;
        this.currentWindow = currentWindow;

        this.childAnchor = new AnchorPane();
        this.childAnchor.setPrefWidth(anchor.getPrefWidth());
        this.childAnchor.setPrefHeight(anchor.getPrefHeight() - 80);
    }

    public void setUpWizard() {
        HBox stepBox = new HBox();
        for (WizardStep wizardStep : steps) {
            wizardStep.initStep(childAnchor, currentWindow, stepBox);
        }

        AnchorPane.setTopAnchor(stepBox, 0.0);
        AnchorPane.setLeftAnchor(stepBox, 0.0);
        AnchorPane.setRightAnchor(stepBox, 0.0);
        stepBox.setPrefHeight(80);

        AnchorPane.setTopAnchor(childAnchor, 80.0);
        AnchorPane.setLeftAnchor(childAnchor, 0.0);
        AnchorPane.setRightAnchor(childAnchor, 0.0);
        AnchorPane.setBottomAnchor(childAnchor, 0.0);

        mainAnchor.getChildren().addAll(stepBox, childAnchor);

        eventSource.addListener(EventType.WIZARD_EVENT_EVENT_TYPE, this);
        eventSource.fire(EventType.STEP_EVENT_TYPE, new StepEvent(currentStep));
    }

    @Override
    public void handle(WizardEvent event) {
        System.out.println("Wizard Event: " + event.currentStep);

        eventSource.fire(EventType.STEP_EVENT_TYPE, new StepEvent(event.currentStep));
    }
}
