package com.the.hugging.team.utils.wizard.events;

public final class EventType<E> {
    public final static EventType<SetCurrentStepEvent> SET_CURRENT_STEP_EVENT_TYPE = new EventType<>();
    public final static EventType<ChangeStepEvent> CHANGE_STEP_EVENT_TYPE = new EventType<>();
    public final static EventType<NextStepEvent> NEXT_STEP_EVENT_TYPE = new EventType<>();
    public final static EventType<PreviousStepEvent> PREVIOUS_STEP_EVENT_TYPE = new EventType<>();

    private EventType() {
    }
}
