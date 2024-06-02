package com.sulfurengine.behaviorscripts;

import javax.sound.sampled.*;

import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.util.AssetManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AudioClip extends Script {
	    private Clip clip;
	    private boolean playOnAwake;


	    public AudioClip(String audioPath, boolean playOnAwake) {
	        clip = AssetManager.getClip(audioPath);
	    }

	    public static void PlayOneShot(String path) {
	    	AudioClip clip = new AudioClip(path, false);
	    	clip.playOneShot();
	    }

	    @Override
	    public void start() {
	    	System.out.println("start");
	        if (playOnAwake) {
	            playOneShot();
	        }
	    }

	    @Override
	    public void update(float dt) {
	        // Implementation for updating the script every frame, if needed
	    }

	    public void playOneShot() {
	        if (clip != null) {
	            clip.setFramePosition(0);  // Rewind to the beginning
	            clip.start();  // Play the clip
	        }
	    }

	    public void playLooped() {
	        if (clip != null) {
	            clip.setFramePosition(0);  // Rewind to the beginning
	            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Play the clip continuously
	        }
	    }

	    public void stopClip() {
	        if (clip != null) {
	            clip.stop();  // Stop the clip
	        }
	    }
}
