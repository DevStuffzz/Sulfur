
import java.awt.Color;

import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Scene;
import com.sulfurengine.io.SceneManager;
import com.sulfurengine.renderer.TextRenderer;

import com.sulfurengine.util.Prefabs;
import com.sulfurengine.util.Vec2;

public class Main {
	public static void main(String[] args) {
		Display display = Display.get();
		display.init(800, 600, "Sulfur Engine | Ray Tracing Demo");		
		
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
		
	
		
		Entity renderer = new Entity("Renderer", new Transform(new Vec2(0, 0), new Vec2(600, 400)));
		renderer.addScript(new Spriterenderer());
		renderer.addScript(new RayTracer());
		
		Entity text = Prefabs.TextEntity("This is being rendered by a Script on an entity, not a loaded image", TextRenderer.DEFAULT_FONT);
		text.transform.pos = new Vec2(0, -250);
		
		scene.addEntity(renderer);
		scene.addEntity(text);
		
		return scene;
	}
}
