package com.harmonygames.engine.math;

public class Vector2f {

    public static final Vector2f ZERO = new Vector2f(0, 0);

    public float x;
    public float y;

    public Vector2f() {
        this(0f, 0f);
    }

    public Vector2f(Vector2f r) {
        this.x = r.x;
        this.y = r.y;
    }

    public Vector2f(float r) {
        this.x = r;
        this.y = r;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2f set(Vector2f r) {
        this.x = r.x;
        this.y = r.y;
        return this;
    }

    public void zero() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2f copy() {
        return new Vector2f(this.x, this.y);
    }

    public Vector2f toZero(float x, float y) {
        if(this.x > 0) this.x = Math.max(0.0f, this.x - x);
        else if(this.x < 0) this.x = Math.min(0.0f, this.x + x);
        if(this.y > 0) this.y = Math.max(0.0f, this.y - y);
        else if(this.y < 0) this.y = Math.min(0.0f, this.y + y);
        return this;
    }

    public Vector2f toZero(float r) {
        return this.toZero(r, r);
    }

    public boolean isZero() {
        return this.x == 0 && this.y == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector2f)) {
            System.err.println("[Harmony Engine]: Could not use .equals with " + obj + " and Vector2f");
            return false;
        }

        Vector2f r = (Vector2f) obj;
        return this.x == r.x && this.y == r.y;
    }

    public boolean equals(float x, float y) {
        return this.x == x && this.y == y;
    }

    public boolean equals(float value) { return this.x == value && this.y == value; }

    @Override
    public String toString() {
        return String.format("[Vector2f: X:%s, Y:%s]", this.x, this.y);
    }

    public Vector2f round() {
        this.x = Math.round(this.x);
        this.y = Math.round(this.y);
        return this;
    }

    public Vector2f clip(Vector2f min, Vector2f max) {
        return this.clip(min.x, max.x, min.y, max.y);
    }

    public Vector2f clip(float xMin, float xMax, float yMin, float yMax) {
        if(this.x < xMin) this.x = xMin;
        else if(this.x > xMax) this.x = xMax;

        if(this.y < yMin) this.y = yMin;
        else if(this.y > yMax) this.y = yMax;

        return this;
    }

    public Vector2f clip(float min, float max) {
        return this.clip(min, max, min, max);
    }

    public Vector2f add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2f sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2f mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2f div(float x, float y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    public Vector2f add(Vector2f r) {
        this.x += r.x;
        this.y += r.y;
        return this;
    }

    public Vector2f sub(Vector2f r) {
        this.x -= r.x;
        this.y -= r.y;
        return this;
    }

    public Vector2f mul(Vector2f r) {
        this.x *= r.x;
        this.y *= r.y;
        return this;
    }

    public Vector2f div(Vector2f r) {
        this.x /= r.x;
        this.y /= r.y;
        return this;
    }

    public Vector2f add(float r) {
        this.x += r;
        this.y += r;
        return this;
    }

    public Vector2f sub(float r) {
        this.x -= r;
        this.y -= r;
        return this;
    }

    public Vector2f mul(float r) {
        this.x *= r;
        this.y *= r;
        return this;
    }

    public Vector2f div(float r) {
        this.x /= r;
        this.y /= r;
        return this;
    }

    public Vector2f min(Vector2f r) {
        this.x = Math.min(this.x, r.x);
        this.y = Math.min(this.y, r.y);
        return this;
    }

    public Vector2f max(Vector2f r) {
        this.x = Math.max(this.x, r.x);
        this.y = Math.max(this.y, r.y);
        return this;
    }
}
