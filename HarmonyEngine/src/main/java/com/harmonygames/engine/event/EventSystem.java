package com.harmonygames.engine.event;

import java.util.ArrayList;

public class EventSystem<T extends Event> {

    private final ArrayList<T> events = new ArrayList<>();

    public void subscribe(T event) {
        this.events.add(event);
    }

    public void unsubscribe(T event) {
        this.events.remove(event);
    }

    public void callEvent() {
        for(Event event : events) event.onEvent();
    }

}
