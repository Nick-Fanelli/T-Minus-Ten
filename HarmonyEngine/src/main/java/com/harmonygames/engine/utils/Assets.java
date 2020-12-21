package com.harmonygames.engine.utils;

import com.harmonygames.engine.graphics.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;

public class Assets {

    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    private static final HashMap<String, SpriteSheet> spriteSheets = new HashMap<>();
    private static final HashMap<String, File> files = new HashMap<>();

    public static BufferedImage getImage(String path) {
        if(images.containsKey(path)) return images.get(path);

        try {
            BufferedImage image = ImageIO.read(Assets.class.getResourceAsStream(path));
            images.put(path, image);
            return image;
        } catch (Exception e) {
            System.err.println("[Harmony Engine (Assets)]: Can not find image at: " + path);
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

    public static SpriteSheet addSpriteSheet(String path, int tileWidth, int tileHeight) {
        if(spriteSheets.containsKey(path)) return spriteSheets.get(path);

        SpriteSheet spriteSheet = new SpriteSheet(Assets.getImage(path), tileWidth, tileHeight);
        spriteSheets.put(path, spriteSheet);

        return spriteSheet;
    }

    public static SpriteSheet getSpriteSheet(String path) {
        if(spriteSheets.containsKey(path)) return spriteSheets.get(path);
        else {
            System.err.println("[Harmony Engine (Assets)]: Can not get spritesheet '" + path + "' without first adding it!");
            System.exit(-1);
        }

        return null;
    }

    public static File getFile(String path) {
        if(files.containsKey(path)) return files.get(path);

        File file = null;
        try {
            file = new File(Assets.class.getResource(path).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        files.put(path, file);

        return file;
    }

}
