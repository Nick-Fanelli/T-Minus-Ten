package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.renderer.SpriteRenderer;
import com.harmonygames.engine.graphics.SpriteSheet;
import com.harmonygames.engine.math.PerlinNoise;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.engine.physics2D.components.BoxCollider2D;
import com.harmonygames.engine.physics2D.components.Rigidbody2D;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Assets;
import com.harmonygames.tMinusTen.objects.Player;

import java.awt.event.KeyEvent;

public class PlanetScene extends Scene {

    public PlanetScene() { super("Planet Scene"); }

    private Player player;
    private SpriteSheet spriteSheet;
    private GameObject[] gameObjects = new GameObject[30];
    private PerlinNoise heightNoiseMap = new PerlinNoise(100);

    @Override
    public void onCreate() {
        super.onCreate();

        Camera.position.set(0, -500);
//        player = new Player();
//        player.transform.position.set(0, -100);
//        super.addGameObject(player);

        spriteSheet = Assets.addSpriteSheet("assets/spritesheets/ground/grasstileset.png", 30, 30);

        this.generateChunk(0, 0, 30, 30, 15, 15);
        this.generateChunk(0, 1, 30, 30, 15, 15);
    }

    private void generateChunk(int chunkX, int chunkY, int tileWidth, int tileHeight, int chunkWidth, int chunkHeight) {
        for(int x = 0; x < chunkWidth; x++) {
            int yOffset;

            // FIXME: Clip the range so the yOffset can not be greater than the chunk height!!
            if(chunkY == 0) yOffset = (int) Math.round(heightNoiseMap.noise((chunkX * chunkWidth) + x) * 10f);
            else yOffset = (chunkY * chunkHeight);

            int xPos = (chunkX * (chunkWidth * tileWidth)) + tileWidth * x;

            GameObject gameObject = new GameObject("Generate_Tile", new Transform(
                    new Vector2f(xPos, yOffset * tileHeight), new Scale(tileWidth, tileHeight)
            ));

            gameObject.addComponent(new SpriteRenderer(spriteSheet.getSprite(chunkY == 0 ? 0 : 1, chunkY == 1 ? 0 : 1)));
            gameObject.addComponent(new BoxCollider2D(new Vector2f(), gameObject.transform.scale));

            super.addGameObject(gameObject);

            int heightInverseAdjustment = chunkHeight != yOffset ? yOffset : 0;

            for(int y = 0; y < chunkHeight - 1 - heightInverseAdjustment; y++) {
                GameObject gameObject1 = new GameObject("Generated_Tile", new Transform(
                        new Vector2f(xPos, (yOffset + 1 + y) * tileHeight), new Scale(tileWidth, tileHeight)
                ));
                gameObject1.addComponent(new SpriteRenderer(spriteSheet.getSprite(1, chunkY == 1 ? 0 : 1)));
                gameObject1.addComponent(new BoxCollider2D(new Vector2f(), gameObject1.transform.scale));

                super.addGameObject(gameObject1);
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Input.isKey(KeyEvent.VK_LEFT)) Camera.position.x -= 100 * deltaTime;
        if(Input.isKey(KeyEvent.VK_RIGHT)) Camera.position.x += 100 * deltaTime;
        if(Input.isKey(KeyEvent.VK_UP)) Camera.position.y -= 100 * deltaTime;
        if(Input.isKey(KeyEvent.VK_DOWN)) Camera.position.y += 100 * deltaTime;
    }

}
