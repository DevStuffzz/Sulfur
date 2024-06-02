package com.sulfurengine.util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
	
	public static Clip getClip(String audioPath) {
		if(!audioClips.containsKey(audioPath)) {
			Clip clip;
	
			
			try {
			    // Load the audio resource
				URL audioUrl = AssetManager.class.getResource(audioPath);
				if (audioUrl == null) {
				    System.err.println("Audio file does not exist at: " + audioPath);
				    return null;
				}
				
				// Open an audio input stream.
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioUrl);
				// Get a sound clip resource.
				clip = AudioSystem.getClip();
				// Open audio clip and load samples from the audio input stream.
				clip.open(audioIn);
				audioClips.put(audioPath, clip);
	
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			    e.printStackTrace();
			}
		}
		return audioClips.get(audioPath);
	}
}
