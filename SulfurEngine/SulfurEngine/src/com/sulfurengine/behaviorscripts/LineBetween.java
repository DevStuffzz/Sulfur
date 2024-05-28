package com.sulfurengine.behaviorscripts;

import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.renderer.LineRenderer;

public class LineBetween extends Script {

	public Entity e1, e2;
	
	public LineBetween(Entity e1, Entity e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		LineRenderer lr = parent.getScript(LineRenderer.class);
		if(lr != null) {
			lr.startPoint = e1.transform.pos;
			lr.endPoint = e2.transform.pos;
		}
	}

}