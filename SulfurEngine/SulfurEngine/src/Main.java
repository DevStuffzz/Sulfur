
import java.awt.Color;
import java.awt.Font;

import com.sulfurengine.behaviorscripts.AudioClip;
import com.sulfurengine.behaviorscripts.BoxCollider;
import com.sulfurengine.behaviorscripts.LineBetween;
import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Scene;
import com.sulfurengine.io.SceneManager;
import com.sulfurengine.renderer.Animator;
import com.sulfurengine.renderer.LineRenderer;
import com.sulfurengine.renderer.Sprite;
import com.sulfurengine.renderer.TextRenderer;
import com.sulfurengine.ui.UiComponent;
import com.sulfurengine.util.Debug;
import com.sulfurengine.util.Prefabs;
import com.sulfurengine.util.Vec2;

public class Main {
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
		scene.background = new Spriterenderer(Color.WHITE);
		scene.start();
		
		Entity bg = Prefabs.ImageEntity(new Sprite("/resources/assets/backgroundtest.jpg"));
		bg.transform.scale = new Vec2(2000, 2000);
		
		Entity player = new Entity("Player", new Transform(
				new Vec2(400, 300), new Vec2(100, 100)));
		player.addScript(new PlayerMovement());
		player.addScript(new Spriterenderer(Color.cyan));
		
		Animator playerAnim = new Animator();
		playerAnim.addAnim("idle", new Sprite("/resources/assets/idle.gif"));
		playerAnim.addAnim("walk", new Sprite("/resources/assets/walk.gif"));
		
		player.addScript(playerAnim);
		
		Entity redCube = new Entity("RedCube", new Transform(
				new Vec2(100, 100), new Vec2(100, 100)));
		
		redCube.addScript(new Spriterenderer(Color.red));
		redCube.addScript(new Rotator());
	
		Entity text_default = Prefabs.TextEntity("Default Font", TextRenderer.DEFAULT_FONT);
		text_default.transform.pos = new Vec2(200, 200);
		
		Entity text_custom = Prefabs.TextEntity("Custom Text", new Font("Monospaced", Font.PLAIN, 19));
		text_custom.transform.pos = new Vec2(200, 400);
		
		Entity ui_text = Prefabs.TextEntity("UI Text", new Font("TimesRoman", Font.PLAIN, 19));
		ui_text.transform.pos = new Vec2(400, 200);
		ui_text.addScript(new UiComponent());
		
		Entity line = new Entity("line", new Transform());
		line.addScript(new LineRenderer(new Vec2(100, 100), new Vec2(100, 200)));
		line.addScript(new LineBetween(player, redCube));

		scene.addEntity(bg);
		scene.addEntity(player);
		scene.addEntity(redCube);
		scene.addEntity(text_default);
		scene.addEntity(text_custom);
		scene.addEntity(ui_text);
		scene.addEntity(line);

		
		return scene;
	}
}
