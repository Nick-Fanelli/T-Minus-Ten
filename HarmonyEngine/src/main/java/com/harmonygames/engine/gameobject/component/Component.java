package com.harmonygames.engine.gameobject.component;

import com.harmonygames.engine.gameobject.GameObject;

import java.awt.*;

public abstract class Component {

    protected GameObject gameObject = null;

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public abstract void update(float deltaTime);
    public abstract void draw(Graphics2D g);

    public GameObject getGameObject() { return this.gameObject; }

}
