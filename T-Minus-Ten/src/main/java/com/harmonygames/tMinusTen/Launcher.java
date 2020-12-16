package com.harmonygames.tMinusTen;

import com.harmonygames.engine.GameContext;
import com.harmonygames.tMinusTen.scenes.PlanetScene;

public class Launcher {

    public static void main(String[] args) {

        GameContext context = new GameContext("T Minus Ten", new PlanetScene());
        context.start();

    }

}
