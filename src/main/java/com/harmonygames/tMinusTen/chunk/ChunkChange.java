package com.harmonygames.tMinusTen.chunk;

import com.harmonygames.tMinusTen.objects.Block;

public abstract class ChunkChange {

    // Block to store
    protected Block storedBlock;

    // Abstract Chunk Change Constructor
    public ChunkChange(Block block) {
        this.storedBlock = block;
    }

    // Abstract Method to Override
    public abstract void applyChange(Chunk chunk);

    // ==================================================================================
    // Nested Chunk Change Classes
    // ==================================================================================

    public static class NewBlockChange extends ChunkChange {

        public NewBlockChange(Block block) { super(block); }

        @Override
        public void applyChange(Chunk chunk) {
            chunk.blocks.add(storedBlock);
        }
    }

    public static class RemoveBlockChange extends ChunkChange {

        public RemoveBlockChange(Block block) { super(block); }

        @Override
        public void applyChange(Chunk chunk) {
            chunk.removeBlock(storedBlock);
        }
    }
}
