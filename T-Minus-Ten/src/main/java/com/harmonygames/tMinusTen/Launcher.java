package com.harmonygames.tMinusTen;

import com.harmonygames.engine.GameContext;
import com.harmonygames.engine.data.DataUtils;
import com.harmonygames.tMinusTen.scenes.GameScene;

public class Launcher {

    public static void main(String[] args) {
        GameContext context = new GameContext("T Minus Ten", new GameScene());
        context.start();

        System.out.println(DataUtils.getSaveLocation());
    }

}
