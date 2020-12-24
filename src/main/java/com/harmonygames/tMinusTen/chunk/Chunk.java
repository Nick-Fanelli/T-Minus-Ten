package com.harmonygames.tMinusTen.chunk;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.SimilarObjectContainer;
import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.PerlinNoise;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.tMinusTen.objects.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Chunk extends GameObject {

    public static final PerlinNoise heightNoiseMap = new PerlinNoise(100, 10);

    public final int chunkX, chunkY;
    public final int tileWidth, tileHeight;
    public final int chunkWidth, chunkHeight;
    public final SpriteSheet spriteSheet;

    public ArrayList<Block> blocks = new ArrayList<>();
    private boolean hasLoaded = false;

    private final SimilarObjectContainer<Chunk> objectContainer;

    public Chunk(SpriteSheet spriteSheet, SimilarObjectContainer<Chunk> objectContainer, int chunkX, int chunkY, int tileWidth, int tileHeight, int chunkWidth, int chunkHeight) {
        super("Generated_Chunk", new Transform(new Vector2f(chunkX * (tileWidth * chunkWidth), chunkY * (tileHeight * chunkHeight)),
                new Scale(tileWidth * chunkWidth, tileHeight * chunkHeight)));
        this.spriteSheet = spriteSheet;
        this.objectContainer = objectContainer;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.chunkWidth = chunkWidth;
        this.chunkHeight = chunkHeight;
    }

    public void update(float deltaTime) {
        if(!hasLoaded) return;
        if(!Camera.shouldDrawTransform(super.transform)) objectContainer.removeGameObject(this);
        for(Block block : blocks) block.update(deltaTime);
    }

    public void draw(Graphics2D g) {
        if(!hasLoaded) return;
        for(Block block : blocks) block.draw(g);
    }

    public void setLoaded(boolean value) { this.hasLoaded = value; }

    @Override
    public <T extends Component> GameObject[] getSelf(Class<T> component) {
        ArrayList<GameObject> selectedObjects = new ArrayList<>();

        for(GameObject gameObject : blocks) {
            selectedObjects.addAll(Arrays.asList(gameObject.getSelf(component)));
        }

        return selectedObjects.toArray(new GameObject[0]);
    }
}
