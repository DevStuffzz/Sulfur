package com.sulfurengine.behaviorscripts;
import com.sulfurengine.ecs.Script;

public class Rotator extends Script {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		parent.transform.rotation += dt * 100.0f;
	}

}
