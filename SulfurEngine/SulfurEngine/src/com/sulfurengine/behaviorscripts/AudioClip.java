package com.sulfurengine.behaviorscripts;

import javax.sound.sampled.*;

import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AudioClip extends Script {
	 private String audioPath;
	    private Clip clip;
	    private boolean playOnAwake;


	    public AudioClip(String audioPath, boolean playOnAwake) {
	        this.audioPath = audioPath;
	        this.playOnAwake = playOnAwake;
	        try {
	            // Load the audio resource
	            URL audioUrl = getClass().getResource(audioPath);
	            if (audioUrl == null) {
	                System.err.println("Audio file does not exist at: " + audioPath);
	                return;
	            }

	            // Open an audio input stream.
	            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioUrl);
	            // Get a sound clip resource.
	            clip = AudioSystem.getClip();
	            // Open audio clip and load samples from the audio input stream.
	            clip.open(audioIn);
	        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	            e.printStackTrace();
	        }
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
