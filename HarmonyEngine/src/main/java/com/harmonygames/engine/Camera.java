package com.harmonygames.engine;

import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;

public class Camera {

    public static final Vector2f position = new Vector2f();

    public static boolean shouldDrawTransform(Transform transform) {
        return transform.position.x + transform.scale.width >= Camera.position.x
                && transform.position.x <= Camera.position.x + Display.getResolution().getWidth()
                && transform.position.y + transform.scale.height >= Camera.position.y
                && transform.position.y < Camera.position.y + Display.getResolution().getHeight();
    }

    public static boolean shouldDraw(float x, float y, int width, int height) {
        return x + width >= Camera.position.x
                && x <= Camera.position.x + Display.getResolution().getWidth()
                && y + height >= Camera.position.y
                && y <= Camera.position.y + Display.getResolution().getHeight();
    }

}
