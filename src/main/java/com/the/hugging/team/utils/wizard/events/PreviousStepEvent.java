package com.the.hugging.team.utils.wizard.events;

public class PreviousStepEvent implements BaseEvent {
    public PreviousStepEvent() {
    }

    @Override
    public EventType<?> getEventType() {
        return EventType.PREVIOUS_STEP_EVENT_TYPE;
    }
}
