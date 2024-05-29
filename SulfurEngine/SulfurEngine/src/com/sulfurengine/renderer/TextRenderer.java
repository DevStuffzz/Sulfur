package com.sulfurengine.renderer;

import java.awt.Color;
import java.awt.Font;

import com.sulfurengine.ecs.Script;

public class TextRenderer extends Script {
	public String text = "Hello World";
	public Font font;
	public Color color;
	
	public static Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);
	

	public TextRenderer(String t, Font f) {
		text = t;
		font = f;
		color = Color.black;
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update(float dt) {
		
	}
}
