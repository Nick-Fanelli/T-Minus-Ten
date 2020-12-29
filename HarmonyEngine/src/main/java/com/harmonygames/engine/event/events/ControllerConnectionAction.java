package com.harmonygames.engine.event.events;

import com.harmonygames.engine.event.EventAction;

public class ControllerConnectionAction extends EventAction {

    public final int controllerID;
    public final boolean isConnected;

    public ControllerConnectionAction(int controllerID, boolean isConnected) {
        this.controllerID = controllerID;
        this.isConnected = isConnected;
    }

}