package com.sulfurengine.behaviorscripts;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Input;
import com.sulfurengine.renderer.Animator;
import com.sulfurengine.renderer.DirtyFlag;

public class TopDownPlayerMovement extends Script {

	public float speed = 500.0f;
	
	@Override
	public void start() {		
	}

	@Override
	public void update(float dt) {
		//Display.currentScene.camera.position = parent.transform.pos;
		float x = Input.getAxisHorizontal() * speed * dt;
		float y = Input.getAxisVertical() * speed * dt;
		
		parent.transform.pos.x += x;
		parent.transform.pos.y += y;
		
		Spriterenderer spr = parent.getScript(Spriterenderer.class);
		if(spr != null) {
		    if (x > 0) {
	            spr.flipX = false; 
	        } else if (x < 0) {
	            spr.flipX = true; 
	        }		
		}
		
		if(x != 0 || y != 0) {
			DirtyFlag.dirty = true;
		} else {
		}
		
		
		Display.title("Player pos: x=" + parent.transform.pos.x + " y=" + parent.transform.pos.y);
	}

}
