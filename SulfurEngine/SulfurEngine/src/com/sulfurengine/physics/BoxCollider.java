package com.sulfurengine.physics;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.util.Vec2;

import java.util.ArrayList;
import java.util.List;

public class BoxCollider extends Script {
    private List<Entity> collidingEntities;

    
    public BoxCollider() {
    	collidingEntities = new ArrayList<>();
    }
    
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
                        
                    }
                }
            }
        }
    }
    public boolean isColliding(Transform otherTransform) {
        // Get the positions and sizes of the colliders
        float thisLeft = parent.transform.pos.x - parent.transform.scale.x / 2;
        float thisRight = parent.transform.pos.x + parent.transform.scale.x / 2;
        float thisTop = parent.transform.pos.y - parent.transform.scale.y / 2;
        float thisBottom = parent.transform.pos.y + parent.transform.scale.y / 2;
        
        float otherLeft = otherTransform.pos.x - otherTransform.scale.x / 2;
        float otherRight = otherTransform.pos.x + otherTransform.scale.x / 2;
        float otherTop = otherTransform.pos.y - otherTransform.scale.y / 2;
        float otherBottom = otherTransform.pos.y + otherTransform.scale.y / 2;
        
        // Check for collision by comparing bounding boxes
        if (thisRight >= otherLeft && thisLeft <= otherRight &&
            thisBottom >= otherTop && thisTop <= otherBottom) {
            // Collision detected
            return true;
        }
        
        // No collision detected
        return false;
    }


    // Getter method to retrieve the list of colliding entities
    public List<Entity> getCollidingEntities() {
        return collidingEntities;
    }


    public RaycastResult raycast(Vec2 origin, Vec2 direction, float length) {
        RaycastResult result = new RaycastResult();

        Vec2 halfSize = new Vec2(parent.transform.scale.x / 2.0f, parent.transform.scale.y / 2.0f);
        Vec2 min = parent.transform.pos.subtract(halfSize);
        Vec2 max = parent.transform.pos.add(halfSize);

        float tmin = (min.x - origin.x) / direction.x;
        float tmax = (max.x - origin.x) / direction.x;

        if (tmin > tmax) {
            float temp = tmin;
            tmin = tmax;
            tmax = temp;
        }

        float tymin = (min.y - origin.y) / direction.y;
        float tymax = (max.y - origin.y) / direction.y;

        if (tymin > tymax) {
            float temp = tymin;
            tymin = tymax;
            tymax = temp;
        }

        if ((tmin > tymax) || (tymin > tmax)) {
            return result;
        }

        if (tymin > tmin) {
            tmin = tymin;
        }

        if (tymax < tmax) {
            tmax = tymax;
        }

        if (tmin < 0 || tmin > length) {
            return result;
        }

        result.hit = true;
        result.distance = tmin;
        result.point = origin.add(direction.multiply(tmin));
        result.normal = direction.multiply(-1).normalize();

        return result;
    }
}
