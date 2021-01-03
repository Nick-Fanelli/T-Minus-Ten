package com.harmonygames.tMinusTen.chunk;

public interface ChunkChange {

    void applyChange(Chunk chunk);

    class NewBlockChange implements ChunkChange {

        @Override
        public void applyChange(Chunk chunk) {

        }
    }

    class RemoveBlockChange implements ChunkChange {

        @Override
        public void applyChange(Chunk chunk) {

        }
    }
}
