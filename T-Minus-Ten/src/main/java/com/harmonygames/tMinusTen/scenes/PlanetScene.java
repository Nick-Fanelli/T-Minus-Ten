package com.harmonygames.tMinusTen.scenes;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.physics2D.components.BoxCollider2D;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.tMinusTen.objects.Player;

public class PlanetScene extends Scene {

    public PlanetScene() { super("Planet Scene"); }

    private Player player;

    @Override
    public void onCreate() {
        super.onCreate();

        this.player = new Player();
        super.addGameObject(player);

        GameObject gameObject = new GameObject("Wall Object", new Transform(new Vector2f(0, 100), new Scale(200, 20)));
        gameObject.addComponent(new BoxCollider2D(new Vector2f(), gameObject.transform.scale));

        super.addGameObject(gameObject);
    }

}
