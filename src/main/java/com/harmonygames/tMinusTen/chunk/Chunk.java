package com.harmonygames.tMinusTen.chunk;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.data.Serializer;
import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.SimilarObjectContainer;
import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.PerlinNoise;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.engine.physics2D.components.BoxCollider2D;
import com.harmonygames.tMinusTen.objects.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Chunk extends GameObject {

    public static final int MAX_CHUNK_AMOUNT_HEIGHT = 125;

    public static final double NOISE = 100;
    public static final PerlinNoise heightNoiseMap = new PerlinNoise(Chunk.NOISE, 10);
    public static final PerlinNoise caveNoiseMap = new PerlinNoise(Chunk.NOISE, 2);

    public final int chunkX, chunkY;
    public final int tileWidth, tileHeight;
    public final int chunkWidth, chunkHeight;
    public final SpriteSheet spriteSheet;

    public ArrayList<Block> blocks = new ArrayList<>();
    private boolean hasLoaded = false;

    private final ArrayList<ChunkChange> changes = new ArrayList<>();

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

        BoxCollider2D collider2D = new BoxCollider2D(new Vector2f(), transform.scale);
        collider2D.setCollidable(false);

        super.addComponent(collider2D);
    }

    public void update(float deltaTime) {
        if(!hasLoaded) return;
        if(!Camera.shouldDrawTransform(super.transform)) objectContainer.removeGameObject(this);
        for(int i = 0; i < blocks.size(); i++) blocks.get(i).update(deltaTime);
    }

    public void draw(Graphics2D g) {
        if(!hasLoaded) return;
        for(int i = 0; i < blocks.size(); i++) blocks.get(i).draw(g);
    }

    public void setLoaded(boolean value) { this.hasLoaded = value; }

    @Override
    public <T extends Component> GameObject[] getSelf(Class<T> component) {
        ArrayList<GameObject> selectedObjects = new ArrayList<>();

        for(int i = 0; i < blocks.size(); i++) {
            selectedObjects.addAll(Arrays.asList(blocks.get(i).getSelf(component)));
        }

        return selectedObjects.toArray(new GameObject[0]);
    }

    @Override
    public void onDestroy() {
        if(changes.size() > 0) {
            ChunkChangeData.addChunk(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Chunk)) return false;
        return ((Chunk) obj).chunkX == this.chunkX && ((Chunk) obj).chunkY == this.chunkY;
    }

    public void addNewBlock(Block block) {
        ChunkChange.NewBlockChange change = new ChunkChange.NewBlockChange();
        change.setStoredBlock(block);

        for(int i = 0; i < changes.size(); i++) {
            if(changes.get(i).equals(change)) changes.remove(changes.get(i));
        }

        this.changes.add(change);
        this.blocks.add(block);
    }

    public void removeBlock(Block block) {
        ChunkChange.RemoveBlockChange change = new ChunkChange.RemoveBlockChange();
        change.setStoredBlock(block);

        for(int i = 0; i < changes.size(); i++) {
            if(changes.get(i).equals(change)) changes.remove(changes.get(i));
        }

        this.changes.add(change);
        this.blocks.remove(block);
    }

    public ChunkChange[] getChanges() { return changes.toArray(new ChunkChange[0]); }

}
