package com.harmonygames.tMinusTen.chunk;

import com.harmonygames.tMinusTen.objects.Block;

public abstract class ChunkChange {

    // Block to store
    protected Block storedBlock;

    // Abstract Chunk Change Constructor
    public ChunkChange() { } // No Arg-Constructor for GSON Serialization

    public void setStoredBlock(Block block) { this.storedBlock = block; }

    // Abstract Method to Override
    public abstract void applyChange(Chunk chunk);

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ChunkChange)) return false;
        return ((ChunkChange) obj).storedBlock.transform.equals(this.storedBlock.transform);
    }

    // ==================================================================================
    // Nested Chunk Change Classes
    // ==================================================================================

    public static class NewBlockChange extends ChunkChange {
        @Override
        public void applyChange(Chunk chunk) {
            if(storedBlock == null) {
                System.err.println("Stored Block is null");
                return;
            }
            chunk.blocks.add(storedBlock);
        }
    }

    public static class RemoveBlockChange extends ChunkChange {
        @Override
        public void applyChange(Chunk chunk) {
            if(storedBlock == null) {
                System.err.println("Stored Block is null");
                return;
            }
            chunk.removeBlock(storedBlock);
        }
    }
}
