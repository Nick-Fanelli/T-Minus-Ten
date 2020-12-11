package com.harmonygames.engine.scene;

import java.awt.*;

public class SceneManager {

    private static Scene activeScene;

    public static void setActiveScene(Scene scene) {
        if(activeScene != null) {
            activeScene.onClose();
            activeScene = null;
        }

        scene.onCreate();
        activeScene = scene;
    }

    public void update(float deltaTime) {
        if(activeScene != null) activeScene.update(deltaTime);
    }

    public void draw(Graphics2D g) {
        if(activeScene != null) activeScene.draw(g);
    }

    public static Scene getActiveScene() { return activeScene; }
}
