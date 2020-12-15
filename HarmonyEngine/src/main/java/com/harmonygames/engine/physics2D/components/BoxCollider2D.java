package com.harmonygames.engine.physics2D.components;

import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Vector2f;

import java.awt.*;

public class BoxCollider2D extends Component {

    private Vector2f offset;
    private Scale scale;

    public BoxCollider2D(Vector2f offset, Scale scale) {
        this.offset = offset;
        this.scale = scale;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void draw(Graphics2D g) {

    }

    public Vector2f getOffset() { return this.offset; }
    public Scale getScale() { return this.scale; }
}
