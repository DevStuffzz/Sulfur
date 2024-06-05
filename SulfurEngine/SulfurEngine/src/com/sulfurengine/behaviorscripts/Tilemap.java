import java.util.List;

import com.sulfurengine.behaviorscripts.PhysicsCollisionIgnore;
import com.sulfurengine.behaviorscripts.Spriterenderer;
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
	
	public List<Sprite> spriteData;
	
	public void addSprite(Sprite sprite) {
		spriteData.add(sprite);
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
	            int spriteValue = tileValue-1;
	            
	            // Check if the tile value is valid and within the range of spriteData
	            if (tileValue > 0 && tileValue <= spriteData.size()) {
	                // Get the corresponding sprite for this tile value
	                Sprite sprite = spriteData.get(spriteValue); // Adjusting for zero-based indexing
	                
	                // Create an entity for this tile
	                Entity tile = new Entity("tile", new Transform());
	                
	                // Calculate position based on tile size and grid position
	                Vec2 position = new Vec2(x * tileSize, y * tileSize);
	                
	                // Add transform component to the entity
	                tile.transform = new Transform(position, new Vec2(tileSize, tileSize));
	                
	                // Add sprite renderer component to the entity
	                tile.addScript(new Spriterenderer(sprite));
	                tile.addScript(new BoxCollider());
	                if(spriteValue == 2) {
	                	tile.addScript(new Coin());
	                	tile.addScript(new PhysicsCollisionIgnore());
	                	tile.transform.scale = new Vec2(32, 32);
	                	tile.name = "coin";
	                }
	                if(spriteValue == 3) {
	                	tile.addScript(new Rigidbody());
	                	tile.addScript(new EnemyController());
	                	tile.addScript(new PhysicsCollisionIgnore());
	                	tile.name = "enemy";
	                }
	                if(spriteValue == 4) {
	                	tile.addScript(new Mushroom());
	                	tile.name = "mushroom";
	                }
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
