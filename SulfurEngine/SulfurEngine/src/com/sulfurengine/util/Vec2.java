package com.sulfurengine.util;

public class Vec2 {
	public float x, y;
	
	public Vec2() {
		this(0, 0);
	}
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vec2 other) {
		add(other.x, other.y);
	}
	
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public Vec2 copy() {
		return new Vec2(x, y);
	}
}
