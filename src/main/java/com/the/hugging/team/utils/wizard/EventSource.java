package com.the.hugging.team.utils.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface Listener<E extends BaseEvent> {
    void handle(E event);
}

class BaseEvent {
}

class WizardEvent extends BaseEvent {
    int currentStep;

    public WizardEvent(int currentStep) {
        this.currentStep = currentStep;
    }
}

class StepEvent extends BaseEvent {
    int stepNumber;

    public StepEvent(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int getStepNumber() {
        return stepNumber;
    }
}

final class EventType<E> {
    public final static EventType<WizardEvent> WIZARD_EVENT_EVENT_TYPE = new EventType<>();
    public final static EventType<StepEvent> STEP_EVENT_TYPE = new EventType<>();

    private EventType() {
    }
}

public class EventSource {
    private static EventSource instance = null;
    private final Map<EventType, List<Listener<? extends BaseEvent>>> listenersMap = new HashMap<>();

    public static EventSource getInstance() {
        if (instance == null) {
            instance = new EventSource();
        }
        return instance;
    }

    public <E extends BaseEvent> void addListener(EventType<E> eventType, Listener<E> listener) {
        listeners(eventType).add(listener);
    }

    public <E extends BaseEvent> void fire(EventType<E> eventType, E event) {
        for (Listener listener : listeners(eventType)) {
            listener.handle(event);
        }
    }

    private List<Listener<? extends BaseEvent>> listeners(EventType eventType) {
        if (listenersMap.containsKey(eventType)) {
            return listenersMap.get(eventType);
        } else {
            List<Listener<? extends BaseEvent>> listenersList = new ArrayList();
            listenersMap.put(eventType, listenersList);
            return listenersList;
        }
    }

}


