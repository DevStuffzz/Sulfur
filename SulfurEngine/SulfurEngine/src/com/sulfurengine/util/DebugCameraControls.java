package com.sulfurengine.util;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Input;

public class DebugCameraControls extends Script {

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		Vec2 move = new Vec2(Input.getAxisHorizontal(), Input.getAxisVertical());
		Display.currentScene.camera.position = Display.currentScene.camera.position.add(move);
	}

}
