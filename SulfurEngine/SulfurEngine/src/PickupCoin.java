import java.awt.event.KeyEvent;

import com.sulfurengine.behaviorscripts.AudioClip;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Input;
import com.sulfurengine.io.SceneManager;

public class PickupCoin extends Script{

	public static int coins = 0;
	
	@Override
	public void start() {
		coins = 0;
	}

	@Override
	public void update(float dt) {
		if(Input.isKeyDown(KeyEvent.VK_R)) {
			SceneManager.SetScene(1);
		}
	}
	
	@Override
	public void onCollision(Entity other) {
		if(other.getScript(Coin.class) != null) {
			coins++;
			Display.currentScene.destroy(other);
			AudioClip.PlayOneShot("/resources/coin.wav");
		}
		
		if(other.getScript(EnemyController.class) != null) {
			if(parent.transform.pos.y < other.transform.pos.y) {
				Display.currentScene.destroy(other);
			} else {
				SceneManager.SetScene(1);
				AudioClip.PlayOneShot("/resources/lose.wav");

			}
		}
		if(other.getScript(Mushroom.class) != null) {
			AudioClip.PlayOneShot("/resources/levelup.wav");
			SceneManager.NextScene();
		}
	}

}
