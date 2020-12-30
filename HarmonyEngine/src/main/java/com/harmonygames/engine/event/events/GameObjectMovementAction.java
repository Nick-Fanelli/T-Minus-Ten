package com.harmonygames.engine.event.events;

import com.harmonygames.engine.event.EventAction;
import com.harmonygames.engine.math.Vector2f;

public class GameObjectMovementAction extends EventAction {

    public final String objectName;
    public final Vector2f deltaPosition;

    public GameObjectMovementAction(String objectName, Vector2f deltaPosition) {
        this.objectName = objectName;
        this.deltaPosition = deltaPosition;
    }

}
