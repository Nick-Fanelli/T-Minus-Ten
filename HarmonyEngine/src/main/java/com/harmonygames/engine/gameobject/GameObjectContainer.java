package com.harmonygames.engine.gameobject;

import com.harmonygames.engine.scene.Scene;

import java.awt.*;
import java.util.ArrayList;

public class GameObjectContainer extends GameObject {

    private final Scene scene;

    private final ArrayList<GameObject> gameObjects = new ArrayList<>();

    public GameObjectContainer(String name, Scene scene) {
        super(name);
        this.scene = scene;
    }

    public void addGameObject(GameObject gameObject) {
        gameObject.setScene(scene);
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
        gameObject.setScene(null);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(GameObject gameObject : gameObjects) { if(gameObject.isEnabled()) gameObject.update(deltaTime); }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        for(GameObject gameObject : gameObjects) { if(gameObject.isEnabled()) gameObject.draw(g); }
    }
}
