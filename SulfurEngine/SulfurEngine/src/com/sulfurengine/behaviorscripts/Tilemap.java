package com.sulfurengine.behaviorscripts;
import java.util.List;

import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.physics.BoxCollider;
import com.sulfurengine.physics.Rigidbody;
import com.sulfurengine.renderer.Sprite;
import com.sulfurengine.util.Vec2;

public class Tilemap extends Script{
	
	public int[][] tileData;
	public int tileSize = 32;
	
	public List<Entity> data;
	
	public void addData(Entity d) {
		data.add(d);
	}
	
	@Override
	public void start() {
	    // Loop through each row of tileData
	    for (int y = 0; y < tileData.length; y++) {
	        // Loop through each column of tileData
	        for (int x = 0; x < tileData[y].length; x++) {
	            // Get the tile value at this position
	            int tileValue = tileData[y][x];
	            if(tileValue == 0) continue;
	            int value = tileValue-1;
	            
	            // Check if the tile value is valid and within the range of spriteData
	            if (tileValue > 0 && tileValue <= data.size()) {
	                // Get the corresponding sprite for this tile value
	                Entity tile = data.get(value).clone(); // Adjusting for zero-based indexing
	                
	               
	                // Add the entity to the scene
	                Display.currentScene.instantiate(tile);
	            }
	        }
	    }
	}


	@Override
	public void update(float dt) {		
	}

}
