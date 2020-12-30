package com.harmonygames.engine.event.events;

import com.harmonygames.engine.event.Event;

public interface GameObjectMovementEvent extends Event<GameObjectMovementAction> {
    @Override void onEvent(GameObjectMovementAction action);
}
