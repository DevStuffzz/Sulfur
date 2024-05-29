package com.sulfurengine.behaviorscripts;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.util.Vec2;

public class CameraFollow extends Script{

	
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		Display.currentScene.camera.position = parent.transform.pos.add(new Vec2(0, -100));
	}

}
