package com.sulfurengine.physics;

import java.util.List;

import com.sulfurengine.ecs.Entity;
import com.sulfurengine.io.Display;
import com.sulfurengine.util.Vec2;

public class Raycast {

public static RaycastResult castRay(Vec2 origin, Vec2 direction, float length) {
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
}
