package com.harmonygames.engine.gameobject.component;

import com.harmonygames.engine.gameobject.GameObject;

import java.awt.*;

public abstract class Component {

    public GameObject gameObject = null;

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void update(float deltaTime) { }
    public void draw(Graphics2D g) { }

}
