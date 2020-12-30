package com.harmonygames.tMinusTen.chunk;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.gameobject.SimilarObjectContainer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.tMinusTen.objects.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChunkLoader implements Runnable {

    private final Scene scene;
    private final SpriteSheet spriteSheet;
    private final int tileWidth, tileHeight;
    private final int chunkWidth, chunkHeight;
    private final int xAnnexation, yAnnexation;

    private boolean hasLoaded = false;

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
        this.xAnnexation = (int) Math.ceil(Display.getResolution().getWidth() / (float) (tileWidth * chunkWidth)) + 1;
        this.yAnnexation = (int) Math.ceil(Display.getResolution().getHeight() / (float) (tileHeight * chunkHeight)) + 1;

//        waitingList.add(new Chunk(spriteSheet, chunks, 0, 0, tileWidth, tileHeight, chunkWidth, chunkHeight));

        thread.start();
    }

    private final HashMap<Vector2f, Boolean> chunksAddedMap = new HashMap<>();
    private Vector2f lastPosition = new Vector2f();

    @Override
    public void run() {
        while (thread.isAlive()) {

            if (!Camera.position.equals(lastPosition)) {
                Vector2f zeroPositionVector = new Vector2f((float) Math.floor(Camera.position.x / (chunkWidth * tileWidth)), (float) Math.floor(Camera.position.y / (chunkHeight * tileHeight)));

                for (int x = 0; x < xAnnexation; x++) {
                    for (int y = 0; y < yAnnexation; y++) {
                        chunksAddedMap.put(new Vector2f(zeroPositionVector).add(x, y), false);
                    }
                }

                for (Map.Entry<Vector2f, Boolean> entry : chunksAddedMap.entrySet()) {
                    for (int i = 0; i < chunks.getGameObjects().size(); i++) {
                        if(i >= chunks.getGameObjects().size() || chunks.getGameObjects().get(i) == null) continue;
                        if (entry.getKey().equals(chunks.getGameObjects().get(i).chunkX, chunks.getGameObjects().get(i).chunkY)) {
                            entry.setValue(true);
                            break;
                        }
                    }

                    if (!entry.getValue()) {
                        waitingList.add(new Chunk(spriteSheet, chunks, (int) entry.getKey().x, (int) entry.getKey().y, tileWidth, tileHeight, chunkWidth, chunkHeight));
                    }
                }

                lastPosition = Camera.position.copy();
            }

            for(int i = 0; i < waitingList.size(); i++) {
                Chunk chunk = waitingList.get(i);

                this.loadChunk(chunk);

                waitingList.remove(chunk);
                chunks.addGameObject(chunk);
                chunk.setLoaded(true);
            }

            if(!hasLoaded) hasLoaded = true;
            chunksAddedMap.clear(); // Clear the map

            // Free up some processing
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasLoaded() { return hasLoaded; }

    // ===================================================================
    // Chunk Loading Methods
    // ===================================================================

    private synchronized void loadChunk(Chunk chunk) {
        if(chunk.chunkY > Chunk.MAX_CHUNK_AMOUNT_HEIGHT) return;

        for(int x = 0; x < chunk.chunkWidth; x++) {
            int blockX = (chunk.chunkX * chunk.chunkWidth) + x;
            int columnHeight = (int) Math.round(Chunk.heightNoiseMap.noise(blockX));

            for(int y = 0; y < chunk.chunkHeight; y++) {
                int blockY = (chunk.chunkY * chunk.chunkHeight) + y;

                if(blockY >= columnHeight) {
                    if(blockY >= 10) {
                        if(Math.round(Chunk.caveNoiseMap.noise(blockX, blockY)) != 0) {
                            continue;
                        }
                    }

                    int absX = (chunk.chunkX * chunk.chunkWidth * chunk.tileWidth) + (x * chunk.tileWidth);
                    int absY = (chunk.chunkY * chunk.chunkHeight * chunk.tileHeight) + (y * chunk.tileHeight);
                    chunk.blocks.add(new Block("Generated_Block", new Transform(new Vector2f(absX, absY),
                            new Scale(chunk.tileWidth, chunk.tileHeight)), chunk.spriteSheet, Block.Type.SOIL));
                }
            }
        }
    }

}
