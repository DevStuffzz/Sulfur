package com.sulfurengine.behaviorscripts;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.physics.Rigidbody;
import com.sulfurengine.util.Vec2;

public class TopDownGroundEnemyController extends Script {
    private Rigidbody rigidbody;
    private float moveSpeed = 150f; // Adjust the speed as necessary
    private Entity player; // Reference to the player entity

    @Override
    public void start() {
        // Get the Rigidbody component attached to the entity
        rigidbody = parent.getScript(Rigidbody.class);
        
        // Find the player entity (assuming there's a way to get it, e.g., by name)
        player = Display.currentScene.findEntityByName("player");
        if (player != null) {
            System.out.println("Player found: " + player.name);
        } else {
            System.out.println("Player not found");
        }
    }

    @Override
    public void update(float dt) {
        if (player != null) {
            // Handle enemy movement towards the player
            handleMovement(dt);
        }
    }

    private void handleMovement(float dt) {
        Vec2 playerPos = player.transform.pos;
        Vec2 enemyPos = parent.transform.pos;

        // Determine the direction to move based on the player's position
        float direction = playerPos.x > enemyPos.x ? 1 : -1;
        parent.getScript(Spriterenderer.class).flipX = playerPos.x > enemyPos.x;
        float moveVelocity = direction * moveSpeed;

        // Apply horizontal movement by setting the velocity directly
        rigidbody.velocity = new Vec2(moveVelocity, rigidbody.velocity.y + 5.0f);
    }
}
