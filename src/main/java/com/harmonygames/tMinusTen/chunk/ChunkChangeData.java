package com.harmonygames.tMinusTen.chunk;

import com.harmonygames.engine.math.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class ChunkChangeData {

    private static final HashMap<Vector2f, ChunkChange[]> modifiedChunks = new HashMap<>();

    public static void addChunk(Chunk chunk) {
        Vector2f position = new Vector2f(chunk.chunkX, chunk.chunkY);

        for(Map.Entry<Vector2f, ChunkChange[]> entry : modifiedChunks.entrySet()) {
            if(entry.getKey().equals(position)) {
                System.out.println("Hey");
                modifiedChunks.remove(entry.getKey());
                break;
            }
        }

        modifiedChunks.put(position, chunk.getChanges());
    }

}
