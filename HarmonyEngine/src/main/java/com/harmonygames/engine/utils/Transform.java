package com.harmonygames.engine.utils;

public class Transform {

    public Vector2f position;
    public Scale scale;

    public Transform() {
        this(new Vector2f(), new Scale());
    }

    public Transform(Vector2f position) {
        this(position, new Scale());
    }

    public Transform(Vector2f position, Scale scale) {
        this.position = position;
        this.scale = scale;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Transform)) {
            System.err.println("[Harmony Engine]: Could not use .equals with " + obj + " and Transform");
            return false;
        }

        Transform transform = (Transform) obj;
        return transform.position.equals(this.position) && transform.scale.equals(this.scale);
    }

    @Override
    public String toString() {
        return String.format("[Transform: %s, %s]", this.position.toString(), this.scale.toString());
    }
}
