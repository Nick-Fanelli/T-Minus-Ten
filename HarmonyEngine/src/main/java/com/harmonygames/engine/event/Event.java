package com.harmonygames.engine.event;

public interface Event<T extends EventAction> {

    void onEvent(T action);

}
