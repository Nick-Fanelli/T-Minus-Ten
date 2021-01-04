package com.harmonygames.tMinusTen.scenes.planets;

import com.harmonygames.engine.utils.Assets;
import com.harmonygames.tMinusTen.scenes.PlanetScene;

public class MarsScene extends PlanetScene {

    public MarsScene() {
        super("Planet Mars", Assets.addSpriteSheet("/spritesheets/planet/mars/mars-tileset.png", 512, 512));
    }

}
