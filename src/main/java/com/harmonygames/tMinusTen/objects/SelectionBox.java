package com.harmonygames.tMinusTen.objects;

import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.Box;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;

import java.awt.*;

public class SelectionBox extends Box {

    public SelectionBox(Scale scale) {
        super("Selection Box", new Transform(new Vector2f(), scale), Color.BLACK, Color.BLACK, Type.STROKED);
        super.setStatic(true);
        super.setZIndex(1);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        this.transform.position.set(Input.getMousePosition());
    }

}
