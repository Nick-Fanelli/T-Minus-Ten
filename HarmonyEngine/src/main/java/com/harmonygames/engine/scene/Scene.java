package com.harmonygames.engine.scene;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.GameObjectContainer;
import com.harmonygames.engine.gameobject.SimilarObjectContainer;
import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.graphics.RenderBatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class Scene {

    public final String sceneName;

    private final ArrayList<RenderBatch> renderBatches = new ArrayList<>();

    public Scene(String sceneName) {
        this.sceneName = sceneName;
    }

    public void onCreate() { }

    public void update(float deltaTime) {
        for(RenderBatch batch : renderBatches) {
            batch.update(deltaTime);
        }
    }

    public void draw(Graphics2D g) {
        for(RenderBatch batch : renderBatches) {
            batch.draw(g);
        }
    }

    public void onDestroy() { }

    public void addGameObject(GameObject gameObject) {
        gameObject.setScene(this);
        gameObject.onCreate();

        RenderBatch renderBatch = null;

        for(RenderBatch rb : renderBatches) if(rb.zIndex == gameObject.getZIndex()) renderBatch = rb;

        if(renderBatch != null) {
            renderBatch.addGameObject(gameObject);
        } else {
            renderBatch = new RenderBatch(gameObject.getZIndex());
            renderBatch.addGameObject(gameObject);
            renderBatches.add(renderBatch);

        }

        this.sortRenderBatches();
    }

    public void removeGameObject(GameObject gameObject) {
        RenderBatch batch = renderBatches.get(gameObject.getZIndex());
        batch.removeGameObject(gameObject);

        gameObject.onDestroy();
        gameObject.setScene(null);

        this.sortRenderBatches();
    }

    public void addRenderBatch(RenderBatch renderBatch) {
        renderBatch.setScene(this);
        renderBatches.add(renderBatch);

        this.sortRenderBatches();
    }

    public void removeRenderBatch(RenderBatch renderBatch) {
        renderBatches.remove(renderBatch);
        renderBatch.setScene(null);

        this.sortRenderBatches();
    }

    public void sortRenderBatches() {
        Collections.sort(renderBatches);
    }

    public ArrayList<RenderBatch> getRenderBatches() { return renderBatches; }

    public <T extends Component> ArrayList<GameObject> getUnwrappedGameObjects(Class<T> componentClass) {
        ArrayList<GameObject> gameObjects = new ArrayList<>();

        for(RenderBatch renderBatch : renderBatches) {
            for(GameObject gameObject : renderBatch.getGameObjects()) {
                gameObjects.addAll(Arrays.asList(gameObject.getSelf(componentClass)));
            }
        }

        return gameObjects;
    }
}
