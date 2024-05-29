package com.sulfurengine.behaviorscripts;

import java.awt.event.KeyEvent;

import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Input;
import com.sulfurengine.physics.Rigidbody;
import com.sulfurengine.renderer.Animator;
import com.sulfurengine.util.Vec2;

public class PlatformerController extends Script {
    private Rigidbody rigidbody;
    private float moveSpeed = 500f;
    private float jumpForce = 700f;
    private boolean isGrounded = false;

    @Override
    public void start() {
        // Get the Rigidbody component attached to the entity
        rigidbody = parent.getScript(Rigidbody.class);
        parent.transform.pos = new Vec2(100, 400);
    }

    @Override
    public void update(float dt) {
        // Handle player movement
        handleMovement();

        // Handle jumping
        handleJump();
    }

    private void handleMovement() {
        float moveInput = Input.getAxisHorizontal();
        
        if(moveInput > 0) {
        	parent.getScript(Spriterenderer.class).flipX = true;
        	parent.getScript(Animator.class).setAnim("walk");
        } else if (moveInput < 0) {
        	parent.getScript(Spriterenderer.class).flipX = false;
        	parent.getScript(Animator.class).setAnim("walk");
        } else {
        	parent.getScript(Animator.class).setAnim("idle");
        }
        
        float moveVelocity = moveInput * moveSpeed;
        
        // Apply horizontal movement
        rigidbody.velocity.x = moveVelocity;
        rigidbody.applyForce(new Vec2(0, 1)); 
    }

    private void handleJump() {
        if (Input.isKeyDown(KeyEvent.VK_SPACE) && isGrounded) {
            // Apply vertical force for jumping
            rigidbody.applyForce(new Vec2(0, -jumpForce));
            isGrounded = false; // Update grounded state
        }
    }
    

    // Method to handle collision with other entities
    @Override
    public void onCollision(Entity other) {
        // Check if the collided entity is named "ground" and if the player is above it
    	
    	onGrounded(); // 
    }

    // Method to check if the player is positioned above the ground entity
    private boolean isAboveGround(Entity groundEntity) {
        // Get the position of the player's collider
        Vec2 playerPosition = parent.transform.pos;
        
        // Get the position of the ground entity
        Vec2 groundPosition = groundEntity.transform.pos;
        
        // Compare the y-coordinates to check if the player is above the ground
        return playerPosition.y > groundPosition.y;
    }

    // Method to be called when the entity lands on the ground
    public void onGrounded() {
        isGrounded = true;
    }
}
