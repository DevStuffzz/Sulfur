package com.sulfurengine.physics;


import com.sulfurengine.util.Vec2;

public class RaycastResult {
    public boolean hit;
    public float distance;
    public Vec2 point;
    public Vec2 normal;

    public RaycastResult() {
        this.hit = false;
        this.distance = 0;
        this.point = new Vec2(0, 0);
        this.normal = new Vec2(0, 0);
    }
}
