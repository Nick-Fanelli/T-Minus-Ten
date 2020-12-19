package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.GameObjectContainer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.tMinusTen.chunk.Chunk;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlanetScene extends Scene {

    public PlanetScene() { super("Planet Scene"); }

    @Override
    public void onCreate() {
        super.onCreate();

        Camera.position.set(0, -500);
//        player = new Player();
//        player.transform.position.set(0, -100);
//        super.addGameObject(player);

        SpriteSheet spriteSheet = Assets.addSpriteSheet("assets/spritesheets/ground/grasstileset.png", 30, 30);
        Chunk chunk = new Chunk(spriteSheet, 0, 0, 30, 30, 15, 15);

        chunk.enable();

        GameObjectContainer chunkContainer = new GameObjectContainer("ChunkContainer", this);
        chunkContainer.addGameObject(chunk);

        super.addGameObject(chunkContainer);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Input.isKey(KeyEvent.VK_LEFT)) Camera.position.x -= 100 * deltaTime;
        if(Input.isKey(KeyEvent.VK_RIGHT)) Camera.position.x += 100 * deltaTime;
        if(Input.isKey(KeyEvent.VK_UP)) Camera.position.y -= 100 * deltaTime;
        if(Input.isKey(KeyEvent.VK_DOWN)) Camera.position.y += 100 * deltaTime;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

}
