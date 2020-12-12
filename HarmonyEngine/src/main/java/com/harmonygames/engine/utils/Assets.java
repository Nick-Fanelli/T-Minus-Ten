package com.harmonygames.engine.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Assets {

    private static final HashMap<String, BufferedImage> images = new HashMap<>();

    public static BufferedImage getImage(String path) {
        if(images.containsKey(path)) return images.get(path);

        try {
            BufferedImage image = ImageIO.read(Assets.class.getResourceAsStream(path));
            images.put(path, image);
            return image;
        } catch (IOException e) {
            System.err.println("[Harmony Engine (Assets)]: Can not find image at: " + path);
        }

        return null;
    }

}
