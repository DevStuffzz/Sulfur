package com.sulfurengine.renderer;

import java.util.HashMap;
import java.util.Map;

import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.ecs.Script;

public class Animator extends Script {
	private Map<String, Sprite> animations;

	public Animator() {
		animations = new HashMap<>();
	}
	
	@Override
	public void start() {		
	}

	@Override
	public void update(float dt) {		
	}
	
	public void addAnim(String key, Sprite anim) {
		this.animations.put(key, anim);
	}
	
	public void setAnim(String key) {
		Spriterenderer sr = parent.getScript(Spriterenderer.class);
		if(sr != null) {
			sr.setSprite(animations.get(key));
		}
	}
}