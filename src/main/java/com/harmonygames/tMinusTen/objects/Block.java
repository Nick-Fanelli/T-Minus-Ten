package com.harmonygames.tMinusTen.objects;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.renderer.SpriteRenderer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.Transform;

import java.awt.image.BufferedImage;

public class Block extends GameObject {

    public enum Type {
        GRASS(0, 1),
        DIRT(1, 1);

        public final int x, y;

        Type(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public final Type type;
    public final BufferedImage image;

    public Block(String name, Transform transform, SpriteSheet spriteSheet, Type type) {
        super(name, transform);

        this.type = type;
        this.image = spriteSheet.getSprite(this.type.x, this.type.y);
        this.addComponent(new SpriteRenderer(image));
    }
}
