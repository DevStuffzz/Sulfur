package com.sulfurengine.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Input;
import com.sulfurengine.io.SceneManager;
import com.sulfurengine.util.Vec2i;

public class SceneSwitchingButton extends Script {

    private int sceneToSwitch = 0;
    
    public SceneSwitchingButton(int scene) {
        sceneToSwitch = scene;
    }
    
    @Override
    public void start() {
        // Initialization logic if needed
    }

    @Override
    public void update(float dt) {
        // Get the transform of the parent entity
        Transform parentTransform = parent.transform;
        if (parentTransform != null) {
            // Calculate the bounding box centered around the parent entity's position
            Vec2i pos = Vec2i.convert(parentTransform.pos);
            Vec2i scale = Vec2i.convert(parentTransform.scale);
            int left = pos.x - scale.x / 2;
            int top = pos.y - scale.y / 2;
            Rectangle rect = new Rectangle(left, top, scale.x, scale.y);
            
            // Get the mouse position
            Point mouse = Input.getMousePosition();
            
            // Check if the mouse is within the bounding box and if the left mouse button is pressed
            if (rect.contains(mouse)) {
                if (Input.isMouseButtonDown(MouseEvent.BUTTON1)) {
                    SceneManager.SetScene(sceneToSwitch);
                }
            }
        }
    }
}
