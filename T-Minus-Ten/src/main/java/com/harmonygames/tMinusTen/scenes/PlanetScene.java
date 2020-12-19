package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.SimilarObjectContainer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.tMinusTen.chunk.Chunk;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlanetScene extends Scene {

    private SimilarObjectContainer<Chunk> chunkContainer;
    private SpriteSheet spriteSheet;

    public PlanetScene() { super("Planet Scene"); }

    public static final int chunkWidth = 15, chunkHeight = 15, tileWidth = 30, tileHeight = 30;

    @Override
    public void onCreate() {
        super.onCreate();

        Camera.position.set(0, -500);
//        player = new Player();
//        player.transform.position.set(0, -100);
//        super.addGameObject(player);

        spriteSheet = Assets.addSpriteSheet("assets/spritesheets/ground/grasstileset.png", 30, 30);
        Chunk chunk = new Chunk(spriteSheet, 0, 0, tileWidth, tileHeight, chunkWidth, chunkHeight);
        Chunk c2 = new Chunk(spriteSheet, 1, 0, tileWidth, tileHeight, chunkWidth, chunkHeight);

        chunk.load();
        c2.load();

        chunkContainer = new SimilarObjectContainer<>("ChunkContainer", this);
        chunkContainer.addGameObject(chunk);
        chunkContainer.addGameObject(c2);

        super.addGameObject(chunkContainer);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Input.isKey(KeyEvent.VK_LEFT)) Camera.position.x -= 100 * deltaTime;
        if(Input.isKey(KeyEvent.VK_RIGHT)) Camera.position.x += 100 * deltaTime;
        if(Input.isKey(KeyEvent.VK_UP)) Camera.position.y -= 100 * deltaTime;
        if(Input.isKey(KeyEvent.VK_DOWN)) Camera.position.y += 100 * deltaTime;

        Vector2f targetChunk = new Vector2f((float) Math.floor(Camera.position.x / (chunkWidth * tileWidth)), (float) Math.floor(Camera.position.y / (chunkHeight * tileHeight)));
//        System.out.println(targetChunk);

        for(int i = 0; i < chunkContainer.getGameObjects().size(); i++) {
            Chunk chunk = chunkContainer.getGameObjects().get(i);
//
//            if(!targetChunk.equals(chunk.chunkX, chunk.chunkY)) {
//                chunkContainer.removeGameObject(chunk);
//
//                Chunk c = new Chunk(spriteSheet, (int) targetChunk.x, (int) targetChunk.y, tileWidth, tileHeight, chunkWidth, chunkHeight);
//                c.enable();
//
//                chunkContainer.addGameObject(c);
//                continue;
//            }

            if(!Camera.shouldDrawTransform(chunk.transform)) {
                chunkContainer.removeGameObject(chunk);
                continue;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

}
