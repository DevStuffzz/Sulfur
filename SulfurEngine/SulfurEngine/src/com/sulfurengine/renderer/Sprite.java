package com.sulfurengine.renderer;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import com.sulfurengine.util.Debug;

public class Sprite {
	private Image image;
	
	  public Sprite(String loc) {
	        URL imageUrl = getClass().getResource(loc);
	        if (imageUrl == null) {
	            System.err.println("File does not exist at: " + loc);
	            this.image = null; // Handle the case where the file does not exist
	        } else {
	            this.image = new ImageIcon(imageUrl).getImage();
	        }
	    }

	public void setImage(String loc) {
		this.image = new ImageIcon(loc).getImage();
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return this.image;
	}
}
