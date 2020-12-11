package com.harmonygames.engine.scene;

import java.awt.*;

public abstract class Scene {

    public void onCreate() { }
    public void update(float deltaTime) { }
    public void draw(Graphics2D g) { }
    public void onClose() { }

}
