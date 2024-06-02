import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sulfurengine.behaviorscripts.CameraFollow;
import com.sulfurengine.behaviorscripts.PlatformerController;
import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.behaviorscripts.TopDownPlayerMovement;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Input;
import com.sulfurengine.io.Scene;
import com.sulfurengine.io.SceneManager;
import com.sulfurengine.physics.BoxCollider;
import com.sulfurengine.physics.Rigidbody;
import com.sulfurengine.renderer.Animator;
import com.sulfurengine.renderer.Sprite;
import com.sulfurengine.renderer.TextRenderer;
import com.sulfurengine.ui.UiComponent;
import com.sulfurengine.util.DebugCameraControls;
import com.sulfurengine.util.Prefabs;
import com.sulfurengine.util.Vec2;

public class Main {

    public static void main(String[] args) {
        Display display = Display.get();
        display.init(800, 600, "Mushroom Hiest");     
        display.setIcon(new Sprite("/resources/mushroom.png"));
        
        SceneManager.AddScene(level(0));
        SceneManager.AddScene(level(1));
        SceneManager.AddScene(level(2));
        SceneManager.AddScene(level(3));
        SceneManager.AddScene(level(4));
        SceneManager.AddScene(level(5));
        SceneManager.AddScene(gameOver());




        SceneManager.SetScene(0);
        
        while(Display.open()) {
        	display.update();
        }
        
        display.close();
    }
    
    private static Scene gameOver() {
    	Scene scene = new Scene();
    	scene.background = new Spriterenderer(new Sprite("/resources/bg.jpg"));
    	
    	Entity GameOverText = Prefabs.TextEntity("You Win! (R to play again, Esc to close", TextRenderer.DEFAULT_FONT);
    	GameOverText.transform.pos = new Vec2(0, -100);
    	
    	Entity mushroom = Prefabs.ImageEntity(new Sprite("/resources/mushroom.png"));
    	mushroom.transform = new Transform(new Vec2(0, 0), new Vec2(100, 100));
    	
    	mushroom.addScript(new CameraFollow());
    	
    	scene.addEntity(GameOverText);
    	scene.addEntity(mushroom);
    	return scene;
    }
    
    private static Scene level(int level) {
    	Scene scene = new Scene();
        scene.background = new Spriterenderer(new Sprite("/resources/bg.jpg"));
      
        if(level == 0) {
        Entity tutText1 = Prefabs.TextEntity("Collect Coins", TextRenderer.DEFAULT_FONT);
        tutText1.transform.pos = new Vec2(100, 500);
        scene.addEntity(tutText1);
        
        Entity tutText2 = Prefabs.TextEntity("Stomp on the enemies", TextRenderer.DEFAULT_FONT);
        tutText2.transform.pos = new Vec2(700, 500);
        scene.addEntity(tutText2);
        

        Entity tutText3 = Prefabs.TextEntity("Collect Mushrooms to progress", TextRenderer.DEFAULT_FONT);
        tutText3.transform.pos = new Vec2(900, 500);
        scene.addEntity(tutText3);
        }
        
        Entity player = new Entity("player", new Transform());
        player.transform.pos = new Vec2(0, -50);
        player.transform.scale = new Vec2(50, 50);
        player.addScript(new Rigidbody());
        player.addScript(new BoxCollider());
        player.addScript(new Spriterenderer());
        player.addScript(new CameraFollow());
        player.addScript(new PlatformerController());
        player.addScript(new PickupCoin());
        
        
        Animator playerAnim = new Animator();
        playerAnim.addAnim("idle", new Sprite("/resources/idle.gif"));
        playerAnim.addAnim("walk", new Sprite("/resources/walk.gif"));
        player.addScript(playerAnim);
        
       
        Tilemap tilemapScript = new Tilemap();
        switch(level) {
        case 0:
        	tilemapScript.tileData = Scenes.tutorial();
        	break;
        case 1:
        	tilemapScript.tileData = Scenes.level1();
        	break;
        case 2:
        	tilemapScript.tileData = Scenes.level2();
        	break;
        case 3:
        	tilemapScript.tileData = Scenes.level3();
        	break;
        case 4:
        	tilemapScript.tileData = Scenes.level4();
        	break;
        case 5:
        	tilemapScript.tileData = Scenes.level5();
        	Entity bg = Prefabs.ImageEntity(new Sprite("/resources/grid.png"));
        	bg.transform = new Transform(new Vec2(100, 400), new Vec2(5000, 5000));
        	scene.addEntity(bg);
        }
        tilemapScript.spriteData = generateSprites();
        tilemapScript.tileSize = 60;

        Entity tilemapEntity = new Entity("TilemapEntity", new Transform());
        tilemapEntity.addScript(tilemapScript);
        
        
        scene.addEntity(player);
        scene.addEntity(tilemapEntity);
        
        return scene;
    }
    


    private static List<Sprite> generateSprites() {
        List<Sprite> sprites = new ArrayList<>();
        sprites.add(new Sprite("/resources/grass.png"));
        sprites.add(new Sprite("/resources/grid.png"));
        sprites.add(new Sprite("/resources/coin.gif"));
        sprites.add(new Sprite("/resources/enemy.gif"));
        sprites.add(new Sprite("/resources/mushroom.png"));

        return sprites;
    }
    

}
