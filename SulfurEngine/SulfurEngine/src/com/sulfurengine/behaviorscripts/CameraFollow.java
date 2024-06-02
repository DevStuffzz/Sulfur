package com.sulfurengine.behaviorscripts;

import java.awt.event.KeyEvent;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Input;
import com.sulfurengine.io.SceneManager;
import com.sulfurengine.util.Vec2;

public class CameraFollow extends Script{

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		Vec2 targetPos = parent.transform.pos.add(new Vec2(0, -100));
		targetPos = targetPos.add(targetPos.subtract(targetPos.slerp(targetPos, dt)));
		targetPos.y = Math.min(600, targetPos.y);
		Display.currentScene.camera.position = targetPos;
		
		if(Input.isKeyDown(KeyEvent.VK_R)) {
			SceneManager.SetScene(1);
		}
	}

}
