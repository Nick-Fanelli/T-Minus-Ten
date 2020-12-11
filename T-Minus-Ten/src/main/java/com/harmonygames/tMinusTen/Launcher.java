package com.harmonygames.tMinusTen;

import com.harmonygames.engine.GameContext;
import com.harmonygames.engine.scene.SceneManager;
import com.harmonygames.tMinusTen.scenes.GameScene;

public class Launcher {

    public static void main(String[] args) {
        GameContext context = new GameContext("T Minus Ten");

        SceneManager.setActiveScene(new GameScene());

        context.start();
    }

}
