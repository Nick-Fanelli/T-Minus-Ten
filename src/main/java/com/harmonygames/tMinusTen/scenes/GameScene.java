package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.graphics.Tilemap;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.engine.utils.TiledTilemapLoader;
import com.harmonygames.tMinusTen.objects.Player;

import java.awt.*;

public class GameScene extends Scene {

    private Player player;

    public GameScene() { super("Game Scene"); }

    @Override
    public void onCreate() {
        super.onCreate();

        TiledTilemapLoader loader = new TiledTilemapLoader(Assets.getFile("/maps/space-station.tmx"));
        Tilemap[] tilemaps = loader.load();

        for (Tilemap tilemap : tilemaps) tilemap.addGameObjectsToScene(this);

        player = new Player();
        super.addGameObject(player);

        Camera.position.set(player.transform.position.x - (float) Display.getAspectRatio().getWidth() / 2f, player.transform.position.y - (float) Display.getAspectRatio().getHeight() / 2f);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int) Display.getAspectRatio().getWidth(), (int) Display.getAspectRatio().getHeight());
        super.draw(g);
    }
}
