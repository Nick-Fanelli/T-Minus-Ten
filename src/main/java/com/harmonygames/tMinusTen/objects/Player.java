package com.harmonygames.tMinusTen.objects;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.event.events.ControllerConnectionAction;
import com.harmonygames.engine.event.events.ControllerConnectionEvent;
import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.renderer.AnimationRenderer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.engine.physics2D.components.BoxCollider2D;
import com.harmonygames.engine.physics2D.components.Rigidbody2D;
import com.harmonygames.engine.utils.Assets;
import com.studiohartman.jamepad.ControllerAxis;
import com.studiohartman.jamepad.ControllerButton;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {

    private SpriteSheet playerSheet = null;
    private AnimationRenderer renderer;
    private Rigidbody2D rigidbody2D;
    private BoxCollider2D collider2D;

    private boolean handleGamepad = false;

    private float playerSpeedForce = 2f;

    public Player() { super("Player", new Transform(new Vector2f(0, 0), new Scale(32, 32))); }

    @Override
    public void onCreate() {
        super.onCreate();

        playerSheet = Assets.addSpriteSheet("/spritesheets/characters/player-sheet.png", 32, 64);

        super.addComponent(this.renderer = new AnimationRenderer(playerSheet, 0));
        super.addComponent(this.collider2D = new BoxCollider2D(new Vector2f(4f, 52), new Scale(24, 10)));
        super.addComponent(this.rigidbody2D = new Rigidbody2D());

        this.rigidbody2D.setMass(1f);
        this.rigidbody2D.setHasGravity(true);

        Input.addControllerConnectionEvent(action -> {
            if(action.controllerID == 0) this.handleGamepad = action.isConnected;
            System.out.println(action.isConnected);
        });
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        this.handleMovementInput();
        this.handleBlockInput();

        Camera.position.set(new Vector2f(this.transform.position).sub(Display.getCanvas().getWidth() / 2f, Display.getCanvas().getHeight() / 2f));
    }

    public void handleMovementInput() {
        boolean isMoving = false;

        // Desktop Controls
        if(Input.isKey(KeyEvent.VK_D) || Input.isKey(KeyEvent.VK_RIGHT)) {
            this.renderer.setAnimation(2);
            this.rigidbody2D.setForceToNonzero(new Vector2f(playerSpeedForce, 0));
            isMoving = true;
        }

        if(Input.isKey(KeyEvent.VK_A) || Input.isKey(KeyEvent.VK_LEFT)) {
            this.renderer.setAnimation(1);
            this.rigidbody2D.setForceToNonzero(new Vector2f(-playerSpeedForce, 0));
            isMoving = true;
        }

        if(Input.isKey(KeyEvent.VK_W) || Input.isKey(KeyEvent.VK_UP) || Input.isKey(KeyEvent.VK_SPACE)) {
            if(rigidbody2D.isColliding()) this.rigidbody2D.addForce(new Vector2f(0, -5f));
        }

        // Gamepad controls for moving
        if(this.handleGamepad) {

            // Gamepad move controls
            double moveForce = Math.round((Input.getControllerAxis(ControllerAxis.LEFTX) * playerSpeedForce) * 10D) / 10D;

            if(moveForce != 0) {
                this.rigidbody2D.setForceToNonzero(new Vector2f(Input.getControllerAxis(ControllerAxis.LEFTX) * playerSpeedForce, 0));
                this.renderer.setAnimation(Input.getControllerAxis(ControllerAxis.LEFTX) * playerSpeedForce > 0 ? 2 : 1);
                isMoving = true;
            }

            // Gamepad Jump Control
            if(Input.isControllerButtonDown(ControllerButton.A, 0) && rigidbody2D.isColliding())
                this.rigidbody2D.addForce(new Vector2f(0, -5f));
        }

        // If the player is moving, run the animation.
        // TODO: Based on the current player speed and difference from controller input!
        if(isMoving) renderer.incrementMillis(100);
    }

    public void handleBlockInput() {

    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
