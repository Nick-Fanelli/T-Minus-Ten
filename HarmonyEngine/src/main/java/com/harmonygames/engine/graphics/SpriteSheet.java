package com.harmonygames.engine.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private final BufferedImage[] sprites;
    private final int spriteWidth, spriteHeight;
    private final int numCols, numRows;

    public SpriteSheet(BufferedImage image, int tileWidth, int tileHeight) {
        this.spriteWidth = tileWidth;
        this.spriteHeight = tileHeight;
        this.numCols = image.getWidth() / tileWidth;
        this.numRows = image.getHeight() / tileHeight;

        this.sprites = new BufferedImage[numCols * numRows];

        for(int x = 0; x < numCols; x++) {
            for(int y = 0; y < numRows; y++) {
                BufferedImage sprite = image.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
                sprites[x + y * numCols] = sprite;
            }
        }
    }

    public BufferedImage getSprite(int x, int y) { return sprites[x + y * numCols]; }
    public BufferedImage getSprite(int i) { return sprites[i]; }

    public BufferedImage[] getSprites() { return this.sprites; }
    public int getSpriteWidth() { return this.spriteWidth; }
    public int getSpriteHeight() { return this.spriteHeight; }
    public int getNumCols() { return this.numCols; }
    public int getNumRows() { return this.numRows; }

}
