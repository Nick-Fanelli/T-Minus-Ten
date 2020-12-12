package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.SpriteRenderer;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;

public class GameScene extends Scene {

    private GameObject gameObject;

    @Override
    public void onCreate() {
        super.onCreate();

        gameObject = new GameObject("GameObject");
        gameObject.addComponent(new SpriteRenderer(Assets.getImage("assets/tilesets/space-station.png")));

        super.addGameObject(gameObject);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
