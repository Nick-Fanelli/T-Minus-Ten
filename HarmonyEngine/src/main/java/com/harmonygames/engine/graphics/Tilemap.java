package com.harmonygames.engine.graphics;

import com.harmonygames.engine.gameobject.Tile;
import com.harmonygames.engine.physics2D.components.BoxCollider2D;
import com.harmonygames.engine.gameobject.component.renderer.SpriteRenderer;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Scale;
import com.harmonygames.engine.utils.Transform;
import com.harmonygames.engine.utils.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tilemap {

    private ArrayList<Tile> tiles = new ArrayList<>();

    private String data;
    private Type type;
    private int tileWidth, tileHeight;
    private int numCols, numRows;

    private boolean loaded = false;

    public enum Type {
        SOLID, UI, INTERACTABLES, GROUND
    }

    public Tilemap(String data, Type type, int tileWidth, int tileHeight, int numCols, int numRows) {
        this.type = type;
        this.data = data;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.numCols = numCols;
        this.numRows = numRows;
    }

    public void load(ArrayList<Tileset> tilesets) {
        String[] tileValues = data.split(",");

        if(tileValues.length != numCols * numRows) {
            System.err.println("[Harmony Engine(Tilemap)]: String array tile values does not equal the number of columns by rows!!");
            System.exit(-1);
        }

        for(int i = 0; i < tileValues.length; i++) {
            if(tileValues[i].trim().equals("0") || tileValues[i].isBlank()) continue;

            BufferedImage image = getImage(tilesets, Integer.parseInt(tileValues[i]));

            int x = (i % numCols) * tileWidth;
            int y = Math.floorDiv(i, numCols) * tileHeight;

            Tile tile = new Tile("Tile:" + i, new Transform(new Vector2f(x, y), new Scale(tileWidth, tileHeight)));
            tile.addComponent(new SpriteRenderer(image));
            if(type == Type.SOLID) tile.addComponent(new BoxCollider2D(new Vector2f(), tile.transform.scale));

            tiles.add(tile);
        }

        this.loaded = true;
    }

    private BufferedImage getImage(ArrayList<Tileset> tilesets, int targetSprite) {
        Tileset tileset = null;
        int offset = -1;

        for(Tileset t : tilesets) {
            if(targetSprite >= t.getStartID()) {
                tileset = t;
                offset = t.getStartID();
            }
        }

        if(tileset == null || offset == -1) {
            System.err.println("[Harmony Engine(Tilemap)]: Could not identify what tileset/spritesheet iteration '" + targetSprite + "' belongs to!");
            System.exit(-1);
        }

        return tileset.getSpriteSheet().getSprite(targetSprite - offset);
    }

    public void addGameObjectsToScene(Scene scene) {
        if(!loaded) {
            System.err.println("[Harmony Engine (TileMap)]: The tilemap must be loaded before adding the game objects to the scene!");
            System.exit(-1);
        }

        for(Tile tile : tiles) scene.addGameObject(tile);
    }

    public Type getType() { return this.type; }
}
