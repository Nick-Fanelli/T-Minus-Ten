package com.harmonygames.engine.utils;

import com.harmonygames.engine.display.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static final int NUM_KEYS = 256;
    private static final boolean[] keys     = new boolean[NUM_KEYS];
    private static final boolean[] keysLast = new boolean[NUM_KEYS];

    public static final int NUM_BUTTONS = 5;
    private static final boolean[] buttons       = new boolean[NUM_BUTTONS];
    private static final boolean[] buttonsLast   = new boolean[NUM_BUTTONS];

    private static final Vector2f mousePosition = new Vector2f();
    private static int collectiveScroll = 0;
    private static int scroll = 0;

    public Input() {
        JFrame frame = Window.getContext().getFrame();
        Canvas canvas = Window.getContext().getCanvas();

        frame.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);

        canvas.setFocusable(true);
        canvas.requestFocus();
    }

    public void update() {
        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);
        System.arraycopy(buttons, 0, buttonsLast, 0, NUM_BUTTONS);

        scroll = 0;
    }

    // Keys
    public static boolean isKey(int keycode) { return keys[keycode]; }
    public static boolean isKeyUp(int keycode) { return !keys[keycode] && keysLast[keycode]; }
    public static boolean isKeyDown(int keycode) { return keys[keycode] && !keysLast[keycode]; }

    // Buttons
    public static boolean isMouseButton(int button) { return buttons[button]; }
    public static boolean isMouseButtonUp(int button) { return !buttons[button] && buttonsLast[button]; }
    public static boolean isMouseButtonDown(int button) { return buttons[button] && !buttonsLast[button]; }

    // Mouse
    public static Vector2f getMousePosition() { return mousePosition; }
    public static int getCollectiveScroll() { return collectiveScroll; }
    public static int getScroll() { return scroll; }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() <= NUM_KEYS) keys[e.getKeyCode()] = true;
        else if(e.getKeyCode() > NUM_KEYS) {
            System.err.println("[Harmony Engine (Input)]: Can not register key with code of " + e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() <= NUM_KEYS) {
            keys[e.getKeyCode()] = false;
        } else {
            System.err.println("[Harmony Engine (Input)]: Can not register key with code of " + e.getKeyCode());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() <= NUM_BUTTONS) {
            buttons[e.getButton()] = true;
        } else {
            System.err.println("[Harmony Engine (Input)]: Can not register button with code of " + e.getButton());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() <= NUM_BUTTONS) {
            buttons[e.getButton()] = false;
        } else {
            System.err.println("[Harmony Engine (Input)]: Can not register button with code of " + e.getButton());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition.set(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseMoved(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
        collectiveScroll += e.getWheelRotation();
    }

    @Deprecated @Override public void keyTyped(KeyEvent e) {}
    @Deprecated @Override public void mouseClicked(MouseEvent e) {}
    @Deprecated @Override public void mouseEntered(MouseEvent e) {}
    @Deprecated @Override public void mouseExited(MouseEvent e) {}
}
