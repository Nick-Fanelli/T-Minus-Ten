package com.harmonygames.engine.physics2D.components;

import com.harmonygames.engine.event.EventSystem;
import com.harmonygames.engine.event.events.GameObjectMovementAction;
import com.harmonygames.engine.event.events.GameObjectMovementEvent;
import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.physics2D.Collision2D;
import com.harmonygames.engine.math.Vector2f;

import java.awt.*;

public class Rigidbody2D extends Component {

    private final EventSystem<GameObjectMovementEvent, GameObjectMovementAction> eventSystem = new EventSystem<>();

    private final Vector2f accumulatedForce = new Vector2f();
    private final Vector2f addForce = new Vector2f();

    private final Vector2f minForce = new Vector2f(Float.MIN_VALUE);
    private final Vector2f maxForce = new Vector2f(Float.MAX_VALUE);

    private float gravityForce = 0f;

    private boolean isColliding = false;
    private boolean isMoving = false;

    private boolean hasGravity = false;
    private float maxGravity = 3f;
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
            if(gravityForce < maxGravity) {
                this.gravityForce = Math.min(maxGravity, this.gravityForce + mass);
            }

            if(this.addForce.y < 0) this.gravityForce = 0;
        }

        this.accumulatedForce.add(addForce);
        this.accumulatedForce.y += gravityForce;

        // FIXME Make the Vector2f method work!!!
        if(!this.minForce.equals(Float.MIN_VALUE) || !this.maxForce.equals(Float.MAX_VALUE)) {
//            this.accumulatedForce.clip(this.minForce, this.maxForce);
            if(this.accumulatedForce.y < this.minForce.y) { this.accumulatedForce.y = this.minForce.y; }
            if(this.addForce.y < this.minForce.y) { this.addForce.y = this.minForce.y; }
        }

        BoxCollider2D collider = super.gameObject.getComponent(BoxCollider2D.class);

        if (!accumulatedForce.isZero()) {
            isMoving = true;
            this.eventSystem.callEvent(new GameObjectMovementAction(super.gameObject.getName(), accumulatedForce.copy()));

            if (collider != null && super.gameObject.getScene() != null && collider.isCollidable()) {
                boolean colliding = false;

                for (GameObject gameObject : super.gameObject.getScene().getUnwrappedGameObjects(BoxCollider2D.class)) {
                    if (gameObject == super.gameObject) {
                        continue;
                    }

                    // The target object's box collider
                    BoxCollider2D objectCollider = gameObject.getComponent(BoxCollider2D.class);
                    if(!objectCollider.isCollidable()) continue; // If the object's box collider can't collide, skip

                    // Check to see if the object is completely colliding
                    if(Collision2D.isColliding(new Vector2f(super.gameObject.transform.position).add(collider.getOffset()).add(accumulatedForce), collider.getScale(),
                            new Vector2f(gameObject.transform.position).add(objectCollider.getOffset()), objectCollider.getScale())) {

                        isColliding = true;
                        colliding = true;

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
                    } else if(!colliding) {
                        isColliding = false;
                    }

                }
                super.gameObject.transform.position.add(accumulatedForce); // Add the adjusted force to the transform of the game object.
                accumulatedForce.zero(); // Zero the accumulated force.
            }

            addForce.toZero(mass / 10);
        } else isMoving = false;
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

    public void setMaxGravity(float maxGravity) { this.maxGravity = maxGravity; }
    public float getMaxGravity() { return this.maxGravity; }

    public void setMass(float mass) { this.mass = mass; }
    public float getMass() { return this.mass; }

    public Vector2f getAccumulatedForce() { return this.accumulatedForce; }

    public boolean isColliding() {
        return this.isColliding;
    }
    public boolean isMoving() { return this.isMoving; }

    public Vector2f getMinForce() { return this.minForce; }
    public Vector2f getMaxForce() { return this.maxForce; }

    public void setMinForce(Vector2f minForce) { this.minForce.set(minForce); }
    public void setMaxForce(Vector2f maxForce) { this.maxForce.set(maxForce); }

    public void addMovementListener(GameObjectMovementEvent event) {
        this.eventSystem.subscribe(event);
    }

    public void removeMovementListener(GameObjectMovementEvent event) {
        this.eventSystem.unsubscribe(event);
    }
}
