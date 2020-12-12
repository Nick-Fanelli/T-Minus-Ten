package com.harmonygames.engine.gameobject.component;

import com.harmonygames.engine.gameobject.GameObject;

import java.awt.*;

public abstract class Component {

    public final GameObject gameObject;

    public Component(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void update(float deltaTime) { }
    public void draw(Graphics2D g) { }

}
