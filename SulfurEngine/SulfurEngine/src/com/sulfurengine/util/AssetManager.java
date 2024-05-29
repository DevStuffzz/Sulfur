package com.sulfurengine.util;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class AssetManager {
	private static Map<String, Image> images = new HashMap<>();
	private static Map<String, Clip> audioClips = new HashMap<>();

	public static Image getImage(String loc) {
		if(!images.containsKey(loc)) {
			Image image;
			 URL imageUrl = AssetManager.class.getResource(loc);
			 if (imageUrl == null) {
				 System.err.println("File does not exist at: " + loc);
				 System.exit(-1);
				 image = null; // Handle the case where the file does not exist
			 } else {
				 image = new ImageIcon(imageUrl).getImage();
			 }
			 images.put(loc, image);
		}
		return images.get(loc);
	}
}
