package com.harmonygames.engine.utils;

public class Vector2f {

    public float x;
    public float y;

    public Vector2f() {
        this(0f, 0f);
    }

    public Vector2f(Vector2f r) {
        this.x = r.x;
        this.y = r.y;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2f r) {
        this.x = r.x;
        this.y = r.y;
    }

    public void zero() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2f copy() {
        return new Vector2f(this.x, this.y);
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

    @Override
    public String toString() {
        return String.format("[Vector2f: X:%s, Y:%s]", this.x, this.y);
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
}
