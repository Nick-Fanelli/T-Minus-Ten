package com.harmonygames.tMinusTen.objects;

import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.BoxCollider2D;
import com.harmonygames.engine.gameobject.component.Rigidbody2D;
import com.harmonygames.engine.gameobject.component.renderer.SpriteRenderer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.engine.utils.Scale;
import com.harmonygames.engine.utils.Transform;
import com.harmonygames.engine.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {

    private SpriteSheet playerSheet = null;
    private Rigidbody2D rigidbody2D;
    private BoxCollider2D collider2D;

    private float playerSpeed = 3f;

    public Player() {
        super("Player", new Transform(new Vector2f(1300f, 913f), new Scale(32, 32)));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        playerSheet = Assets.addSpriteSheet("assets/spritesheets/characters/player-sheet.png", 4, 4);

        super.addComponent(new SpriteRenderer(playerSheet.getSprite(0, 0)));
        super.addComponent(this.collider2D = new BoxCollider2D(new Vector2f(4f, 52), new Scale(24, 10)));
        super.addComponent(this.rigidbody2D = new Rigidbody2D());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Input.isKey(KeyEvent.VK_RIGHT)) {
            rigidbody2D.setForceToNonzero(new Vector2f(playerSpeed, 0));
        }
        if(Input.isKey(KeyEvent.VK_LEFT)) {
            rigidbody2D.setForceToNonzero(new Vector2f(-playerSpeed, 0));
        }
        if(Input.isKey(KeyEvent.VK_DOWN)) {
            rigidbody2D.setForceToNonzero(new Vector2f(0, playerSpeed));
        }
        if(Input.isKey(KeyEvent.VK_UP)) {
            rigidbody2D.setForceToNonzero(new Vector2f(0, -playerSpeed));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

//        g.setColor(Color.GREEN);
//        g.drawRect((int) ((this.transform.position.x + collider2D.getOffset().x) - Camera.position.x), (int) ((this.transform.position.y + collider2D.getOffset().y) - Camera.position.y), collider2D.getScale().width, collider2D.getScale().height);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
