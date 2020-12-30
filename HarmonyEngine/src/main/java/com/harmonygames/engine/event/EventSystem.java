package com.harmonygames.engine.event;

import java.util.ArrayList;

public class EventSystem<E extends Event<A>, A extends EventAction> {

    private final ArrayList<E> events = new ArrayList<>();

    public void subscribe(E event) {
        this.events.add(event);
    }

    public void unsubscribe(E event) {
        this.events.remove(event);
    }

    public void callEvent(A eventAction) {
        for(E event : events) event.onEvent(eventAction);
    }

}
