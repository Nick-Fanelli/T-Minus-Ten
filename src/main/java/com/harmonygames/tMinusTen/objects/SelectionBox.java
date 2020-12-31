package com.harmonygames.tMinusTen.objects;

import com.harmonygames.engine.Camera;
import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.display.Input;
import com.harmonygames.engine.gameobject.Box;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Transform;
import com.harmonygames.engine.math.Vector2f;
import com.harmonygames.engine.physics2D.Collision2D;
import com.harmonygames.engine.physics2D.components.BoxCollider2D;
import com.harmonygames.tMinusTen.chunk.Chunk;
import com.harmonygames.tMinusTen.scenes.PlanetScene;
import com.studiohartman.jamepad.ControllerAxis;

import java.awt.*;

public class SelectionBox extends Box {

    public static final float MOVEMENT_MULTIPLIER = 2.5f;

    private final PlanetScene planetScene;
    private final BoxCollider2D boxCollider2D;

    private final Vector2f lastMousePos = new Vector2f();
    private final Vector2f absPosition = new Vector2f(Display.getResolution().width / 2f, Display.getResolution().height / 2f);
    private final Vector2f lastAbsPosition = new Vector2f();

    private final int xMax, yMax;

    private Block selectedBlock = null;
    private Chunk selectedChunk = null;

    public SelectionBox(PlanetScene scene, Scale scale) {
        super("Selection Box", new Transform(new Vector2f(), scale), Color.BLACK, Color.BLACK, Type.STROKED);
        super.setStatic(true);
        super.setZIndex(1);

        boxCollider2D = new BoxCollider2D(new Vector2f(), this.transform.scale);
        boxCollider2D.setGameObject(this);
        this.planetScene = scene;

        this.xMax = Display.getResolution().width - this.transform.scale.width;
        this.yMax = Display.getResolution().height - this.transform.scale.height;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        this.handleMovementInput();
        this.handleSelectionInput();
    }

    private void handleMovementInput() {
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

    private enum ChangeType {
        NONE, DELETE, ADD
    }

    private void handleSelectionInput() {
        ChangeType type = ChangeType.NONE;

        if(Input.isMouseButton(1)) type = ChangeType.DELETE;
        else if(Input.isMouseButton(3)) type = ChangeType.ADD;

        if(type == ChangeType.NONE && Input.isControllerConnected(Player.TARGET_CONTROLLER_ID)) {
            if(Input.getControllerAxis(ControllerAxis.TRIGGERLEFT, Player.TARGET_CONTROLLER_ID) != 0) type = ChangeType.DELETE;
            else if(Input.getControllerAxis(ControllerAxis.TRIGGERRIGHT, Player.TARGET_CONTROLLER_ID) != 0) type = ChangeType.ADD;
        }

        if(type != ChangeType.NONE) {
//            if(!this.absPosition.equals(this.lastAbsPosition)) {
                // Determine which chunk it's in
            boolean blockSelected = false;

            for(int i = 0; i < planetScene.getChunkContainer().getGameObjects().size(); i++) {
                    Chunk chunk = planetScene.getChunkContainer().getGameObjects().get(i);
                    if(chunk == null) {
                        System.err.println("Skipping because chunk is null");
                        continue;
                    }

                    BoxCollider2D chunkCollider = chunk.getComponent(BoxCollider2D.class);
                    if(chunkCollider == null) {
                        System.err.println("[T Minus Ten (SelectionBox)]: Couldn't find the chunk collider for the chunk");
                        continue;
                    }


                    if(Collision2D.isColliding(this.transform.position, this.transform.scale, chunk.transform.position.copy().sub(Camera.position),
                            chunk.transform.scale)) {
                        for(Block block : chunk.blocks) {
                            if(Collision2D.isColliding(this.transform.position, this.transform.scale, block.transform.position.copy().sub(Camera.position),
                                    block.transform.scale)) {
                                blockSelected = true;
                                selectedBlock = block;
                            }
                        }
                    }


                }

            if(!blockSelected) selectedBlock = null;

//            }


            System.out.println(selectedBlock);
        }

        this.lastAbsPosition.set(absPosition);
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
