package com.harmonygames.engine.gameobject;

import com.harmonygames.engine.math.Transform;

public class Tile extends GameObject {

    public Tile(String name) {
        super(name);
    }

    public Tile(String name, Transform transform) {
        super(name, transform);
    }

}
