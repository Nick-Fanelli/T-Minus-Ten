package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.engine.utils.TiledTilemapLoader;

public class GameScene extends Scene {

    @Override
    public void onCreate() {
        super.onCreate();

        TiledTilemapLoader loader = new TiledTilemapLoader(Assets.getFile("assets/maps/space-station.tmx"));
        loader.load();
    }

    @Override
    public void update(float deltaTime) {
    }
}
