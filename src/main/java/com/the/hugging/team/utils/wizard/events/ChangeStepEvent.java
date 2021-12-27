package com.the.hugging.team.utils.wizard.events;

public class ChangeStepEvent implements BaseEvent {
    int stepNumber;

    public ChangeStepEvent(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    @Override
    public EventType<?> getEventType() {
        return EventType.CHANGE_STEP_EVENT_TYPE;
    }
}
