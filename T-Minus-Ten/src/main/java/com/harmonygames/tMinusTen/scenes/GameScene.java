package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Input;

import java.awt.event.KeyEvent;

public class GameScene extends Scene {

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Input.isKey(KeyEvent.VK_SPACE)) System.out.println("HEY");
    }
}
