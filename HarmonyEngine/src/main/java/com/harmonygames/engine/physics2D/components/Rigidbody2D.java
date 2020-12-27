package com.harmonygames.engine.physics2D.components;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.SimilarObjectContainer;
import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.graphics.RenderBatch;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.physics2D.Collision2D;
import com.harmonygames.engine.math.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Rigidbody2D extends Component {

    private final Vector2f accumulatedForce = new Vector2f();
    private final Vector2f addForce = new Vector2f();
    private float gravity = 8f;
    private boolean isColliding = false;

    private boolean hasGravity = false;
    private float mass = 1.0f;

    public Rigidbody2D() { }

    public Rigidbody2D(float mass) {
        this.mass = mass;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void update(float deltaTime) {
        if (this.hasGravity) {
            this.accumulatedForce.y += mass + (mass / 10);
            if(this.accumulatedForce.y > gravity) this.accumulatedForce.y = gravity;
        }


        BoxCollider2D collider = super.gameObject.getComponent(BoxCollider2D.class);

        if (!accumulatedForce.isZero()) {

            if (collider != null && super.gameObject.getScene() != null) {
                for (GameObject gameObject : super.gameObject.getScene().getUnwrappedGameObjects(BoxCollider2D.class)) {
                    if (gameObject == super.gameObject) {
                        continue;
                    }

                    // The target object's box collider
                    BoxCollider2D objectCollider = gameObject.getComponent(BoxCollider2D.class);

                    // Check to see if the object is completely colliding
                    if(Collision2D.isColliding(new Vector2f(super.gameObject.transform.position).add(collider.getOffset()).add(accumulatedForce), collider.getScale(),
                            new Vector2f(gameObject.transform.position).add(objectCollider.getOffset()), objectCollider.getScale())) {

                        // Check for X collisions and scale and adjust if necessary
                        while (Collision2D.xIsColliding(super.gameObject.transform.position.x + collider.getOffset().x + accumulatedForce.x,
                                gameObject.transform.position.x + objectCollider.getOffset().x, collider.getScale().width, objectCollider.getScale().width) &&
                                Collision2D.yIsColliding(super.gameObject.transform.position.y + collider.getOffset().y, gameObject.transform.position.y,
                                        collider.getScale().height, objectCollider.getScale().height) && accumulatedForce.x != 0) {
                            accumulatedForce.toZero(1, 0); // 1 = one pixel
                        }

                        // Check for Y collisions and scale and adjust if necessary
                        while (Collision2D.yIsColliding(super.gameObject.transform.position.y + collider.getOffset().y + accumulatedForce.y,
                                gameObject.transform.position.y + objectCollider.getOffset().y, collider.getScale().height, objectCollider.getScale().height) &&
                                Collision2D.xIsColliding(super.gameObject.transform.position.x + collider.getOffset().x, gameObject.transform.position.x,
                                        collider.getScale().width, objectCollider.getScale().width) && accumulatedForce.y != 0) {
                            accumulatedForce.toZero(0, 1); // 1 = one pixel
                        }
                    }

                }
                super.gameObject.transform.position.add(accumulatedForce); // Add the adjusted force to the transform of the game object.
                accumulatedForce.toZero(mass); // Slow down the force depending on the mass of the object.
            }
        }

        if (collider != null && super.gameObject.getScene() != null) {
            for (GameObject gameObject : super.gameObject.getScene().getUnwrappedGameObjects(BoxCollider2D.class)) {
                if (gameObject == super.gameObject) {
                    continue;
                }

                // The target object's box collider
                BoxCollider2D objectCollider = gameObject.getComponent(BoxCollider2D.class);

                // Check to see if the object is completely colliding
                if (Collision2D.isColliding(new Vector2f(super.gameObject.transform.position).add(collider.getOffset()).add(accumulatedForce), collider.getScale(),
                        new Vector2f(gameObject.transform.position).add(objectCollider.getOffset()), objectCollider.getScale())) {
                    isColliding = true;
                } else isColliding = false;
            }

        }
    }

    @Override
    public void draw(Graphics2D g) {

    }

    public void addForce(Vector2f force) {
        this.addForce.add(force);
    }

    public void setForce(Vector2f force) {
        this.accumulatedForce.set(force);
    }

    public void setForceToNonzero(Vector2f force) {
        this.accumulatedForce.set(force.x != 0 ? force.x : this.accumulatedForce.x, force.y != 0 ? force.y : this.accumulatedForce.y);
    }

    public void setHasGravity(boolean value) { this.hasGravity = value; }
    public boolean hasGravity() { return this.hasGravity; }

    public void setGravity(float gravity) { this.gravity = gravity; }
    public float getGravity() { return this.gravity; }

    public void setMass(float mass) { this.mass = mass; }
    public float getMass() { return this.mass; }

    public Vector2f getAccumulatedForce() { return this.accumulatedForce; }

    public boolean isColliding() {
        return isColliding;
    }
}
