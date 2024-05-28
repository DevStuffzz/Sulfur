package com.sulfurengine.util;

import java.awt.Font;

import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.renderer.Sprite;
import com.sulfurengine.renderer.TextRenderer;

public class Prefabs {
	public static Entity ImageEntity(Sprite sprite) {
		Entity e = new Entity("ImageEntity_Gen", new Transform(new Vec2(0, 0), new Vec2(100, 100)));
		e.addScript(new Spriterenderer(sprite));
		return e;
	}
	
	public static Entity TextEntity(String text, Font f) {
		Entity e = new Entity("TextEntity_Gen", new Transform());
		e.addScript(new TextRenderer(text, f));
		return e;
	}
}
