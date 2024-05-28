import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Input;
import com.sulfurengine.renderer.DirtyFlag;

public class PlayerMovement extends Script {

	public float speed = 500.0f;
	
	@Override
	public void start() {		
	}

	@Override
	public void update(float dt) {
		float x = Input.getAxisHorizontal() * speed * dt;
		float y = Input.getAxisVertical() * speed * dt;
		
		parent.transform.pos.add(x, y);
		// Get the display width and height
	    int screenWidth = Display.get().width();
	    int screenHeight = Display.get().height();
	    // Teleport logic if the player goes off the screen
	    if (parent.transform.pos.x < -15) {
	        parent.transform.pos.x = screenWidth; // Teleport to the right side
	    } else if (parent.transform.pos.x > screenWidth) {
	        parent.transform.pos.x = -15; // Teleport to the left side
	    }
	    
	    if (parent.transform.pos.y < 0) {
	        parent.transform.pos.y = screenHeight; // Teleport to the bottom
	    } else if (parent.transform.pos.y > screenHeight) {
	        parent.transform.pos.y = 0; // Teleport to the top
	    }
		
		Spriterenderer spr = parent.getScript(Spriterenderer.class);
		if(spr != null) {
		    if (x > 0) {
	            spr.flipX = true; 
	        } else if (x < 0) {
	            spr.flipX = false; 
	        }		
		}
		
		if(x != 0 || y != 0) DirtyFlag.dirty = true;
		
	}

}
