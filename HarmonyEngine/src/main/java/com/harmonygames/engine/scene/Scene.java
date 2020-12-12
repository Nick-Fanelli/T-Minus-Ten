package com.harmonygames.engine.scene;

import com.harmonygames.engine.gameobject.GameObject;

import java.awt.*;
import java.util.ArrayList;

public abstract class Scene {

    private final ArrayList<GameObject> gameObjects = new ArrayList<>();

    public void onCreate() { }

    public void update(float deltaTime) {
        for(GameObject gameObject : gameObjects) gameObject.update(deltaTime);
    }

    public void draw(Graphics2D g) {
        for(GameObject gameObject : gameObjects) gameObject.draw(g);
    }

    public void onDestroy() { }

    public void addGameObject(GameObject gameObject) {
        gameObject.onCreate();
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        if(!gameObjects.contains(gameObject)) return;
        gameObjects.remove(gameObject);
        gameObject.onDestroy();
    }

    public ArrayList<GameObject> getGameObjects() { return this.gameObjects; }
}
