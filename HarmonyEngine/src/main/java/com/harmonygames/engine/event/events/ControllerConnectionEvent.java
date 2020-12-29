package com.harmonygames.engine.event.events;

import com.harmonygames.engine.event.Event;

public interface ControllerConnectionEvent extends Event<ControllerConnectionAction> {
    void onEvent(ControllerConnectionAction action);
}
