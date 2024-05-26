import com.sulfurengine.behaviorscripts.BoxCollider;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.SceneManager;

public class PlayerManager extends Script {


	
	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(float dt) {
		BoxCollider col = parent.getScript(BoxCollider.class);
		
		for(Entity e : col.getCollidingEntities()) {
			Projectile p = e.getScript(Projectile.class);
			if(p != null) {
				SceneManager.SetScene(1);
			}
			
			Apple a = e.getScript(Apple.class);
			if(a != null) {
				Display.currentScene.destroy(a.parent);
				UIPoints.points++;
			}
		}
		
	}
	
	@Override
	public void onCollision(Entity e) {
		
	}
}
