package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.SimilarObjectContainer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.tMinusTen.chunk.Chunk;
import com.harmonygames.tMinusTen.chunk.ChunkLoader;
import com.harmonygames.tMinusTen.objects.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlanetScene extends Scene {

    private Player player;

    private SimilarObjectContainer<Chunk> chunkContainer;
    private ChunkLoader chunkLoader;
    private SpriteSheet spriteSheet;

    public PlanetScene() { super("Planet Scene"); }

    public static final int chunkWidth = 15, chunkHeight = 15, tileWidth = 30, tileHeight = 30;

    @Override
    public void onCreate() {
        super.onCreate();

        Camera.position.set(0, -500);
        player = new Player();
        player.transform.position.set(0, -200);
        super.addGameObject(player);

        spriteSheet = Assets.addSpriteSheet("/spritesheets/ground/grasstileset.png", 30, 30);
        chunkContainer = new SimilarObjectContainer<>("ChunkContainer", this);
        chunkLoader = new ChunkLoader(this, chunkContainer, spriteSheet, tileWidth, tileHeight, chunkWidth, chunkHeight);

        super.addGameObject(chunkContainer);
    }

    float fps = 0;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        fps = 1f / deltaTime;

//        if(Input.isKey(KeyEvent.VK_LEFT)) Camera.position.x -= 100 * deltaTime;
//        if(Input.isKey(KeyEvent.VK_RIGHT)) Camera.position.x += 100 * deltaTime;
//        if(Input.isKey(KeyEvent.VK_UP)) Camera.position.y -= 100 * deltaTime;
//        if(Input.isKey(KeyEvent.VK_DOWN)) Camera.position.y += 100 * deltaTime;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Display.getCanvas().getWidth(), Display.getCanvas().getHeight());
//
//        g.setColor(Color.WHITE);
//        g.drawString("FPS: " + fps, 10, 20);

        super.draw(g);
    }

}
