package com.sulfurengine.ui;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.util.Vec2;

public class UiComponent extends Script {

    private Vec2 screenPosition; // Desired position in screen space

    public UiComponent() {
    }

    @Override
    public void start() {
        this.screenPosition = parent.transform.pos;
    }

    @Override
    public void update(float dt) {
    	 // Get the current camera position
        Vec2 cameraPos = Display.currentScene.camera.getPosition();
        Display display = Display.get();

        // Calculate the offset from the camera (which is the center of the screen)
        Vec2 offset = new Vec2(display.width() / 2, display.height() / 2);

        // Adjust the parent's transform position to keep it fixed on screen space
        // The screenPosition is relative to the top-left corner of the screen
        parent.transform.pos = new Vec2(cameraPos.x - offset.x + screenPosition.x,
                                        cameraPos.y - offset.y + screenPosition.y);
    }
}
