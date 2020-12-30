package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.Box;
import com.harmonygames.engine.gameobject.SimilarObjectContainer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.engine.physics2D.components.Rigidbody2D;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.tMinusTen.chunk.Chunk;
import com.harmonygames.tMinusTen.chunk.ChunkLoader;
import com.harmonygames.tMinusTen.objects.Player;
import com.harmonygames.tMinusTen.objects.SelectionBox;

import java.awt.*;

public class PlanetScene extends Scene {

    public static final int chunkWidth = 15, chunkHeight = 15, tileWidth = 30, tileHeight = 30;

    protected Player player;

    protected SimilarObjectContainer<Chunk> chunkContainer;
    protected ChunkLoader chunkLoader;
    protected SpriteSheet spriteSheet;
    protected SelectionBox selectionBox;

    public PlanetScene(String name, SpriteSheet planetSpriteSheet) {
        super(name);

        this.spriteSheet = planetSpriteSheet;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Camera.position.set(0, -500);

        chunkContainer = new SimilarObjectContainer<>("ChunkContainer", this);
        chunkLoader = new ChunkLoader(this, chunkContainer, spriteSheet, tileWidth, tileHeight, chunkWidth, chunkHeight);

        super.addGameObject(chunkContainer);

        player = new Player();
        player.transform.position.set(0, -200);
        super.addGameObject(player);

        selectionBox = new SelectionBox(player.getComponent(Rigidbody2D.class), new Scale(tileWidth, tileHeight));
        super.addGameObject(selectionBox);
    }

}
