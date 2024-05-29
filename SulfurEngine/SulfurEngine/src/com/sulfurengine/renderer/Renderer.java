package com.sulfurengine.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

import com.sulfurengine.behaviorscripts.Spriterenderer;
import com.sulfurengine.ecs.Entity;
import com.sulfurengine.ecs.Transform;
import com.sulfurengine.io.Display;
import com.sulfurengine.util.Debug;
import com.sulfurengine.util.Vec2;
import com.sulfurengine.util.Vec2i;

public class Renderer extends JPanel {

    private static final long serialVersionUID = 1L;

    private Display display;
    private int originalWidth;
    private int originalHeight;
    private double scaleX;
    private double scaleY;

    public Renderer() {
        display = Display.get();
        originalWidth = display.width();
        originalHeight = display.height();

        this.setBounds(0, 0, originalWidth, originalHeight);
        this.setDoubleBuffered(true);

        // Update scaling factor when the component is resized
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                updateScalingFactors();
                repaint();
            }
        });
    }

    private void updateScalingFactors() {
        scaleX = getWidth() / (double) originalWidth;
        scaleY = getHeight() / (double) originalHeight;
    }

    @Override
    public void paint(Graphics grap) {
        super.paint(grap);

        Graphics2D g = (Graphics2D) grap;

        // Apply the scaling factor
        AffineTransform oldTransform = g.getTransform();
        g.scale(scaleX, scaleY);

        Camera camera = Display.currentScene.camera;
        Vec2 cameraPos = camera.getPosition();

        // Offset based on camera position with camera as center of the screen
        int offsetX = (int) (cameraPos.x - originalWidth / 2);
        int offsetY = (int) (cameraPos.y - originalHeight / 2);

        Transform t = new Transform(new Vec2(0, 0), new Vec2(originalWidth, originalHeight));
        Vec2i pos = Vec2i.convert(t.pos);
        Vec2i scale = Vec2i.convert(t.scale);

        Spriterenderer background = Display.currentScene.background;
        if (background.colored()) {
            g.setColor(background.getColor());
            g.fillRect(pos.x, pos.y, scale.x, scale.y);
        } else {
            Image img = background.getSprite().getImage();
            g.drawImage(img, pos.x, pos.y, scale.x, scale.y, this);
        }


        if (Debug.DEBUG) {
            g.setColor(Color.red);
            for (Entity e : Display.currentScene.getAllEntities()) {
                Transform tr = e.transform;
                Vec2i p = Vec2i.convert(tr.pos).sub(new Vec2(offsetX, offsetY));
                Vec2i s = Vec2i.convert(tr.scale);


                // Apply rotation
                AffineTransform oldEntityTransform = g.getTransform();
                g.rotate(Math.toRadians(tr.rotation), p.x, p.y);
                g.fillRect(p.x - s.x / 2, p.y - s.y / 2, s.x, s.y);
                g.setTransform(oldEntityTransform);
            }
        } else {
            for (Spriterenderer sprite : Display.currentScene.getAllSprites()) {
                Transform t1 = sprite.parent.transform;
                Vec2i p = Vec2i.convert(t1.pos).sub(new Vec2(offsetX, offsetY));
                Vec2i s = Vec2i.convert(t1.scale);


                // Apply rotation
                AffineTransform oldEntityTransform = g.getTransform();
                g.rotate(Math.toRadians(t1.rotation), p.x, p.y);

                if (sprite.colored()) {
                    g.setColor(sprite.getColor());
                    g.fillRect(p.x - s.x / 2, p.y - s.y / 2, s.x, s.y);
                } else {
                    Image img = sprite.getSprite().getImage();

                    // Check for flipping
                    int imgWidth = img.getWidth(this);
                    int imgHeight = img.getHeight(this);
                    int dx1 = p.x - s.x / 2;
                    int dy1 = p.y - s.y / 2;
                    int dx2 = p.x + s.x / 2;
                    int dy2 = p.y + s.y / 2;
                    int sx1 = 0;
                    int sy1 = 0;
                    int sx2 = imgWidth;
                    int sy2 = imgHeight;

                    if (sprite.flipX) {
                        int temp = sx1;
                        sx1 = sx2;
                        sx2 = temp;
                    }
                    if (sprite.flipY) {
                        int temp = sy1;
                        sy1 = sy2;
                        sy2 = temp;
                    }

                    g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
                }
                g.setTransform(oldEntityTransform); // Reset transform after drawing
            }
        }
        
        g.setTransform(oldTransform);

        for (TextRenderer tr : Display.currentScene.getAllTexts()) {
            Vec2i textPos = Vec2i.convert(tr.parent.transform.pos);
            Vec2i offsetTextPos = new Vec2i(textPos.x - offsetX, textPos.y - offsetY);
            g.setColor(tr.color);

            // Set the font before drawing the text
            g.setFont(tr.font);
            

            // Calculate the position for centered text
            int textWidth = g.getFontMetrics().stringWidth(tr.text);
            int textX = offsetTextPos.x - (textWidth / 2);
            int textY = offsetTextPos.y;

            // Draw the text
            g.drawString(tr.text, textX, textY);
        }
        
        for(LineRenderer lr : Display.currentScene.getAllLines()) {
        	lr.render(g);
        }


        // Reset the scaling transform
    }
}
