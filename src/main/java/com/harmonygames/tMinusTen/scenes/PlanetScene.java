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
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.tMinusTen.chunk.Chunk;
import com.harmonygames.tMinusTen.chunk.ChunkLoader;
import com.harmonygames.tMinusTen.objects.Player;

import java.awt.*;

public class PlanetScene extends Scene {

    private Player player;

    private SimilarObjectContainer<Chunk> chunkContainer;
    private ChunkLoader chunkLoader;
    private SpriteSheet spriteSheet;
    private Box selectionBox = new Box("Selection Box", new Transform(new Vector2f(0, 0), new Scale(tileWidth, tileHeight)));

    public PlanetScene() { super("Planet Scene"); }

    public static final int chunkWidth = 15, chunkHeight = 15, tileWidth = 30, tileHeight = 30;

    @Override
    public void onCreate() {
        super.onCreate();

        Camera.position.set(0, -500);

        spriteSheet = Assets.addSpriteSheet("/spritesheets/planet/mars/mars-tileset.png", 512, 512);
        chunkContainer = new SimilarObjectContainer<>("ChunkContainer", this);
        chunkLoader = new ChunkLoader(this, chunkContainer, spriteSheet, tileWidth, tileHeight, chunkWidth, chunkHeight);

        super.addGameObject(chunkContainer);

        player = new Player();
        player.transform.position.set(0, -200);
        super.addGameObject(player);

        selectionBox.setType(Box.Type.STROKED);
        selectionBox.setStatic(true);
        super.addGameObject(selectionBox);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        selectionBox.transform.position = Input.getMousePosition();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, Display.getAspectRatio().width, Display.getAspectRatio().height);

        super.draw(g);
    }

}
