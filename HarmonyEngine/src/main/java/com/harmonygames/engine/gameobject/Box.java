package com.harmonygames.engine.gameobject;

import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;

import java.awt.*;

public class Box extends GameObject {

    private Type type;
    private Color fillColor;
    private Color strokeColor;

    public enum Type { STROKED, FILLED, STROKED_AND_FILLED }

    public Box(String name, Transform transform) {
        this(name, transform, Color.BLACK);
    }

    public Box(String name, Transform transform, Color fillColor) {
        this(name, transform, fillColor, Color.BLACK);
    }

    public Box(String name, Transform transform, Color fillColor, Color strokeColor) {
        this(name, transform, fillColor, strokeColor, Type.FILLED);
    }

    public Box(String name, Transform transform, Color fillColor, Color strokeColor, Type type) {
        super(name, transform);
        this.fillColor  = fillColor;
        this.strokeColor = strokeColor;
        this.type = type;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        Vector2f offset = this.getCameraOffset();

        if(type != Type.STROKED) {
            g.setColor(fillColor);
            g.fillRect((int) (this.transform.position.x + offset.x), (int) (this.transform.position.y + offset.y),
                    this.transform.scale.width, this.transform.scale.height);
        }

        if(type == Type.STROKED || type == Type.STROKED_AND_FILLED) {
            g.setColor(strokeColor);
            g.drawRect((int) (this.transform.position.x + offset.x), (int) (this.transform.position.y + offset.y),
                    this.transform.scale.width, this.transform.scale.height);
        }
    }

    public Color getFillColor() { return this.fillColor; }
    public void setFillColor(Color fillColor) { this.fillColor = fillColor; }

    public Color getStrokeColor() { return this.strokeColor; }
    public void setStrokeColor(Color strokeColor) { this.strokeColor = strokeColor; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}
