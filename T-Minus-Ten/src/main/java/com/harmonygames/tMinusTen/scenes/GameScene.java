package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.SpriteRenderer;
import com.harmonygames.engine.graphics.Tilemap;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.engine.utils.TiledTilemapLoader;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameScene extends Scene {

    @Override
    public void onCreate() {
        super.onCreate();

        TiledTilemapLoader loader = new TiledTilemapLoader(Assets.getFile("assets/maps/space-station.tmx"));
        Tilemap[] tilemaps = loader.load();

        for(Tilemap tilemap : tilemaps) tilemap.addGameObjectsToScene(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Input.isKey(KeyEvent.VK_RIGHT)) Camera.position.x += 200 * deltaTime;
        if(Input.isKey(KeyEvent.VK_LEFT)) Camera.position.x -= 200 * deltaTime;
        if(Input.isKey(KeyEvent.VK_DOWN)) Camera.position.y += 200 * deltaTime;
        if(Input.isKey(KeyEvent.VK_UP)) Camera.position.y -= 200 * deltaTime;

    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
