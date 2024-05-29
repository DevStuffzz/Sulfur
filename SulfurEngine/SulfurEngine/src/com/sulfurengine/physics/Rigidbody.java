package com.sulfurengine.physics;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.util.Vec2;
import java.util.ArrayList;
import java.util.List;

public class Rigidbody extends Script {
    public Vec2 velocity;
    public float mass;
    public float bounceFactor;
    private List<Entity> collidingEntities;

    // Gravity constant
    private static final float GRAVITY = -1.0f; // Adjust as needed

    @Override
    public void start() {
        velocity = new Vec2(0, 0);
        mass = 1;
        bounceFactor = 0f; // Adjust as needed
        collidingEntities = new ArrayList<>();
    }

    @Override
    public void update(float dt) {
        // Check if the Rigidbody is grounded before applying gravity
        boolean grounded = isGrounded();
        if (!grounded) {
            // Apply gravitational force
            applyForce(new Vec2(0, -mass * GRAVITY));
        }
        
        // Update position based on velocity
        parent.transform.pos = parent.transform.pos.add(new Vec2(velocity.x * dt, velocity.y * dt));
        
        // Clear the list of colliding entities before performing collision detection
        collidingEntities.clear();
        
        // Collision detection logic
        checkCollisions();
    }

    // Check if the Rigidbody is grounded (resting on a surface)
    private boolean isGrounded() {
        // Perform raycast from the bottom of the Rigidbody
        Vec2 rayOrigin = parent.transform.pos.add(new Vec2(0, -0.5f)); // Assuming the Rigidbody's origin is at its center
        Vec2 rayDirection = new Vec2(0, -1); // Ray direction pointing downwards
        float rayLength = 0.1f; // Length of the ray, adjust as needed
        
        // Cast the ray
        RaycastResult result = castRay(rayOrigin, rayDirection, rayLength);
        
        // Check if the ray hit anything
        return result.hit;
    }

    private RaycastResult castRay(Vec2 origin, Vec2 direction, float length) {
        // Iterate through all colliders in the scene
        List<Entity> entities = Display.currentScene.getAllEntities();
        RaycastResult closestHit = new RaycastResult(); // Initialize with no hit
        
        for (Entity entity : entities) {
            // Check if the entity has a BoxCollider component
            BoxCollider boxCollider = entity.getScript(BoxCollider.class);
            if (boxCollider != null) {
                // Perform ray-box intersection test
                RaycastResult hitResult = boxCollider.raycast(origin, direction, length);
                
                // Update closest hit if this hit is closer
                if (hitResult.hit && (closestHit.hit == false || hitResult.distance < closestHit.distance)) {
                    closestHit = hitResult;
                }
            }
        }
        
        return closestHit;
    }

    private void checkCollisions() {
        List<Entity> entities = Display.currentScene.getAllEntities(); // Get all entities in the scene
        
        for (Entity entity : entities) {
            if (entity != parent) { // Avoid self-collision
                // Check if the entity has a BoxCollider component
                BoxCollider boxCollider = entity.getScript(BoxCollider.class);
                
                if (boxCollider != null) { // Ensure the entity has a BoxCollider component
                    // Check for collision with the BoxCollider
                    if (boxCollider.isColliding(parent.transform)) {
                        // Collision detected, call onCollision method of the Rigidbody
                        parent.onCollision(entity);
                    }
                }
            }
        }
    }

    @Override
    public void onCollision(Entity other) {
        // Handle collision response here
        // Adjust position to prevent clipping
        adjustPosition(other);

        // Calculate the normal of collision
        Vec2 collisionNormal = other.transform.pos.subtract(parent.transform.pos).normalize();
        
        // Calculate new velocity using the reflection formula and apply the bounce factor
        float dotProduct = velocity.dot(collisionNormal);
        Vec2 reflection = collisionNormal.multiply(dotProduct * 2).subtract(velocity);
        
        // Apply the bounce factor
        velocity = reflection.multiply(bounceFactor);
    }

    private void adjustPosition(Entity other) {
        BoxCollider thisCollider = parent.getScript(BoxCollider.class);
        BoxCollider otherCollider = other.getScript(BoxCollider.class);

        if (thisCollider != null && otherCollider != null) {
            float thisHalfWidth = thisCollider.parent.transform.scale.x / 2.0f;
            float thisHalfHeight = thisCollider.parent.transform.scale.y / 2.0f;
            float otherHalfWidth = otherCollider.parent.transform.scale.x / 2.0f;
            float otherHalfHeight = otherCollider.parent.transform.scale.y / 2.0f;

            float deltaX = parent.transform.pos.x - other.transform.pos.x;
            float deltaY = parent.transform.pos.y - other.transform.pos.y;

            float intersectX = Math.abs(deltaX) - (thisHalfWidth + otherHalfWidth);
            float intersectY = Math.abs(deltaY) - (thisHalfHeight + otherHalfHeight);

            if (intersectX > intersectY) {
                // Resolve collision along x-axis
                if (deltaX > 0) {
                    parent.transform.pos.x = other.transform.pos.x + thisHalfWidth + otherHalfWidth;
                } else {
                    parent.transform.pos.x = other.transform.pos.x - thisHalfWidth - otherHalfWidth;
                }
                velocity.x = -velocity.x * bounceFactor; // Reflect velocity along x-axis
            } else {
                // Resolve collision along y-axis
                if (deltaY > 0) {
                    parent.transform.pos.y = other.transform.pos.y + thisHalfHeight + otherHalfHeight;
                } else {
                    parent.transform.pos.y = other.transform.pos.y - thisHalfHeight - otherHalfHeight;
                }
                velocity.y = -velocity.y * bounceFactor; // Reflect velocity along y-axis
            }
        }
    }

    // Getter method to retrieve the list of colliding entities
    public List<Entity> getCollidingEntities() {
        return collidingEntities;
    }

    // Method to apply a force to the Rigidbody
    public void applyForce(Vec2 force) {
        // Divide force by mass and add to velocity
        velocity = velocity.add(new Vec2(force.x / mass, force.y / mass));
    }
}

