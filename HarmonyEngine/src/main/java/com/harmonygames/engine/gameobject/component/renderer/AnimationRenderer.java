package com.harmonygames.engine.gameobject.component.renderer;

import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.graphics.SpriteSheet;

import java.awt.*;

public class AnimationRenderer extends Component {

    private final SpriteSheet spriteSheet;
    private SpriteRenderer spriteRenderer;
    private int animationID;
    private int currentFrame;
    private boolean isDirty = false;

    public AnimationRenderer(SpriteSheet spriteSheet, int animationID) {
        this.spriteSheet = spriteSheet;
        this.animationID = animationID;
        this.currentFrame = 0;
    }

    @Override
    public void onCreate() {
        this.spriteRenderer = new SpriteRenderer(spriteSheet.getSprite(currentFrame, animationID));
        this.spriteRenderer.setGameObject(super.gameObject);
    }

    @Override
    public void update(float deltaTime) {
        if(isDirty) {
            spriteRenderer.setImage(spriteSheet.getSprite(currentFrame, animationID));
        }

        this.spriteRenderer.update(deltaTime);
    }

    @Override
    public void draw(Graphics2D g) {
        this.spriteRenderer.draw(g);
    }

    private long lastTime = 0;

    public void incrementMillis(int millis) {
        if(System.currentTimeMillis() - lastTime >= millis) {
            this.nextFrame();
            this.lastTime = System.currentTimeMillis();
        }
    }

    public void setCurrentFrame(int value) {
        this.currentFrame = value;
        this.isDirty = true;
    }

    public void nextFrame() {
        if(currentFrame >= spriteSheet.getNumCols() - 1) currentFrame = 0;
        else currentFrame++;
        this.isDirty = true;
    }

    public void setAnimation(int animationID) {
        this.animationID = animationID;
        this.isDirty = true;
    }

    public int getCurrentFrame() { return this.currentFrame; }
    public int getAnimationID() { return this.animationID; }
    public SpriteSheet getSpriteSheet() { return this.spriteSheet; }
}
