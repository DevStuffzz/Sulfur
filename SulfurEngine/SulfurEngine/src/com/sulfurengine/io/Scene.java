package com.sulfurengine.io;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.renderer.Camera;
import com.sulfurengine.renderer.DirtyFlag;
import com.sulfurengine.renderer.LineRenderer;
import com.sulfurengine.renderer.TextRenderer;
import com.sulfurengine.util.Vec2;

public class Scene {
	private List<Entity> entities;
    private Queue<Entity> entitiesToAdd;
    private Queue<Entity> entitiesToDelete;
    
    public Camera camera;
	
	private boolean running = false;
	
	public Spriterenderer background = new Spriterenderer(Color.black);
	
	public Scene() {
        this.camera = new Camera(new Vec2(0, 0));
		entities = new ArrayList<>();
		entitiesToAdd = new LinkedList<>();
		entitiesToDelete = new LinkedList<>();
	}
	
	public void start() {
		for(Entity e : entities) {
			e.start();
		}
		DirtyFlag.dirty = true;
		running = true;
	}
	
	public Entity instantiate(Entity e) {
		this.entitiesToAdd.offer(e);
		if(running) {
			e.start();
		}
		return e;
	}
	
	
	public Entity getEntity(int index) {
		if(index > entities.size()) return null;
		return this.entities.get(index);
	}
	
	public List<Entity> getAllEntities() {
		return this.entities;
	}
	
	public List<Spriterenderer> getAllSprites() {
		 List<Spriterenderer> sprites = new ArrayList<>();
	        
	        // Use an iterator to safely iterate over entities
	        Iterator<Entity> iterator = entities.iterator();
	        while (iterator.hasNext()) {
	            Entity e = iterator.next();
	            Spriterenderer sr = e.getScript(Spriterenderer.class);
	            if(sr != null) {
	                sprites.add(sr);
	            }
	        }
	        
	        return sprites;
	}
	
	public List<TextRenderer> getAllTexts() {
		 List<TextRenderer> texts = new ArrayList<>();
	        
	        // Use an iterator to safely iterate over entities
	        Iterator<Entity> iterator = entities.iterator();
	        while (iterator.hasNext()) {
	            Entity e = iterator.next();
	            TextRenderer sr = e.getScript(TextRenderer.class);
	            if(sr != null) {
	                texts.add(sr);
	            }
	        }
	        
	        return texts;
	}
	
	
	public void update(float dt) {
		for(Entity e : entities) {
			e.update(dt);
		}
		
		// Add entities queued for addition
        while (!entitiesToAdd.isEmpty()) {
            entities.add(entitiesToAdd.poll());
        }
        
        while (!entitiesToDelete.isEmpty()) {
            entities.remove(entitiesToDelete.poll());
        }
	}
	
	public void addEntity(Entity e) {
		this.entities.add(e);
	}

	public void destroy(Entity e) {
		entitiesToDelete.offer(e);
	}
	
	@Override
	public Scene clone() {
		Scene clone = new Scene();
		
		if(background.colored()) {
			clone.background = new Spriterenderer(this.background.getColor());
		} else {
			clone.background = new Spriterenderer(this.background.getSprite());
		}
		for(Entity e : entities) {
			Entity c = e.clone();
			clone.addEntity(c);
		}
		
		return clone;
	}

	public List<LineRenderer> getAllLines() {
		 List<LineRenderer> lines = new ArrayList<>();
	        
        // Use an iterator to safely iterate over entities
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity e = iterator.next();
            LineRenderer lr = e.getScript(LineRenderer.class);
            if(lr != null) {
                lines.add(lr);
            }
        }
        
        return lines;
	}
}
 