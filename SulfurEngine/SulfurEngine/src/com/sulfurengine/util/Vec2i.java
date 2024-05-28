package com.sulfurengine.util;

public class Vec2i {
public int x, y;
	
	public Vec2i() {
		this(0, 0);
	}
	
	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2i(float f, float g) {
		this.x = (int)f;
		this.y = (int)g;
	}

	public static Vec2i convert(Vec2 vec) {
		return new Vec2i((int)vec.x, (int)vec.y);
	}
	
	public Vec2i sub(Vec2 vec) {
		return new Vec2i(x - vec.x, y - vec.y);
	}
}
