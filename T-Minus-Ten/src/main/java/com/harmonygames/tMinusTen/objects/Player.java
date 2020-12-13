package com.harmonygames.tMinusTen.objects;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.SpriteRenderer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.engine.utils.Scale;
import com.harmonygames.engine.utils.Transform;
import com.harmonygames.engine.utils.Vector2f;

import java.awt.*;

public class Player extends GameObject {

    private SpriteSheet playerSheet = null;

    public Player() {
        super("Player", new Transform(new Vector2f(1300f, 913f), new Scale(32, 32)));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        playerSheet = Assets.addSpriteSheet("assets/spritesheets/characters/player-sheet.png", 4, 4);
        super.addComponent(new SpriteRenderer(playerSheet.getSprite(0, 0)));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
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
