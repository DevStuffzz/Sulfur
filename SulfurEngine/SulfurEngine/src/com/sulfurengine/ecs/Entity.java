package com.sulfurengine.ecs;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Entity {
	public String name;
	public Transform transform;
	
	private List<Script> scripts;
	
	public Entity(String name, Transform t) {
		this.name = name;
		this.transform = t;
		this.scripts = new ArrayList<>();
	}
	
	
	 public <T extends Script> T getScript(Class<T> ScriptClass) {
	        for (Script c : scripts) {
	            if (ScriptClass.isAssignableFrom(c.getClass())) {
	                try {
	                    return ScriptClass.cast(c);
	                } catch (ClassCastException e) {
	                    e.printStackTrace();
	                    assert false : "Error: Casting Script.";
	            }
	        }
	    }
	
	    return null;
	}
	
	public <T extends Script> void removeScript(Class<T> ScriptClass) {
	    for (int i=0; i < scripts.size(); i++) {
	        Script s = scripts.get(i);
	        if (ScriptClass.isAssignableFrom(s.getClass())) {
	            scripts.remove(i);
	            return;
	        }
	    }
	}
	
	public void addScript(Script s) {
	    this.scripts.add(s);
	    s.parent = this;
	}

	public void start() {
		for(Script s : scripts) {
			s.start();
		}
	}
	
	public void update(float dt) {
		for(Script s : scripts) {
			s.update(dt);
		}
	}

	public void onCollision(Entity other) {
		for(Script s : scripts) {
			s.onCollision(other);
		}
	}

	public Rectangle bounds() {
		Rectangle r = new Rectangle();
		r.setBounds((int)transform.pos.x, (int)transform.pos.y, (int)transform.scale.x, (int)transform.scale.y);
		return r;
	}
}
