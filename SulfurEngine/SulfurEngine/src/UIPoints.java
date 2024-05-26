import com.sulfurengine.ecs.Script;
import com.sulfurengine.renderer.TextRenderer;

public class UIPoints extends Script {
	private TextRenderer tr;
	public static int points = 0;
	@Override
	public void start() {
		tr = parent.getScript(TextRenderer.class);
		points = 0;
	}

	@Override
	public void update(float dt) {
		tr.text = "Apples Eaten: " + points;
	}

}