
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.renderer.DirtyFlag;
import com.sulfurengine.util.Vec2;

public class Apple extends Script{
	private Vec2 direction;
	private float speed = 250.0f;
	
	public Apple(float x, float y) {
		direction = new Vec2(x, y);
	}
	public Apple(Vec2 dir) {
		this.direction = dir;
	}
	@Override
	public void start() {
		
	}
	@Override
	public void update(float dt) {
		float x = direction.x * speed * dt;
		float y = direction.y * speed * dt;
		DirtyFlag.dirty = true;
		parent.transform.pos.add(direction);
		
		Entity projectile = parent;
		Transform transform = projectile.transform;
        Vec2 position = transform.pos;
        int screenWidth = Display.get().width();
        int screenHeight = Display.get().height();

        // Destroy the projectile if it goes off the screen
        if (position.x < 0 || position.x > screenWidth || position.y < 0 || position.y > screenHeight) {
            Display.currentScene.destroy(projectile);
        }
	}
}
