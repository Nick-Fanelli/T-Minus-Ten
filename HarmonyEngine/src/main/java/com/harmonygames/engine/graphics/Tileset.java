package com.harmonygames.engine.graphics;

import java.awt.image.BufferedImage;

public class Tileset {

    private SpriteSheet spriteSheet;
    private int startID;

    public Tileset(SpriteSheet spriteSheet, int startID) {
        this.spriteSheet = spriteSheet;
        this.startID = startID;
    }

}
