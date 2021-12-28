package com.the.hugging.team.utils.wizard;

import com.the.hugging.team.utils.Window;
import com.the.hugging.team.utils.wizard.events.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class Wizard implements Listener {
    private final List<WizardStep> steps;
    private final AnchorPane mainAnchor;
    private final AnchorPane childAnchor;
    private final Window currentWindow;
    private final EventSource eventSource = EventSource.getInstance();
    private int currentStep;

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

        eventSource.addListener(EventType.SET_CURRENT_STEP_EVENT_TYPE, this);
        eventSource.addListener(EventType.NEXT_STEP_EVENT_TYPE, this);
        eventSource.addListener(EventType.PREVIOUS_STEP_EVENT_TYPE, this);
        eventSource.fire(EventType.CHANGE_STEP_EVENT_TYPE, new ChangeStepEvent(currentStep));
    }

    @Override
    public void handle(BaseEvent event) {
        if (event.getEventType() == EventType.SET_CURRENT_STEP_EVENT_TYPE) {
            SetCurrentStepEvent changeStepEvent = (SetCurrentStepEvent) event;
            currentStep = changeStepEvent.currentStep;
        } else if (event.getEventType() == EventType.NEXT_STEP_EVENT_TYPE) {
            currentStep += 1;
        } else if (event.getEventType() == EventType.PREVIOUS_STEP_EVENT_TYPE) {
            currentStep -= 1;
        }

        eventSource.fire(EventType.CHANGE_STEP_EVENT_TYPE, new ChangeStepEvent(currentStep));
    }
}
