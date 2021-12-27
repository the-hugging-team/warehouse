package com.the.hugging.team.utils.wizard.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventSource {
    private static EventSource instance = null;
    private final Map<EventType, List<Listener>> listenersMap = new HashMap<>();

    public static EventSource getInstance() {
        if (instance == null) {
            instance = new EventSource();
        }
        return instance;
    }

    public <E extends BaseEvent> void addListener(EventType<E> eventType, Listener listener) {
        List<Listener> listeners = listeners(eventType);
        String listenerName = listener.getClass().getName();

        for (int i = 0; i < listeners.size(); i++) {
            if(listeners.get(i).getClass().getName().equals(listenerName)) {
                listeners.remove(i);
            }
        }

        listeners.add(listener);
    }

    public <E extends BaseEvent> void fire(EventType<E> eventType, E event) {
        for (Listener listener : listeners(eventType)) {
            listener.handle(event);
        }
    }

    private List<Listener> listeners(EventType eventType) {
        if (listenersMap.containsKey(eventType)) {
            return listenersMap.get(eventType);
        } else {
            List<Listener> listenersList = new ArrayList<>();
            listenersMap.put(eventType, listenersList);
            return listenersList;
        }
    }
}


