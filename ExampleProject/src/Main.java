
import com.sulfurengine.Sulfur;
import com.sulfurengine.behaviorscripts.Raycaster;
import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Scene;
import com.sulfurengine.io.SceneManager;
import com.sulfurengine.renderer.TextRenderer;
import com.sulfurengine.ui.UiComponent;

import java.awt.Color;
import java.util.Random;

import com.sulfurengine.util.Prefabs;
import com.sulfurengine.util.Vec2;
public class Main {

    public static void main(String[] args) {
        Display display = Display.get();
        display.init(800, 600, "Sulfur Engine 3D Example" + Sulfur.VERSION);     
        
        SceneManager.AddScene(scene());
        
        
        while(Display.open()) {
        	display.update();
        }
        
        display.close();
    }
    
    private static Scene scene() {
    	Scene scene = new Scene();
    	
    	Entity tracer = new Entity("Tracer", new Transform());
    	tracer.transform.scale = new Vec2(800, 600);
    	
    	tracer.addScript(new Spriterenderer());
    	tracer.addScript(new Raycaster(generateRandomData(15, 15), Color.DARK_GRAY, Color.DARK_GRAY.darker()));
    	
    	scene.addEntity(tracer);
    	
    	Entity textUi = Prefabs.TextEntity("Raycasting in Sulfur: Press R to regenerate level", TextRenderer.DEFAULT_FONT);
    	textUi.transform.pos = new Vec2(0, -200);
    	scene.addEntity(textUi);
    	
    	return scene;
    }

public static int[][] generateRandomData(int rows, int cols) {
	int borderWidth = 1;
	int numRandomCells = 10;
	  Random random = new Random();
	    int[][] data = new int[rows][cols];

	    // Define the range of color codes you want
    int numColors = 8; // Assuming you have 8 different colors

    // Fill the grid with air (0s)
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            data[i][j] = 0;
        }
    }

    // Add the border with 1s
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (i < borderWidth || i >= rows - borderWidth || j < borderWidth || j >= cols - borderWidth) {
                data[i][j] = 1;
            }
        }
    }

    // Scatter random color codes
    for (int k = 0; k < numRandomCells; k++) {
        int i = random.nextInt(rows - 2 * borderWidth) + borderWidth;
        int j = random.nextInt(cols - 2 * borderWidth) + borderWidth;
        data[i][j] = random.nextInt(numColors) + 2; // Start colors from 2 to 9
	    }

    return data;
}


}
