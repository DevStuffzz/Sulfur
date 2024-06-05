package com.sulfurengine.renderer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

import com.sulfurengine.util.AssetManager;
import com.sulfurengine.util.Debug;

public class Sprite {
	private Image image;
	
	private Sprite() {
		
	}
	
	public Sprite(String loc) {
        this.image = AssetManager.getImage(loc);
    }

	

	public Sprite(Image image) {
		this.image = image;
	}



	public void setImage(String loc) {
        this.image = AssetManager.getImage(loc);
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return this.image;
	}



	public static Sprite Generate(Image image) {
		Sprite sprite = new Sprite();
		sprite.setImage(image);
		return sprite;
	}
}
