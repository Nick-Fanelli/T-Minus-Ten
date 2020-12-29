package com.harmonygames.tMinusTen.objects;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.renderer.SpriteRenderer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.engine.physics2D.components.BoxCollider2D;
import com.harmonygames.engine.utils.ImageUtils;

import java.awt.image.BufferedImage;

public class Block extends GameObject {

    public enum Type {
        SOIL(0, 0),
        SAMPLE(2, 1);

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

        SpriteRenderer spriteRenderer = new SpriteRenderer(image);
        spriteRenderer.setDrawToObjectSize(true);

        this.addComponent(spriteRenderer);
        this.addComponent(new BoxCollider2D(new Vector2f(), this.transform.scale));
    }
}
