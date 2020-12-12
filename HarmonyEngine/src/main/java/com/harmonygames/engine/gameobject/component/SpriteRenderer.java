package com.harmonygames.engine.gameobject.component;

import com.harmonygames.engine.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteRenderer extends Component {

    public BufferedImage image;

    public SpriteRenderer(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void draw(Graphics2D g) {
        if(Camera.shouldDraw(gameObject.transform.position.x, gameObject.transform.position.y, image.getWidth(), image.getHeight())) {
            g.drawImage(image, (int) (gameObject.transform.position.x + Camera.position.x),
                    (int) (gameObject.transform.position.y + Camera.position.y), null);
        }
    }

    @Override public void update(float deltaTime) {}
}
