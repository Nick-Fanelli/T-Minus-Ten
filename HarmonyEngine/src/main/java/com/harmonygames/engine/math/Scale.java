package com.harmonygames.engine.math;

public class Scale {

    public int width;
    public int height;

    public Scale(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Scale() {
        this(0, 0);
    }

    public Scale copy() {
        return new Scale(this.width, this.height);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Scale)) {
            System.err.println("[Harmony Engine]: Could not use .equals with " + obj + " and Scale");
            return false;
        }

        Scale scale = (Scale) obj;
        return this.width == scale.width && this.height == scale.height;
    }

    @Override
    public String toString() {
        return String.format("[Scale: width: %s, height: %s]", this.width, this.height);
    }
}
