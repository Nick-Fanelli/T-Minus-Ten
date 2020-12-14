package com.harmonygames.engine.graphics;

import com.harmonygames.engine.gameobject.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class RenderBatch {

    public final int zIndex;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    public RenderBatch(int zIndex) {
        this.zIndex = zIndex;
    }

    public void addGameObject(GameObject object) {
        if(object.getZIndex() != this.zIndex) {
            System.err.println("[Harmony Engine (Batch Renderer)]: Could not add game object with zIndex of '" + object.getZIndex() + "' to a batch " +
                    "renderer with a zIndex of '" + this.zIndex + "'");
            System.exit(-1);
        }

        object.setRenderBatch(this);
        this.gameObjects.add(object);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
        gameObject.setRenderBatch(null);
    }

    public void reassignGameObject(GameObject gameObject, RenderBatch renderBatch) {
        this.gameObjects.remove(gameObject);
        renderBatch.addGameObject(gameObject);
        gameObject.setRenderBatch(renderBatch);
    }

    public void update(float deltaTime) {
        for(GameObject gameObject : gameObjects) gameObject.update(deltaTime);
    }

    public void draw(Graphics2D g) {
        for(GameObject gameObject : gameObjects) gameObject.draw(g);
    }

    public ArrayList<GameObject> getGameObjects() { return this.gameObjects; }

}
