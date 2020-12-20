package com.harmonygames.engine.event;

import java.util.ArrayList;

public class EventSystem {

    private final ArrayList<Event> events = new ArrayList<>();

    public void subscribe(Event event) {
        this.events.add(event);
    }

    public void unsubscribe(Event event) {
        this.events.remove(event);
    }

    public void callEvent() {
        for(Event event : events) event.onEvent();
    }

}
