package com.the.hugging.team.utils.wizard;

import com.the.hugging.team.utils.Window;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class Wizard {
    private List<WizardStep> steps;
    private int currentStep;
    private AnchorPane mainAnchor;
    private AnchorPane childAnchor;
    private Window currentWindow;

    public Wizard(AnchorPane anchor, List<WizardStep> steps, Window currentWindow)
    {
        this.steps = steps;
        this.currentStep = 0;
        this.mainAnchor = anchor;
        this.currentWindow = currentWindow;

        this.childAnchor = new AnchorPane();
        this.childAnchor.setPrefWidth(anchor.getPrefWidth());
        this.childAnchor.setPrefHeight(anchor.getPrefHeight()-80);
    }

    public void setUpWizard()
    {
        HBox stepBox = new HBox();
        for (WizardStep wizardStep : steps)
        {
            wizardStep.initStep(childAnchor, currentWindow, stepBox, currentStep);
        }
        //TODO: Set up HBox
        AnchorPane.setTopAnchor(stepBox, 0.0);
        AnchorPane.setLeftAnchor(stepBox, 0.0);
        AnchorPane.setRightAnchor(stepBox, 0.0);
        stepBox.setPrefHeight(80);

        AnchorPane.setTopAnchor(childAnchor, 80.0);
        AnchorPane.setLeftAnchor(childAnchor, 0.0);
        AnchorPane.setRightAnchor(childAnchor, 0.0);
        AnchorPane.setBottomAnchor(childAnchor, 0.0);

        mainAnchor.getChildren().addAll(stepBox, childAnchor);
    }
}
