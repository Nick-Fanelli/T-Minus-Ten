package com.harmonygames.engine.gameobject;

import com.harmonygames.engine.scene.Scene;

import java.awt.*;
import java.util.ArrayList;

public class SimilarObjectContainer<T extends GameObject> extends GameObject {

    private final ArrayList<T> gameObjects = new ArrayList<>();
    private final Scene scene;

    public SimilarObjectContainer(String name, Scene scene) {
        super(name);
        this.scene = scene;
    }

    public void addGameObject(T gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(T gameObject) {
        gameObjects.remove(gameObject);
    }

    public ArrayList<T> getGameObjects() { return this.gameObjects; }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(T gameObject : gameObjects) { if(gameObject.isEnabled()) gameObject.update(deltaTime); }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        for(T gameObject : gameObjects) { if(gameObject.isEnabled()) gameObject.draw(g); }
    }

}
