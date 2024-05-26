package com.sulfurengine.ecs;

import com.sulfurengine.util.Vec2;

public class Transform {
	public Vec2 pos, scale;

	
	public Transform() {
		this(new Vec2(), new Vec2());
	}
	
	public Transform(Vec2 pos, Vec2 scale) {
		this.pos = pos;
		this.scale = scale;
	}
}
