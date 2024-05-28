package com.sulfurengine.behaviorscripts;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.renderer.Sprite;

public class Spriterenderer extends Script{
	private Sprite sprite;
	private Color color;
	
	private boolean colored;
	
	public boolean flipX;
    public boolean flipY;
	
	public Spriterenderer(Sprite sprite) {
		this.sprite = sprite;
		this.color = null;
		this.colored = false;
	}
	
	public Spriterenderer(Color color) {
		this.sprite = null;
		this.color = color;
		this.colored = true;
	}
	
	public Spriterenderer() {
		int width = Display.get().width();
		int height = Display.get().height();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, Color.white.getRGB()); // Set each pixel to white
			}
		}
		this.sprite = new Sprite(image);
		this.color = null;
		this.colored = false;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean colored() {
		return this.colored;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		this.color = null;
		this.colored = false;	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.sprite = null;
		this.color = color;
		this.colored = true;	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}
	
}
