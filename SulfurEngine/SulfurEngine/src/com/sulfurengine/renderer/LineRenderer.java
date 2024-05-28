package com.sulfurengine.renderer;

import java.awt.Color;
import java.awt.Graphics2D;

import com.sulfurengine.ecs.Script;
import com.sulfurengine.io.Display;
import com.sulfurengine.util.Vec2;

public class LineRenderer extends Script {

    public Vec2 startPoint;
    public Vec2 endPoint;
    public Color color;
    public float lineWidth;

    // Default color and line width
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final float DEFAULT_LINE_WIDTH = 1.0f;

    // Constructor with all parameters
    public LineRenderer(Vec2 startPoint, Vec2 endPoint, Color color, float lineWidth) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.color = color;
        this.lineWidth = lineWidth;
    }

    // Constructor with default color and line width
    public LineRenderer(Vec2 startPoint, Vec2 endPoint) {
        this(startPoint, endPoint, DEFAULT_COLOR, DEFAULT_LINE_WIDTH);
    }

    // Constructor with default line width
    public LineRenderer(Vec2 startPoint, Vec2 endPoint, Color color) {
        this(startPoint, endPoint, color, DEFAULT_LINE_WIDTH);
    }

    // Constructor with start and end points only, setting endPoint to startPoint for an empty line
    public LineRenderer(Vec2 startPoint) {
        this(startPoint, startPoint, DEFAULT_COLOR, DEFAULT_LINE_WIDTH);
    }

    @Override
    public void start() {
        // No initialization needed for now
    }

    @Override
    public void update(float dt) {
        // No per-frame logic needed for now
    }

    public void render(Graphics2D g) {
        // Get the current camera position
        Vec2 cameraPos = Display.currentScene.camera.getPosition();
        Display display = Display.get();

        // Calculate the offset from the camera (which is the center of the screen)
        Vec2 offset = new Vec2(display.width() / 2, display.height() / 2);

        // Calculate screen-space positions based on world-space positions
        Vec2 screenStart = new Vec2(startPoint.x - cameraPos.x + offset.x, startPoint.y - cameraPos.y + offset.y);
        Vec2 screenEnd = new Vec2(endPoint.x - cameraPos.x + offset.x, endPoint.y - cameraPos.y + offset.y);

        // Set the line color and width
        g.setColor(color);
        g.setStroke(new java.awt.BasicStroke(lineWidth));

        // Draw the line in screen space, adjusted from world space
        g.drawLine((int) screenStart.x, (int) screenStart.y, (int) screenEnd.x, (int) screenEnd.y);
    }
}
