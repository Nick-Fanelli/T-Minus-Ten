package com.harmonygames.tMinusTen.chunk;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.PerlinNoise;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Chunk extends GameObject {

    public static final ChunkLoader chunkLoader = new ChunkLoader();
    public static final PerlinNoise heightNoiseMap = new PerlinNoise(100);

    public final int chunkX, chunkY;
    public final int tileWidth, tileHeight;
    public final int chunkWidth, chunkHeight;
    public final SpriteSheet spriteSheet;

    public ArrayList<GameObject> gameObjects = new ArrayList<>();
    private boolean hasLoaded = false;

    private boolean isEnabled = false;

    public Chunk(SpriteSheet spriteSheet, int chunkX, int chunkY, int tileWidth, int tileHeight, int chunkWidth, int chunkHeight) {
        super("Generated_Chunk", new Transform(new Vector2f(chunkX * (tileWidth * chunkWidth), chunkY * (tileHeight * chunkHeight)),
                new Scale(tileWidth * chunkWidth, tileHeight * chunkHeight)));
        this.spriteSheet = spriteSheet;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.chunkWidth = chunkWidth;
        this.chunkHeight = chunkHeight;
    }

    public void update(float deltaTime) {
        if(!isEnabled || !hasLoaded) return;
        for(GameObject gameObject : gameObjects) gameObject.update(deltaTime);
    }

    public void draw(Graphics2D g) {
        if(!isEnabled || !hasLoaded) return;
        for(GameObject gameObject : gameObjects) gameObject.draw(g);
    }

    public void load() {
        chunkLoader.loadChunk(this);
    }

    public void enable() {
        this.isEnabled = true;

        if(!hasLoaded) this.load();
    }

    public void disable() {
        this.isEnabled = false;
    }

    public boolean isEnabled() { return this.isEnabled; }

    public void setLoaded(boolean value) { this.hasLoaded = value; }
}
