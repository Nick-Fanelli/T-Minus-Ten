package com.harmonygames.engine.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Assets {

    private static final HashMap<String, BufferedImage> images = new HashMap<>();

    public static BufferedImage getImage(String path) {
        if(images.containsKey(path)) return images.get(path);

        try {
            BufferedImage image = ImageIO.read(new File(path));
            images.put(path, image);
            return image;
        } catch (Exception e) {
            System.err.println("[Harmony Engine (Assets)]: Can not find image at: " + path);
            System.exit(-1);
        }

        return null;
    }

}
