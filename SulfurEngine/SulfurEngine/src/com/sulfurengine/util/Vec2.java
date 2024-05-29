package com.sulfurengine.util;

public class Vec2 {
    public float x, y;

    public Vec2() {
        this(0, 0);
    }

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    public Vec2 subtract(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }

    public Vec2 multiply(Vec2 vec) {
        return new Vec2(this.x * vec.x, this.y * vec.y);
    }

    public Vec2 multiply(float scalar) {
        return new Vec2(this.x * scalar, this.y * scalar);
    }

    public float dot(Vec2 other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vec2 normalize() {
        float magnitude = (float) Math.sqrt(x * x + y * y);
        if (magnitude != 0) {
            return new Vec2(x / magnitude, y / magnitude);
        } else {
            return new Vec2(0, 0);
        }
    }
}
