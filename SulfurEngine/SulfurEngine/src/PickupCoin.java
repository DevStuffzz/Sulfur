import java.awt.event.KeyEvent;

import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Input;
import com.sulfurengine.io.SceneManager;

public class PickupCoin extends Script{

	public static int coins = 0;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
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
		}
	}

}
