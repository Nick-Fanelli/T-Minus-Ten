package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.SpriteRenderer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;

public class GameScene extends Scene {

    private SpriteSheet spriteSheet;
    private GameObject gameObject;

    @Override
    public void onCreate() {
        super.onCreate();

        spriteSheet = Assets.addSpriteSheet("assets/tilesets/space-station.png", 5, 5);

        gameObject = new GameObject("GameObject");
        gameObject.addComponent(new SpriteRenderer(spriteSheet.getSprite(0, 0)));

        super.addGameObject(gameObject);
    }

    double startTime = 0;
    int index = 0;

    @Override
    public void update(float deltaTime) {
        gameObject.transform.position.x += deltaTime * 250f;
//        if(System.currentTimeMillis() - startTime >= 200) {
//            if(index <= 3) index++;
//            else index = 0;
//
//            gameObject.getComponent(SpriteRenderer.class).setImage(spriteSheet.getSprite(index, 0));
//
//            startTime = System.currentTimeMillis();
//        }
    }
}
