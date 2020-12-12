package com.harmonygames.engine.gameobject;

import com.harmonygames.engine.utils.Transform;

import java.awt.*;

public abstract class GameObject {

    private final String name;
    public Transform transform;

    public GameObject(String name) {
        this(name, new Transform());
    }

    public GameObject(String name, Transform transform) {
        this.name = name;
        this.transform = transform;
    }

    public String getName() { return this.name; }

    public void onCreate() { }
    public void update(float deltaTime) { }
    public void draw(Graphics2D g) { }
    public void onDestroy() { }

}
