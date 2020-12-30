package com.harmonygames.tMinusTen;

import com.harmonygames.engine.GameContext;
import com.harmonygames.tMinusTen.scenes.PlanetScene;
import com.harmonygames.tMinusTen.scenes.planets.MarsScene;

public class Launcher {

    public static void main(String[] args) {

        GameContext context = new GameContext("T Minus Ten", new MarsScene());
        context.start();

    }

}
