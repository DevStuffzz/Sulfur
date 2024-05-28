package com.sulfurengine.ecs;

import com.sulfurengine.util.Vec2;

public class Transform {
	public Vec2 pos, scale;
	public float rotation;

	
	public Transform() {
		this(new Vec2(), new Vec2());
	}
	
	public Transform(Vec2 pos, Vec2 scale) {
		this.pos = pos;
		this.scale = scale;
	}
	
	public Transform(Vec2 pos, Vec2 scale, float rotation){
		this.pos = pos;
		this.scale = scale;
		this.rotation = 0;
	}
}
