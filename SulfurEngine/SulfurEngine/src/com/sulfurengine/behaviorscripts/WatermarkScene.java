package com.sulfurengine.behaviorscripts;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.SceneManager;

public class WatermarkScene extends Script {

    float time = 2.0f;
    @Override
    public void start() {
        // TODO Auto-generated method stub
    }

    @Override
    public void update(float dt) {
        time -= dt;
        if(time <=0.0f) {
            SceneManager.SetScene(1);
        }
    }

}
