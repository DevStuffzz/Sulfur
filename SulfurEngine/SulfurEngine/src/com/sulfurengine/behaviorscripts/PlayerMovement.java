package com.sulfurengine.behaviorscripts;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Input;
import com.sulfurengine.renderer.Animator;
import com.sulfurengine.renderer.DirtyFlag;
import com.sulfurengine.util.Vec2;

public class PlayerMovement extends Script {

	public float speed = 5000.0f;
	
	@Override
	public void start() {		
	}

	@Override
	public void update(float dt) {
		Display.currentScene.camera.position = parent.transform.pos;
		float x = Input.getAxisHorizontal() * speed * dt;
		float y = Input.getAxisVertical() * speed * dt;
		
		parent.transform.pos = parent.transform.pos.add(new Vec2(x, y));
		
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
		
			}

}
