package com.harmonygames.tMinusTen.chunk;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.SimilarObjectContainer;
import com.harmonygames.engine.gameobject.component.renderer.SpriteRenderer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.engine.physics2D.components.BoxCollider2D;
import com.harmonygames.engine.scene.Scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChunkLoader implements Runnable {

    private final Scene scene;
    private final SpriteSheet spriteSheet;
    private final int tileWidth, tileHeight;
    private final int chunkWidth, chunkHeight;

    private final Thread thread = new Thread(this, "_TMinusTen:ChunkLoader_");
    private final ArrayList<Chunk> waitingList = new ArrayList<>();

    private SimilarObjectContainer<Chunk> chunks;

    public ChunkLoader(Scene scene, SimilarObjectContainer<Chunk> chunks, SpriteSheet spriteSheet,
                       int tileWidth, int tileHeight, int chunkWidth, int chunkHeight) {
        this.scene = scene;
        this.chunks = chunks;
        this.spriteSheet = spriteSheet;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.chunkWidth = chunkWidth;
        this.chunkHeight = chunkHeight;

        waitingList.add(new Chunk(spriteSheet, chunks, 0, 0, tileWidth, tileHeight, chunkWidth, chunkHeight));

        thread.start();
    }

    private HashMap<Vector2f, Boolean> chunksAddedMap = new HashMap<>();

    @Override
    public void run() {
        while (thread.isAlive()) {

//            chunksAddedMap.put(new Vector2f(()))

            for (int i = 0; i < waitingList.size(); i++) {
                Chunk chunk = waitingList.get(i);

                for (int x = 0; x < chunk.chunkWidth; x++) {
                    int yOffset;

                    // FIXME: Clip the range so the yOffset can not be greater than the chunk height!!
                    if (chunk.chunkY == 0)
                        yOffset = (int) Math.round(Chunk.heightNoiseMap.noise((chunk.chunkX * chunk.chunkWidth) + x) * 10f);
                    else yOffset = (chunk.chunkY * chunk.chunkHeight);

                    int xPos = (chunk.chunkX * (chunk.chunkWidth * chunk.tileWidth)) + chunk.tileWidth * x;

                    GameObject gameObject = new GameObject("Generate_Tile", new Transform(
                            new Vector2f(xPos, yOffset * chunk.tileHeight), new Scale(chunk.tileWidth, chunk.tileHeight)
                    ));

                    gameObject.addComponent(new SpriteRenderer(chunk.spriteSheet.getSprite(chunk.chunkY == 0 ? 0 : 1, 1)));
                    gameObject.addComponent(new BoxCollider2D(new Vector2f(), gameObject.transform.scale));

                    chunk.gameObjects.add(gameObject);

                    int heightInverseAdjustment = chunk.chunkHeight != yOffset ? yOffset : 0;

                    for (int y = 0; y < chunk.chunkHeight - 1 - heightInverseAdjustment; y++) {
                        GameObject gameObject1 = new GameObject("Generated_Tile", new Transform(
                                new Vector2f(xPos, (yOffset + 1 + y) * chunk.tileHeight), new Scale(chunk.tileWidth, chunk.tileHeight)
                        ));
                        gameObject1.addComponent(new SpriteRenderer(chunk.spriteSheet.getSprite(1, 1)));
                        gameObject1.addComponent(new BoxCollider2D(new Vector2f(), gameObject1.transform.scale));

                        chunk.gameObjects.add(gameObject1);
                    }
                }

                chunk.setLoaded(true);
                waitingList.remove(chunk);
                chunks.addGameObject(chunk);
            }

            chunksAddedMap.clear(); // Clear the map

            // Free up some processing
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
