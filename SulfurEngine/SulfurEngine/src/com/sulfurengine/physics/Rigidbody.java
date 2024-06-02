package com.sulfurengine.physics;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.behaviorscripts.PhysicsCollisionIgnore;
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
    private static final float GRAVITY = 1.0f; // Adjust as needed, positive because -y is up

    @Override
    public void start() {
        velocity = new Vec2(0, 0);
        mass = 1;
        bounceFactor = 0f; // Adjust as needed
        collidingEntities = new ArrayList<>();
    }

    @Override
    public void update(float dt) {
        boolean grounded = isGrounded();
        if (!grounded) {
            applyForce(new Vec2(0, mass * GRAVITY)); // Apply gravitational force
        }

        parent.transform.pos = parent.transform.pos.add(velocity.multiply(dt));

        collidingEntities.clear();
        
        checkCollisions();
    }

    private boolean isGrounded() {
        Vec2 rayOrigin = parent.transform.pos.add(new Vec2(0, 0.5f)); // Raycast upwards assuming -y is up
        Vec2 rayDirection = new Vec2(0, 1); // Ray direction pointing upwards
        float rayLength = 0.1f; // Length of the ray, adjust as needed
        
        RaycastResult result = castRay(rayOrigin, rayDirection, rayLength);
        
        return result.hit;
    }

    private RaycastResult castRay(Vec2 origin, Vec2 direction, float length) {
        List<Entity> entities = Display.currentScene.getAllEntities();
        RaycastResult closestHit = new RaycastResult();
        
        for (Entity entity : entities) {
            BoxCollider boxCollider = entity.getScript(BoxCollider.class);
            if (boxCollider != null) {
                RaycastResult hitResult = boxCollider.raycast(origin, direction, length);
                
                if (hitResult.hit && (!closestHit.hit || hitResult.distance < closestHit.distance)) {
                    closestHit = hitResult;
                }
            }
        }
        
        return closestHit;
    }

    private void checkCollisions() {
        List<Entity> entities = Display.currentScene.getAllEntities();
        
        for (Entity entity : entities) {
            if (entity != parent) {
                BoxCollider boxCollider = entity.getScript(BoxCollider.class);
                
                if (boxCollider != null) {
                    if (boxCollider.isColliding(parent.transform)) {
                        parent.onCollision(entity);
                    }
                }
            }
        }
    }

    @Override
    public void onCollision(Entity other) {
    	PhysicsCollisionIgnore pci = other.getScript(PhysicsCollisionIgnore.class);
    	if(pci != null) return;
        adjustPosition(other);

        Vec2 collisionNormal = calculateCollisionNormal(other);
        
        float dotProduct = velocity.dot(collisionNormal);
        Vec2 reflection = velocity.subtract(collisionNormal.multiply(dotProduct * 2));
        
        velocity = reflection.multiply(bounceFactor);

        applyFriction();
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
                if (deltaX > 0) {
                    parent.transform.pos.x = other.transform.pos.x + thisHalfWidth + otherHalfWidth + 0.01f; // Add a small bias
                } else {
                    parent.transform.pos.x = other.transform.pos.x - thisHalfWidth - otherHalfWidth - 0.01f; // Add a small bias
                }
                velocity.x = -velocity.x * bounceFactor;
            } else {
                if (deltaY > 0) {
                    parent.transform.pos.y = other.transform.pos.y + thisHalfHeight + otherHalfHeight + 0.01f; // Add a small bias
                } else {
                    parent.transform.pos.y = other.transform.pos.y - thisHalfHeight - otherHalfHeight - 0.01f; // Add a small bias
                }
                if(bounceFactor != 0.0f)
                    velocity.y = -velocity.y * bounceFactor;
            }
        }
    }

    private Vec2 calculateCollisionNormal(Entity other) {
        Vec2 normal = parent.transform.pos.subtract(other.transform.pos).normalize();
        return normal;
    }

    private void applyFriction() {
        // Basic friction model
        float frictionCoefficient = 0.5f; // Adjust as needed
        float normalForce = mass * GRAVITY;
        Vec2 frictionForce = velocity.normalize().multiply(-frictionCoefficient * normalForce);
        
        applyForce(frictionForce);
    }

    public List<Entity> getCollidingEntities() {
        return collidingEntities;
    }

    public void applyForce(Vec2 force) {
        velocity = velocity.add(force.div(mass));
    }
}
