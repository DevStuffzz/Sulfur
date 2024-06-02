package com.sulfurengine.util;

public class Vec2 {
    public float x, y;

    // Default constructor initializing vector to (0,0)
    public Vec2() {
        this(0, 0);
    }

    // Constructor initializing vector to (x, y)
    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Method to add two vectors
    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    // Method to subtract another vector from this vector
    public Vec2 subtract(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }

    // Method to multiply this vector by another vector component-wise
    public Vec2 multiply(Vec2 vec) {
        return new Vec2(this.x * vec.x, this.y * vec.y);
    }

    // Method to multiply this vector by a scalar
    public Vec2 multiply(float scalar) {
        return new Vec2(this.x * scalar, this.y * scalar);
    }

    // Method to calculate the dot product of this vector and another vector
    public float dot(Vec2 other) {
        return this.x * other.x + this.y * other.y;
    }

    // Method to normalize this vector (make it unit length)
    public Vec2 normalize() {
        float magnitude = magnitude();
        if (magnitude != 0) {
            return new Vec2(x / magnitude, y / magnitude);
        } else {
            return new Vec2(0, 0);
        }
    }

    // Method to divide this vector by a scalar
    public Vec2 div(float scalar) {
        if (scalar == 0) {
        	return new Vec2(0, 0);
        }
        return new Vec2(this.x / scalar, this.y / scalar);
    }

    // Method to calculate the magnitude of this vector
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    // Overriding the toString method for better debugging and logging
    @Override
    public String toString() {
        return "Vec2(" + x + ", " + y + ")";
    }

    // Method to perform spherical linear interpolation between this vector and another vector
    public Vec2 slerp(Vec2 target, float t) {
        // Clamp t between 0 and 1
        t = Math.max(0, Math.min(1, t));

        // Compute the dot product between the vectors
        float dot = this.dot(target);

        // Clamp dot product to avoid numerical errors
        dot = Math.max(-1.0f, Math.min(1.0f, dot));

        // Compute the angle between the vectors
        float theta = (float) Math.acos(dot) * t;

        // Compute the relative vector
        Vec2 relativeVec = target.subtract(this.multiply(dot)).normalize();

        // Interpolate and return the result
        return this.multiply((float) Math.cos(theta)).add(relativeVec.multiply((float) Math.sin(theta)));
    }
}
