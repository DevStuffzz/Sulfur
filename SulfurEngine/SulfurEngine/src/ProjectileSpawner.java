import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Scene;
import com.sulfurengine.renderer.Sprite;
import com.sulfurengine.util.Vec2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProjectileSpawner extends Script {
    private Random random = new Random();


    private float maxTime = 0.5f;
    private float currentTime = 1.5f;
    
    @Override
    public void start() {
    	currentTime = 1.5f;
    }

    @Override
    public void update(float dt) {
    	currentTime -= dt;
    	
    	if(currentTime < 0) {
		    // Spawn projectiles from the sides of the screen
		    spawnRandom();
		    
		    currentTime = maxTime;
    	}
        checkAndDestroyOffScreen();
    }

    // Spawns a projectile at the specified position with the given direction
    private void spawnProjectile(Vec2 position, Vec2 direction) {
    	   Entity projectile = Display.currentScene.instantiate(new Entity("Projectile", new Transform(position, new Vec2(75, 75))));
    	   int spriteIndex = random.nextInt(2); // Generates a random integer between 0 (inclusive) and 2 (exclusive)
           
           // Determine the sprite path based on the sprite index
           String spritePath;
           if (spriteIndex == 0) {
               spritePath = "/resources/assets/fish1.png";
           } else {
               spritePath = "/resources/assets/fish2.png";
           }
           
          
    	   projectile.addScript(new Projectile(direction.copy()));
		   projectile.addScript(new Spriterenderer(new Sprite(spritePath)));
		   if(direction.x > 0) projectile.getScript(Spriterenderer.class).flipX = true;
    }
    
    private void spawnRandom() {
        float spawnChance = 0.9f; // Adjust the spawn chance as needed
        
        for(int i = 0; i < 10; i++) {
        if (random.nextFloat() < spawnChance) {
            float screenWidth = Display.get().width();
            float screenHeight = Display.get().height();
            
            // Randomly select a side of the screen
            int side = random.nextInt(4); // 0: top, 1: right, 2: bottom, 3: left
            
            // Randomly select a position along the chosen side
            float x = 0, y = 0;
            switch (side) {
                case 0: // Top side
                    x = random.nextFloat() * screenWidth;
                    y = 0;
                    break;
                case 1: // Right side
                    x = screenWidth;
                    y = random.nextFloat() * screenHeight;
                    break;
                case 2: // Bottom side
                    x = random.nextFloat() * screenWidth;
                    y = screenHeight;
                    break;
                case 3: // Left side
                    x = 0;
                    y = random.nextFloat() * screenHeight;
                    break;
            }
            
            // Randomly select a direction for the fish to swim
            float angle = (float) (random.nextFloat() * 2 * Math.PI);
            Vec2 direction = new Vec2((float) Math.cos(angle), (float) Math.sin(angle));
            
            int apple = random.nextInt(5);
            // Spawn the fish at the chosen position and with the selected direction
            switch(apple) {
            case 0:
            case 1:
            case 2:
            case 3:
            	spawnProjectile(new Vec2(x, y), direction);
            	break;
            case 4:
            	spawnApple(new Vec2(x, y), direction);
            	break;
            }
        }
        }
    }

    private void spawnApple(Vec2 position, Vec2 direction) {
    	Entity apple = Display.currentScene.instantiate(new Entity("Projectile", new Transform(position, new Vec2(75, 75))));
 	   int spriteIndex = random.nextInt(1); // Generates a random integer between 0 (inclusive) and 2 (exclusive)
        
        // Determine the sprite path based on the sprite index
        String spritePath;
        spritePath = "/resources/assets/apple.png";
        
        
       
 	   apple.addScript(new Apple(direction.copy()));
 	   apple.addScript(new Spriterenderer(new Sprite(spritePath)));
	}

	// Check and destroy projectiles that are off the screen
    private void checkAndDestroyOffScreen() {
        
    }
}
