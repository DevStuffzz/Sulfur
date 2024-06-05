package com.sulfurengine.io;

import java.util.ArrayList;
import java.util.List;

import com.sulfurengine.behaviorscripts.AudioClip;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.renderer.DirtyFlag;

public class SceneManager {
    
    private static List<Scene> initialScenes = new ArrayList<>();
    
    public static int CurrentSceneIndex;
    
    public static void AddScene(Scene scene) {
		// Store a clone of the initial state
		initialScenes.add((Scene) scene.clone());
    }
    
    public static void SetScene(Scene scene) {
        for(Entity e : Display.currentScene.getAllEntities()) {
        	AudioClip clip = e.getScript(AudioClip.class);
        	if(clip != null) {
        		clip.stopClip();
        	}
        }
        
        
        Display.currentScene = scene;
        Display.currentScene.start();
    }
    
    public static void SetScene(int index) {
    	CurrentSceneIndex = index;
        Scene scene = initialScenes.get(index).clone();
        
        for(Entity e : Display.currentScene.getAllEntities()) {
        	AudioClip clip = e.getScript(AudioClip.class);
        	if(clip != null) {
        		clip.stopClip();
        	}
        }
        
        
        Display.currentScene = scene;
        Display.currentScene.start();
    }

	public static void NextScene() {
		SetScene(CurrentSceneIndex +1);
	}
}
