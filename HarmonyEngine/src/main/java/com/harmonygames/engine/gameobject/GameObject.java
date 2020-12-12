package com.harmonygames.engine.gameobject;

import com.harmonygames.engine.utils.Transform;

import java.awt.*;
import java.util.ArrayList;

public abstract class GameObject {

    private final String name;
    public Transform transform;

    protected ArrayList<Component> components = new ArrayList<>();

    public GameObject(String name) {
        this(name, new Transform());
    }

    public GameObject(String name, Transform transform) {
        this.name = name;
        this.transform = transform;
    }

    public void addComponent(Component component) {
        this.components.add(component);
    }

    public void removeComponent(Component component) {
        if(components.contains(component)) components.remove(component);
    }

    public String getName() { return this.name; }

    public void onCreate() { }
    public void update(float deltaTime) { }
    public void draw(Graphics2D g) { }
    public void onDestroy() { }

}
