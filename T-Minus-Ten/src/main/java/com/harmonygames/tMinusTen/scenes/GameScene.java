package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.BoxCollider2D;
import com.harmonygames.engine.gameobject.component.Rigidbody2D;
import com.harmonygames.engine.graphics.Tilemap;
import com.harmonygames.engine.physics2D.Collision2D;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.engine.utils.TiledTilemapLoader;
import com.harmonygames.engine.utils.Vector2f;
import com.harmonygames.tMinusTen.objects.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameScene extends Scene {

    private Player player;

    @Override
    public void onCreate() {
        super.onCreate();

        TiledTilemapLoader loader = new TiledTilemapLoader(Assets.getFile("assets/maps/space-station.tmx"));
        Tilemap[] tilemaps = loader.load();

        for (Tilemap tilemap : tilemaps) tilemap.addGameObjectsToScene(this);

        player = new Player();
        super.addGameObject(player);

        Camera.position.set(player.transform.position.x - Display.getFrame().getWidth() / 2f, player.transform.position.y - Display.getFrame().getHeight() / 2f);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Display.getFrame().getWidth(), Display.getFrame().getHeight());
        super.draw(g);
    }
}
