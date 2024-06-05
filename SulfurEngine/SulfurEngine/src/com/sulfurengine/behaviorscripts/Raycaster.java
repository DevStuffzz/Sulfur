package com.sulfurengine.behaviorscripts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.io.Input;
import com.sulfurengine.renderer.Sprite;

public class Raycaster extends Script {

    private BufferedImage offscreenImage;
    private Graphics2D offscreenGraphics;
    private int[][] data;

    private double posX, posY; // Player position
    private double dirX, dirY; // Direction vector
    private double planeX, planeY; // Camera plane
    
    private boolean dirty;
    Color floorColor, ceilingColor;

    public Raycaster(int[][] data, Color floorColor, Color ceilingColor) {
        this.data = data;
        this.floorColor = floorColor;
        this.ceilingColor = ceilingColor;
        int width = Display.get().width();
        int height = Display.get().height();
        this.offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.offscreenGraphics = offscreenImage.createGraphics();
        this.posX = 1.5; // Initial player X position
        this.posY = 1.5; // Initial player Y position
        this.dirX = -1; // Initial direction vector X
        this.dirY = 0; // Initial direction vector Y
        this.planeX = 0; // Initial camera plane X
        this.planeY = 0.66; // Initial camera plane Y (aspect ratio)
    }

    @Override
    public void start() {
    }
    boolean firstFrame = true;

    @Override
    public void update(float dt) {
    	// Swapped because math is swapped
    	boolean d = Input.isKeyDown(KeyEvent.VK_A);
    	boolean a = Input.isKeyDown(KeyEvent.VK_D);
    	
    	if(Input.isKeyDown(KeyEvent.VK_R)) {
    		regenerateData();
    		return;
    	}
    	
        boolean w = Input.isKeyDown(KeyEvent.VK_W);
        boolean s = Input.isKeyDown(KeyEvent.VK_S);

        boolean left = Input.isKeyDown(KeyEvent.VK_LEFT);
        boolean right = Input.isKeyDown(KeyEvent.VK_RIGHT);
        
        if(w || a || s || d || left || right || firstFrame) {
        	
        firstFrame = false;

        // Movement logic
        double moveSpeed = dt * 5.0; // Move speed
        double rotSpeed = dt * 3.0; // Rotation speed

        if (w) {
            if (data[(int)(posX + dirX * moveSpeed)][(int)posY] == 0) posX += dirX * moveSpeed;
            if (data[(int)posX][(int)(posY + dirY * moveSpeed)] == 0) posY += dirY * moveSpeed;
        }
        if (s) {
            if (data[(int)(posX - dirX * moveSpeed)][(int)posY] == 0) posX -= dirX * moveSpeed;
            if (data[(int)posX][(int)(posY - dirY * moveSpeed)] == 0) posY -= dirY * moveSpeed;
        }
        if (a) {
            if (data[(int)(posX + dirY * moveSpeed)][(int)posY] == 0) posX += dirY * moveSpeed;
            if (data[(int)posX][(int)(posY - dirX * moveSpeed)] == 0) posY -= dirX * moveSpeed;
        }
        if (d) {
            if (data[(int)(posX - dirY * moveSpeed)][(int)posY] == 0) posX -= dirY * moveSpeed;
            if (data[(int)posX][(int)(posY + dirX * moveSpeed)] == 0) posY += dirX * moveSpeed;
        }
        if (left) {
            // Rotate to the left
            double oldDirX = dirX;
            dirX = dirX * Math.cos(rotSpeed) - dirY * Math.sin(rotSpeed);
            dirY = oldDirX * Math.sin(rotSpeed) + dirY * Math.cos(rotSpeed);
            double oldPlaneX = planeX;
            planeX = planeX * Math.cos(rotSpeed) - planeY * Math.sin(rotSpeed);
            planeY = oldPlaneX * Math.sin(rotSpeed) + planeY * Math.cos(rotSpeed);
        }
        if (right) {
            // Rotate to the right
            double oldDirX = dirX;
            dirX = dirX * Math.cos(-rotSpeed) - dirY * Math.sin(-rotSpeed);
            dirY = oldDirX * Math.sin(-rotSpeed) + dirY * Math.cos(-rotSpeed);
            double oldPlaneX = planeX;
            planeX = planeX * Math.cos(-rotSpeed) - planeY * Math.sin(-rotSpeed);
            planeY = oldPlaneX * Math.sin(-rotSpeed) + planeY * Math.cos(-rotSpeed);
        }

        // Raycasting logic

        // Define floor and ceiling colors


        // Draw floor and ceiling
        int width = Display.get().width();
        int height = Display.get().height();
        offscreenGraphics.setColor(floorColor);
        offscreenGraphics.fillRect(0, height / 2, width, height / 2);
        offscreenGraphics.setColor(ceilingColor);
        offscreenGraphics.fillRect(0, 0, width, height / 2);

        for (int x = 0; x < width; x++) {
            double cameraX = 2 * x / (double) width - 1; // X-coordinate in camera space
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX;

            int mapX = (int) posX;
            int mapY = (int) posY;

            double sideDistX;
            double sideDistY;

            double deltaDistX = (rayDirX == 0) ? 1e30 : Math.abs(1 / rayDirX);
            double deltaDistY = (rayDirY == 0) ? 1e30 : Math.abs(1 / rayDirY);
            double perpWallDist;

            int stepX;
            int stepY;

            boolean hit = false; // Was there a wall hit?
            int side = 0; // Was a NS or a EW wall hit?

            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (posX - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - posX) * deltaDistX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (posY - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - posY) * deltaDistY;
            }

            while (!hit) {
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                if (data[mapX][mapY] > 0) hit = true;
            }

            if (side == 0) perpWallDist = (mapX - posX + (1 - stepX) / 2) / rayDirX;
            else perpWallDist = (mapY - posY + (1 - stepY) / 2) / rayDirY;

            int lineHeight = (int) (height / perpWallDist);

            int drawStart = -lineHeight / 2 + height / 2;
            if (drawStart < 0) drawStart = 0;
            int drawEnd = lineHeight / 2 + height / 2;
            if (drawEnd >= height) drawEnd = height - 1;

            Color color;
            switch (data[mapX][mapY]) {
                case 1:
                    color = Color.RED;
                    break;
                case 2:
                    color = Color.GREEN;
                    break;
                case 3:
                    color = Color.BLUE;
                    break;
                case 4:
                    color = Color.WHITE;
                    break;
                case 5:
                    color = Color.YELLOW;
                    break;
                case 6:
                	color = Color.PINK;
                	break;
                case 7:
                	color = Color.CYAN;
                	break;
                case 8:
                	color = Color.BLACK;
                	break;
                    
		           default:
		        	   color = Color.MAGENTA;
		        	   break;
            }

            if (side == 1) {
                color = color.darker();
            }

            offscreenGraphics.setColor(color);
            offscreenGraphics.drawLine(x, drawStart, x, drawEnd);
        }

        // Draw the offscreen image to the main image
        Graphics2D g2d = ((BufferedImage) parent.getScript(Spriterenderer.class).getSprite().getImage()).createGraphics();
        g2d.drawImage(offscreenImage, 0, 0, null);
        g2d.dispose();

        // Update the sprite with the new image
        parent.getScript(Spriterenderer.class).setSprite(Sprite.Generate(offscreenImage));
    
        }
    }

	private void regenerateData() {
		int borderWidth = 1;
		int numRandomCells = 100;
		 Random random = new Random();
		 int rows = 30;
		 int cols = 30;
		 data = new int[rows][cols];

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

	
	}
}
