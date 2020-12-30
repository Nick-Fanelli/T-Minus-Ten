package com.harmonygames.tMinusTen.objects;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.Box;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.engine.physics2D.components.Rigidbody2D;
import com.studiohartman.jamepad.ControllerAxis;

import java.awt.*;

public class SelectionBox extends Box {

    public static final float MOVEMENT_MULTIPLIER = 2.5f;

    private final Vector2f lastMousePos = new Vector2f();
    private final Vector2f absPosition = new Vector2f(Display.getAspectRatio().width / 2f, Display.getAspectRatio().height / 2f);

    private final int xMax, yMax;

    public SelectionBox(Scale scale) {
        super("Selection Box", new Transform(new Vector2f(), scale), Color.BLACK, Color.BLACK, Type.STROKED);
        super.setStatic(true);
        super.setZIndex(1);

        this.xMax = Display.getAspectRatio().width - this.transform.scale.width;
        this.yMax = Display.getAspectRatio().height - this.transform.scale.height;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        boolean controllerUpdated = false;

        if(Input.isControllerConnected(Player.TARGET_CONTROLLER_ID)) {

            double rightX = Math.round(Input.getControllerAxis(ControllerAxis.RIGHTX, Player.TARGET_CONTROLLER_ID) * 10D) / 10D;
            double rightY = Math.round(Input.getControllerAxis(ControllerAxis.RIGHTY, Player.TARGET_CONTROLLER_ID) * 10D) / 10D;

            if(rightX != 0 || rightY != 0) {
                this.absPosition.add((float) rightX * MOVEMENT_MULTIPLIER, (float) -rightY * MOVEMENT_MULTIPLIER);
                this.updatePosition();
                controllerUpdated = true;
            }
        }

        if(!controllerUpdated && !this.lastMousePos.equals(Input.getMousePosition())) {
            this.absPosition.set(Input.getMousePosition().sub(this.transform.scale.width / 2f, this.transform.scale.height / 2f));
        }

        this.lastMousePos.set(Input.getMousePosition());
        this.updatePosition();
    }

    public void updatePosition() {
        this.absPosition.clip(0, xMax, 0, yMax);

        Vector2f cameraDifference = new Vector2f(Camera.position).
                sub(new Vector2f(Camera.position).
                        div(this.transform.scale.width, this.transform.scale.height).
                        round().mul(this.transform.scale.width, this.transform.scale.height));

        this.transform.position.set(Math.round(this.absPosition.x / this.transform.scale.width) * this.transform.scale.width,
                Math.round(this.absPosition.y / this.transform.scale.height) * this.transform.scale.height).
                sub(cameraDifference);
    }

}
