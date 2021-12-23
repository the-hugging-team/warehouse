package com.the.hugging.team.utils.wizard.events;

public class SetCurrentStepEvent implements BaseEvent {
    public int currentStep;

    public SetCurrentStepEvent(int currentStep) {
        this.currentStep = currentStep;
    }

    @Override
    public EventType<?> getEventType() {
        return EventType.SET_CURRENT_STEP_EVENT_TYPE;
    }
}
