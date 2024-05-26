package com.sulfurengine.behaviorscripts;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.util.Vec2;

import java.util.ArrayList;
import java.util.List;

public class BoxCollider extends Script {
    private List<Entity> collidingEntities;

    @Override
    public void start() {
        collidingEntities = new ArrayList<>();
    }

    @Override
    public void update(float dt) {
        // Clear the list of colliding entities before performing collision detection
        collidingEntities.clear();
        
        // Collision detection logic
        checkCollisions();
    }

    private void checkCollisions() {
        List<Entity> entities = Display.currentScene.getAllEntities(); // Get all entities in the scene
        
        for (Entity entity : entities) {
            if (entity != parent) { // Avoid self-collision
                // Get the Transform component of the other entity
                Transform otherTransform = entity.transform;
                
                if (otherTransform != null) { // Ensure the other entity has a Transform component
                    // Check for collision with the other entity's position
                    if (isColliding(otherTransform)) {
                        // Collision detected, add the colliding entity to the list
                        collidingEntities.add(entity);
                        
                        // Optionally, handle collision with the other entity
                        handleCollision(entity);
                    }
                }
            }
        }
    }

    private boolean isColliding(Transform otherTransform) {
        // Get the Transform component of the collider entity (parent's transform)
        Transform parentTransform = parent.transform;
        
        if (parentTransform != null && otherTransform != null) { // Ensure both entities have Transform components
            // Get the positions and sizes of the collider entity and the other entity
            Vec2 colliderPosition = parentTransform.pos;
            Vec2 otherPosition = otherTransform.pos;
            Vec2 colliderSize = parentTransform.scale;
            Vec2 otherSize = otherTransform.scale;
            
            // Calculate the bounding box of the collider entity (centered on its position)
            float colliderLeft = colliderPosition.x - colliderSize.x / 2;
            float colliderRight = colliderPosition.x + colliderSize.x / 2;
            float colliderTop = colliderPosition.y - colliderSize.y / 2;
            float colliderBottom = colliderPosition.y + colliderSize.y / 2;
            
            // Calculate the bounding box of the other entity (centered on its position)
            float otherLeft = otherPosition.x - otherSize.x / 2;
            float otherRight = otherPosition.x + otherSize.x / 2;
            float otherTop = otherPosition.y - otherSize.y / 2;
            float otherBottom = otherPosition.y + otherSize.y / 2;
            
            // Check for collision by comparing bounding boxes
            if (colliderRight >= otherLeft && colliderLeft <= otherRight &&
                colliderBottom >= otherTop && colliderTop <= otherBottom) {
                // Collision detected
                return true;
            }
        }
        
        // No collision detected
        return false;
    }

    private void handleCollision(Entity other) {
        parent.onCollision(other);
    }

    // Getter method to retrieve the list of colliding entities
    public List<Entity> getCollidingEntities() {
        return collidingEntities;
    }
}