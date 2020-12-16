package com.harmonygames.tMinusTen.objects;

import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.physics2D.components.BoxCollider2D;
import com.harmonygames.engine.physics2D.components.Rigidbody2D;
import com.harmonygames.engine.gameobject.component.renderer.AnimationRenderer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {

    private SpriteSheet playerSheet = null;
    private AnimationRenderer renderer;
    private Rigidbody2D rigidbody2D;
    private BoxCollider2D collider2D;

    private float playerSpeedForce = 3.5f;

    public Player() {
        super("Player", new Transform(new Vector2f(1000, 1000), new Scale(32, 32)));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        playerSheet = Assets.addSpriteSheet("assets/spritesheets/characters/player-sheet.png", 32, 64);

        super.addComponent(this.renderer = new AnimationRenderer(playerSheet, 0));
        super.addComponent(this.collider2D = new BoxCollider2D(new Vector2f(4f, 52), new Scale(24, 10)));
        super.addComponent(this.rigidbody2D = new Rigidbody2D());

        this.rigidbody2D.setMass(5f);
        this.rigidbody2D.setHasGravity(true);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        boolean isMoving = false;

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

        if(isMoving) renderer.incrementMillis(100);
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
