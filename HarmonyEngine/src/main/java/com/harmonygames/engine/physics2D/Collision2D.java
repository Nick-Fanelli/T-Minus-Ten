package com.harmonygames.engine.physics2D;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.physics2D.components.BoxCollider2D;
import com.harmonygames.engine.math.Scale;
import com.harmonygames.engine.math.Vector2f;

public class Collision2D {

    public static boolean gameObjectWithGameObject(GameObject g1, GameObject g2) {
        if(!g1.containsComponentType(BoxCollider2D.class) || !g2.containsComponentType(BoxCollider2D.class)) {
            System.err.println("[Harmony Engine (Collision2D)]: One or more of the given game objects does not contain a BoxCollider2D component");
            return false;
        }

        return boxColliderWithBoxCollider(g1.getComponent(BoxCollider2D.class), g2.getComponent(BoxCollider2D.class));
    }

    public static boolean boxColliderWithBoxCollider(BoxCollider2D b1, BoxCollider2D b2) {
        if(b1.getGameObject() == null || b2.getGameObject() == null) {
            System.err.println("[Harmony Engine (Collision2D)]: Could not find the game objects of box colliders.");
            return false;
        }

        if(!b1.isCollidable() || !b2.isCollidable()) return false;

        return b1.getGameObject().transform.position.x + b1.getOffset().x + b1.getScale().width >= b2.getGameObject().transform.position.x + b2.getOffset().x
        && b1.getGameObject().transform.position.x + b1.getOffset().x <= b2.getGameObject().transform.position.x + b2.getOffset().x + b2.getScale().width
        && b1.getGameObject().transform.position.y + b1.getOffset().y + b1.getScale().height >= b2.getGameObject().transform.position.y + b2.getOffset().y
        && b1.getGameObject().transform.position.y + b1.getOffset().y <= b2.getGameObject().transform.position.y + b2.getOffset().y + b2.getScale().height;
    }

    public static boolean forceBoxColliderWithBoxCollider(BoxCollider2D b1, BoxCollider2D b2) {
        if(b1.getGameObject() == null || b2.getGameObject() == null) {
            System.err.println("[Harmony Engine (Collision2D)]: Could not find the game objects of box colliders.");
            return false;
        }

        return b1.getGameObject().transform.position.x + b1.getOffset().x + b1.getScale().width >= b2.getGameObject().transform.position.x + b2.getOffset().x
                && b1.getGameObject().transform.position.x + b1.getOffset().x <= b2.getGameObject().transform.position.x + b2.getOffset().x + b2.getScale().width
                && b1.getGameObject().transform.position.y + b1.getOffset().y + b1.getScale().height >= b2.getGameObject().transform.position.y + b2.getOffset().y
                && b1.getGameObject().transform.position.y + b1.getOffset().y <= b2.getGameObject().transform.position.y + b2.getOffset().y + b2.getScale().height;
    }

    public static boolean isColliding(Vector2f p1, Scale s1, Vector2f p2, Scale s2) {
        return p1.x + s1.width >= p2.x
                && p1.x <= p2.x + s2.width
                && p1.y + s1.height >= p2.y
                && p1.y <= p2.y + s2.height;
    }

    public static boolean xIsColliding(float x1, float x2, float width1, float width2) {
        return x1 + width1 >= x2 && x1 <= x2 + width2;
    }

    public static boolean yIsColliding(float y1, float y2, float height1, float height2) {
        return y1 + height1 >= y2 && y1 <= y2 + height2;
    }

}
