package com.sulfurengine.io;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Input {

    private static boolean[] keyStates = new boolean[256];
    private static boolean[] mouseButtonStates = new boolean[4];
    private static Point mousePosition = new Point();

    // Initialize the input handling for a given JFrame
    public static void initialize(JFrame frame) {
        // Add key listener to the frame
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode >= 0 && keyCode < keyStates.length) {
                    keyStates[keyCode] = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode >= 0 && keyCode < keyStates.length) {
                    keyStates[keyCode] = false;
                }
            }
        });

        // Add mouse listener to the frame
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int button = e.getButton();
                if (button >= 1 && button < mouseButtonStates.length) {
                    mouseButtonStates[button] = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int button = e.getButton();
                if (button >= 1 && button < mouseButtonStates.length) {
                    mouseButtonStates[button] = false;
                }
            }
        });

        // Add mouse motion listener to track mouse position
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosition = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mousePosition = e.getPoint();
            }
        });
    }

    // Check if a specific key is down
    public static boolean isKeyDown(int keyCode) {
        if (keyCode >= 0 && keyCode < keyStates.length) {
            return keyStates[keyCode];
        }
        return false;
    }

    // Check if a specific mouse button is down
    public static boolean isMouseButtonDown(int button) {
        if (button >= 1 && button < mouseButtonStates.length) {
            return mouseButtonStates[button];
        }
        return false;
    }

    // Get the current mouse cursor position
    public static Point getMousePosition() {
        return mousePosition;
    }

    // Get the vertical axis value (-1, 0, or 1)
    public static int getAxisVertical() {
        int value = 0;
        if (isKeyDown(KeyEvent.VK_W) || isKeyDown(KeyEvent.VK_UP)) {
            value += 1;
        }
        if (isKeyDown(KeyEvent.VK_S) || isKeyDown(KeyEvent.VK_DOWN)) {
            value -= 1;
        }
        return -value;
    }

    // Get the horizontal axis value (-1, 0, or 1)
    public static int getAxisHorizontal() {
        int value = 0;
        if (isKeyDown(KeyEvent.VK_A) || isKeyDown(KeyEvent.VK_LEFT)) {
            value -= 1;
        }
        if (isKeyDown(KeyEvent.VK_D) || isKeyDown(KeyEvent.VK_RIGHT)) {
            value += 1;
        }
        return value;
    }
}