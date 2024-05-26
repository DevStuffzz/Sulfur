package com.sulfurengine.behaviorscripts;

import java.awt.Color;

import com.sulfurengine.ecs.Script;
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