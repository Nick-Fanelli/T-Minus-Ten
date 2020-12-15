package com.harmonygames.engine.physics2D.components;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.physics2D.Collision2D;
import com.harmonygames.engine.math.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Rigidbody2D extends Component {

    private Vector2f accumulatedForce = new Vector2f();
    private Vector2f gravity = new Vector2f(0, 9.81f);

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
        if(hasGravity) { this.addForce(new Vector2f(this.gravity).min(new Vector2f(this.gravity).mul(mass).sub(this.accumulatedForce))); }

        if(!accumulatedForce.isZero()) {
            BoxCollider2D collider = super.gameObject.getComponent(BoxCollider2D.class);
            if(collider != null && super.gameObject.getScene() != null && super.gameObject.getRenderBatch() != null) {
                ArrayList<GameObject> sceneObjects = super.gameObject.getRenderBatch().getGameObjects();

                for(GameObject gameObject : sceneObjects) {
                    if(!gameObject.containsComponentType(BoxCollider2D.class) || gameObject == super.gameObject) continue;

                    BoxCollider2D objectCollider = gameObject.getComponent(BoxCollider2D.class);

                    while(Collision2D.isColliding(
                            new Vector2f(super.gameObject.transform.position).add(collider.getOffset()).add(accumulatedForce),
                            collider.getScale(),
                            new Vector2f(gameObject.transform.position).add(objectCollider.getOffset()),
                            objectCollider.getScale()
                    )) {
                        // TODO: Check to see where the object is to toZero at x and y to make collision smoother
                        accumulatedForce.toZero(1);
                    }
                }

            }
            super.gameObject.transform.position.add(accumulatedForce);

            accumulatedForce.toZero(mass);
        }
    }

    @Override
    public void draw(Graphics2D g) {

    }

    public void addForce(Vector2f force) {
        this.accumulatedForce.add(force);
    }

    public void setForce(Vector2f force) {
        this.accumulatedForce.set(force);
    }

    public void setForceToNonzero(Vector2f force) {
        this.accumulatedForce.set(force.x != 0 ? force.x : this.accumulatedForce.x, force.y != 0 ? force.y : this.accumulatedForce.y);
    }

    public void setHasGravity(boolean value) { this.hasGravity = value; }
    public boolean hasGravity() { return this.hasGravity; }

    public void setGravity(Vector2f gravity) { this.gravity = gravity; }
    public Vector2f getGravity() { return this.gravity; }

    public void setMass(float mass) { this.mass = mass; }
    public float getMass() { return this.mass; }
}
