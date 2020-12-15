package com.harmonygames.tMinusTen;

import com.harmonygames.engine.math.PerlinNoise;

import java.util.Random;

public class Launcher {

    public static void main(String[] args) {

        PerlinNoise noise = new PerlinNoise();

        for(int i = 0; i < 100; i++) {
            System.out.println(Math.round(noise.noise(i) * 10));
        }

//        GameContext context = new GameContext("T Minus Ten", new PlanetScene());
//        context.start();
//
//        System.out.println(DataUtils.getSaveLocation());
    }

}
