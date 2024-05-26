package com.sulfurengine.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

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

    public Renderer() {
        display = Display.get();

        this.setBounds(0, 0, display.width(), display.height());
        this.setDoubleBuffered(true);

        // Additional repaint triggers
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics grap) {
        super.paint(grap);

        Graphics2D g = (Graphics2D)grap;

        Transform t = new Transform(new Vec2(0, 0), new Vec2(display.width(), display.height()));
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

        Font font = new Font("Arial", Font.PLAIN, 12);

        for (TextRenderer tr : Display.currentScene.getAllTexts()) {
            Vec2i textPos = Vec2i.convert(tr.parent.transform.pos);
            g.setColor(Color.black);
            
            // Set the font before drawing the text
            g.setFont(font);
            
            // Calculate the position for centered text
            int textWidth = g.getFontMetrics().stringWidth(tr.text);
            int textX = textPos.x - (textWidth / 2);
            int textY = textPos.y;
            
            // Draw the text
            g.drawString(tr.text, textX, textY);
        }

        if (Debug.DEBUG) {
            g.setColor(Color.red);
            for (Entity e : Display.currentScene.getAllEntities()) {
                Transform tr = e.transform;
                Vec2i p = Vec2i.convert(tr.pos);
                Vec2i s = Vec2i.convert(tr.scale);
                g.fillRect(p.x - s.x / 2, p.y - s.y / 2, s.x, s.y);
            }
        } else {
            for (Spriterenderer sprite : Display.currentScene.getAllSprites()) {
                Transform t1 = sprite.parent.transform;
                Vec2i p = Vec2i.convert(t1.pos);
                Vec2i s = Vec2i.convert(t1.scale);

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
            }
        }
    }
}