package com.the.hugging.team.utils.wizard.steps;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.utils.wizard.WizardStep;

import java.util.ArrayList;
import java.util.List;

public class SelectProductStep extends WizardStep {
    List<Product> productList = new ArrayList<>();

    public SelectProductStep(int stepNumber, String stepName, String fxmlPath) {
        super(stepNumber, stepName, fxmlPath);
    }
}
