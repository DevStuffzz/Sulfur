package com.sulfurengine.io;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.behaviorscripts.WatermarkScene;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.renderer.Renderer;
import com.sulfurengine.renderer.Sprite;
import com.sulfurengine.renderer.TextRenderer;
import com.sulfurengine.util.Debug;
import com.sulfurengine.util.Prefabs;
import com.sulfurengine.util.Vec2;

public class Display {
	private int width, height;
	private String title;
	private static Display instance = null;
	
	private JFrame frame;
	private Renderer renderer;
	private Scene watermarkScene;
	public static Scene currentScene = new Scene();
	
	
	private Display() {};
	
	public static Display get() {
		if(instance == null) {
			instance = new Display();
		} 
		return instance;
	}
	

	
	public void init(int w, int h, String t) {
		watermarkScene = new Scene();
		watermarkScene.background = new Spriterenderer(Color.red);
		Entity watermark = Prefabs.ImageEntity(new Sprite("/resources/branding/logo.png"));
		
		watermark.transform.scale = new Vec2(400, 300);
		watermark.addScript(new WatermarkScene());

		Entity text = Prefabs.TextEntity("Made using Sulfur Engine", TextRenderer.DEFAULT_FONT);
		text.transform.pos = new Vec2(0, 200);

		watermarkScene.addEntity(watermark);
		watermarkScene.addEntity(text);

		SceneManager.AddScene(watermarkScene);

		this.width = w;
		this.height = h;
		this.title = t;
		
		frame = new JFrame(title);
		frame.setSize(width, height);
		
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setIconImage(new Sprite("/resources/branding/logo.png").getImage());
		
		Input.initialize(frame);
		
	     frame.addComponentListener(new ComponentAdapter() {
	            @Override
	            public void componentResized(ComponentEvent e) {
	                Dimension newSize = frame.getSize();
	                width = newSize.width;
	                height = newSize.height;
	            }
	        });		
		renderer = new Renderer();
		frame.add(renderer);
		
		frame.setVisible(true);
	}
	
	public void update() {
	    long beginTime = System.currentTimeMillis();
	    long endTime;
	    float dt = 0.0f; // Initialize delta time to zero

	    if(currentScene == null) {
	    	Debug.LogError("Tried to update a null scene");
	    	close();
	    }
	    
	    // Calculate delta time only if currentScene is not null
	    endTime = System.currentTimeMillis();

	    if (endTime > beginTime) { // Ensure endTime is greater than beginTime
	        dt = (endTime - beginTime) / 1000.0f; // Convert milliseconds to seconds
	    }
	    
	    if(dt > 0) {
	        // Call scene update and render
	        currentScene.update(dt);
	        renderer.repaint();
	    }
	    
	    if(Input.isKeyDown(KeyEvent.VK_4)) {
	    	BufferedImage img = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
	    	frame.paint(img.getGraphics());
	    	File outputfile = new File("saved.png");
	    	try {
				ImageIO.write(img, "png", outputfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}


	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public static String title(String title) {
		get().title = title;
		get().frame.setTitle(title);
		return title();
	}
	
	public static String title() {
		return get().title;
	}
	
	public static boolean open() {
		return !Input.isKeyDown(KeyEvent.VK_ESCAPE);
	}

	public void close() {
		frame.dispose();
		System.exit(0);
	}

	public Renderer renderer() {
		return this.renderer;
	}

	public void setIcon(Sprite sprite) {
		frame.setIconImage(sprite.getImage());
	}
}
