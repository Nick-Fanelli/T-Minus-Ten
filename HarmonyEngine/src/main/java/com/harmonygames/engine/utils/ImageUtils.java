package com.harmonygames.engine.utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageUtils {

    public static BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public static BufferedImage rotateImageByDegrees(BufferedImage originalImage, float degrees) {
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        int width  = originalImage.getWidth();
        int height = originalImage.getHeight();
        int newWidth  = (int) Math.floor(width  * cos + height * sin);
        int newHeight = (int) Math.floor(height * cos + width  * sin);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate((newWidth - width) / 2f, (newHeight - height) / 2f);

        int x = width  / 2;
        int y = height / 2;

        affineTransform.rotate(radians, x, y);
        g2d.setTransform(affineTransform);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

}
