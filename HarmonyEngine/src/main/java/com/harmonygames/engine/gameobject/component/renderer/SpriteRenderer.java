package com.harmonygames.engine.gameobject.component.renderer;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteRenderer extends Component {

    private BufferedImage image;
    private BufferedImage adjustedImage;
    private float currentRotation;
    private boolean drawToObjectSize = false;

    public SpriteRenderer(BufferedImage image) {
        this.image = image;
        this.adjustedImage = image;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void draw(Graphics2D g) {
        if(currentRotation != super.gameObject.getRotation()) {
            this.setAdjustedImage();
            currentRotation = super.gameObject.getRotation();
        }

        if(Camera.shouldDraw(gameObject.transform.position.x, gameObject.transform.position.y, image.getWidth(), image.getHeight())) {
            if(!this.drawToObjectSize) {
                g.drawImage(adjustedImage, (int) (gameObject.transform.position.x - super.gameObject.getCameraOffset().x),
                        (int) (gameObject.transform.position.y - super.gameObject.getCameraOffset().y), null);
            } else {
                g.drawImage(adjustedImage, (int) (gameObject.transform.position.x - super.gameObject.getCameraOffset().x),
                        (int) (gameObject.transform.position.y - super.gameObject.getCameraOffset().y), super.gameObject.transform.scale.width,
                        super.gameObject.transform.scale.height, null);
            }
        }
    }

    @Override public void update(float deltaTime) {}

    public void setImage(BufferedImage image) {
        this.image = image;
        this.setAdjustedImage();
    }

    public void setAdjustedImage() {
        if(super.gameObject.getRotation() == 0) this.adjustedImage = image;
        else this.adjustedImage = ImageUtils.rotateImageByDegrees(image, super.gameObject.getRotation());
    }

    public boolean isDrawToObjectSize() { return this.drawToObjectSize; }
    public void setDrawToObjectSize(boolean value) { this.drawToObjectSize = value; }
}
