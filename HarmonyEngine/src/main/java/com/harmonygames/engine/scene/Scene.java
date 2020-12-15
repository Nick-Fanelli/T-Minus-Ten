package com.harmonygames.engine.scene;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.graphics.RenderBatch;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public abstract class Scene {

    public final String sceneName;

    private final TreeMap<Integer, RenderBatch> renderBatches = new TreeMap<>();

    public Scene(String sceneName) {
        this.sceneName = sceneName;
    }

    public void onCreate() { }

    public void update(float deltaTime) {
        for(Map.Entry<Integer, RenderBatch> batch : renderBatches.entrySet()) {
            batch.getValue().update(deltaTime);
        }
    }

    public void draw(Graphics2D g) {
        for(Map.Entry<Integer, RenderBatch> batch : renderBatches.entrySet()) {
            batch.getValue().draw(g);
        }
    }

    public void onDestroy() { }

    public void addGameObject(GameObject gameObject) {
        gameObject.setScene(this);
        gameObject.onCreate();

        RenderBatch renderBatch = renderBatches.get(gameObject.getZIndex());

        if(renderBatch != null) {
            renderBatch.addGameObject(gameObject);
        } else {
            renderBatch = new RenderBatch(gameObject.getZIndex());
            renderBatch.addGameObject(gameObject);
            renderBatches.put(renderBatch.zIndex, renderBatch);
        }

    }

    public void removeGameObject(GameObject gameObject) {
        RenderBatch batch = renderBatches.get(gameObject.getZIndex());
        batch.removeGameObject(gameObject);

        gameObject.onDestroy();
        gameObject.setScene(null);
    }

    public void addRenderBatch(RenderBatch renderBatch) {
        renderBatches.put(renderBatch.zIndex, renderBatch);
    }

    public TreeMap<Integer, RenderBatch> getRenderBatches() { return renderBatches; }
}
