package com.sulfurengine.renderer;

import com.sulfurengine.util.Vec2;

public class Camera {
    public Vec2 position;

    public Camera(Vec2 position) {
        this.position = position;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }
}
