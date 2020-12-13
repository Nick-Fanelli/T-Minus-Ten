package com.harmonygames.engine.graphics;

public class Tileset {

    private SpriteSheet spriteSheet;
    private int startID;

    public Tileset(SpriteSheet spriteSheet, int startID) {
        this.spriteSheet = spriteSheet;
        this.startID = startID;
    }

    public int getStartID() { return this.startID; }
    public SpriteSheet getSpriteSheet() { return this.spriteSheet; }

}
