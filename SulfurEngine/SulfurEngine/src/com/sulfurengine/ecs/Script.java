package com.sulfurengine.ecs;

public abstract class Script {
	public Entity parent;
	public abstract void start();
	public abstract void update(float dt);
	public void onCollision(Entity other) {
		
	}
}