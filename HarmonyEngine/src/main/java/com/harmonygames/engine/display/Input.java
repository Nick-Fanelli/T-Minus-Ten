package com.harmonygames.engine.display;

import com.harmonygames.engine.event.events.ControllerConnectionAction;
import com.harmonygames.engine.event.events.ControllerConnectionEvent;
import com.harmonygames.engine.event.EventSystem;
import com.harmonygames.engine.math.Vector2f;
import com.studiohartman.jamepad.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static final int NUM_KEYS = 256;
    private static final boolean[] keys     = new boolean[NUM_KEYS];
    private static final boolean[] keysLast = new boolean[NUM_KEYS];

    public static final int NUM_BUTTONS = 5;
    private static final boolean[] buttons     = new boolean[NUM_BUTTONS];
    private static final boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private static final Vector2f mousePosition = new Vector2f();
    private static int scroll;

    private static final ControllerManager controllers = new ControllerManager();
    private static final EventSystem<ControllerConnectionEvent, ControllerConnectionAction> controllerEventSystem = new EventSystem<>();

    public Input(JFrame frame, Canvas canvas) {
        frame.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);

        canvas.setFocusable(true);
        canvas.requestFocus();

        // Handle Controller Stuff
        controllers.initSDLGamepad();
    }

    public void update() {
        // Handle Keyboard Inputs
        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);
        System.arraycopy(buttons, 0, buttonsLast, 0, NUM_BUTTONS);

        // Handle Controller Inputs
        controllers.update();
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
    public static int getScroll() { return scroll; }

    // Controller
    public static boolean isControllerButton(ControllerButton button, int controllerID) {
        try {
            if(!isControllerConnected(controllerID)) {
                System.err.println("***Harmony (Input): Controller '" + controllerID + "' not connected.***");
                return false;
            }
            return controllers.getControllerIndex(controllerID).isButtonPressed(button);
        } catch (ControllerUnpluggedException ignored) { }

        return false;
    }

    public static boolean isControllerButton(ControllerButton button) { return isControllerButton(button, 0); }

    public static boolean isControllerButtonDown(ControllerButton button, int controllerID) {
        try {
            if(!isControllerConnected(controllerID)) {
                System.err.println("***Harmony (Input): Controller '" + controllerID + "' not connected.***");
                return false;
            }
            return controllers.getControllerIndex(controllerID).isButtonJustPressed(button);
        } catch (ControllerUnpluggedException ignored) { }

        return false;
    }

    public static boolean isControllerButtonDown(ControllerButton button) { return isControllerButtonDown(button, 0); }

    public static boolean isControllerConnected(int controllerID) { return controllers.getControllerIndex(controllerID).isConnected(); }

    public static float getControllerAxis(ControllerAxis axis) {
        return Input.getControllerAxis(axis, 0);
    }
    public static float getControllerAxis(ControllerAxis axis, int controllerID) {
        try {
            if(!isControllerConnected(controllerID)) {
                System.err.println("***Harmony (Input): Controller '" + controllerID + "' not connected.***");
                return 0f;
            }
            return controllers.getControllerIndex(controllerID).getAxisState(axis);
        } catch (ControllerUnpluggedException e) {
            e.printStackTrace();
        }

        return 0f;
    }

    @Override public void keyPressed(KeyEvent e) { if(e.getKeyCode() <= keys.length) keys[e.getKeyCode()] = true; }

    @Override public void keyReleased(KeyEvent e) { if(e.getKeyCode() <= keys.length) keys[e.getKeyCode()] = false; }

    @Override public void mousePressed(MouseEvent e) { if(e.getButton() <= buttons.length) buttons[e.getButton()] = true; }
    @Override public void mouseReleased(MouseEvent e) { if(e.getButton() <= buttons.length) buttons[e.getButton()] = false; }

    @Override public void mouseDragged(MouseEvent e) {
        mousePosition.x = e.getX();
        mousePosition.y = e.getY();
    }

    @Override public void mouseMoved(MouseEvent e) {
        mousePosition.x = e.getX();
        mousePosition.y = e.getY();
    }

    @Override public void mouseWheelMoved(MouseWheelEvent e) { scroll = e.getWheelRotation(); }

    @Deprecated @Override public void keyTyped(KeyEvent keyEvent) {}
    @Deprecated @Override public void mouseClicked(MouseEvent e) {}
    @Deprecated @Override public void mouseEntered(MouseEvent e) {}
    @Deprecated @Override public void mouseExited(MouseEvent e) {}

    public static void addControllerConnectionEvent(ControllerConnectionEvent event) {
        Input.controllerEventSystem.subscribe(event);
    }

    public static void removeControllerConnectionEvent(ControllerConnectionEvent event) {
        Input.controllerEventSystem.unsubscribe(event);
    }
}
