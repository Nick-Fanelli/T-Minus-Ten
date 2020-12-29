package com.harmonygames.engine.event;

import java.util.ArrayList;

public class EventSystem<T extends Event<A>, A extends EventAction> {

    private final ArrayList<T> events = new ArrayList<>();

    public void subscribe(T event) {
        this.events.add(event);
    }

    public void unsubscribe(T event) {
        this.events.remove(event);
    }

    public void callEvent(A eventAction) {
        for(T event : events) event.onEvent(eventAction);
    }

}
