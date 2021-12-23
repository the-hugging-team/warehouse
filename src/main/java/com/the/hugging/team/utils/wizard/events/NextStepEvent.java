package com.the.hugging.team.utils.wizard.events;

public class NextStepEvent implements BaseEvent {
    public NextStepEvent() {
    }

    @Override
    public EventType<?> getEventType() {
        return EventType.NEXT_STEP_EVENT_TYPE;
    }
}
