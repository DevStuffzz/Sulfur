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
    private static final int PLAYER_JUMP_FORCE = -10; // Adjust as needed

    public static void main(String[] args) {
        Display display = Display.get();
        display.init(800, 600, "Sulfur Engine");      
        
        SceneManager.AddScene(gameScene());
        SceneManager.SetScene(0);
        
        while(Display.open()) {
        	display.update();
        }
        
        display.close();
    }
    
    private static Scene gameScene() {
        Scene scene = new Scene();
        scene.background = new Spriterenderer(Color.magenta);
        Entity bg = new Entity("bg", new Transform());
        bg.transform.pos = new Vec2(100, 400);
        bg.transform.scale = new Vec2(1920*2.0f,1080*2.0f);
        bg.addScript(new Spriterenderer(new Sprite("/resources/bg.png")));
        scene.addEntity(bg);
        
        Entity player = new Entity("Player", new Transform());
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
        
        scene.addEntity(player);
        scene.addEntity(createTilemapEntity());
        
        return scene;
    }



    public static Entity createTilemapEntity() {
        Tilemap tilemapScript = new Tilemap();
        tilemapScript.tileData = generateMap();
        tilemapScript.spriteData = generateSprites();
        tilemapScript.tileSize = 60;

        Entity tilemapEntity = new Entity("TilemapEntity", new Transform());
        tilemapEntity.addScript(tilemapScript);

        return tilemapEntity;
    }

    private static List<Sprite> generateSprites() {
        List<Sprite> sprites = new ArrayList<>();
        sprites.add(new Sprite("/resources/grass.png"));
        sprites.add(new Sprite("/resources/grid.png"));
        sprites.add(new Sprite("/resources/coin.gif"));

        return sprites;
    }
    
    private static int[][] generateMap() {
        int[][] map = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Row 0 to 11 (air)
            {0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Row 1 to 11 (air)
            {0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Row 2 to 11 (air)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Row 3 to 11 (air)
            {0, 0, 3, 0, 2, 2, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 3, 0, 0, 0}, // Row 4 to 11 (air)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0}, // Row 5 to 11 (air)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0}, // Row 6 to 11 (air)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 3, 0, 0, 2, 2, 0}, // Row 7 to 11 (air)
            {0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0}, // Row 8 to 11 (air)
            {0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0}, // Row 9 to 11 (air)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 3, 0}, // Row 10 to 11 (air)
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // Row 12 (grass)
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // Row 13 (grass)
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // Row 14 (grass)
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // Row 15 (grass)
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // Row 16 (grass)
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // Row 17 (grass)
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // Row 18 (grass)
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // Row 19 (grass)
        };

        return map;
    }


}
