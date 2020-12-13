package com.harmonygames.engine.physics2D;

import com.harmonygames.engine.gameobject.GameObject;
import com.harmonygames.engine.gameobject.component.BoxCollider2D;

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

        return b1.getGameObject().transform.position.x + b1.getOffset().x + b1.getScale().width >= b2.getGameObject().transform.position.x + b2.getOffset().x
        && b1.getGameObject().transform.position.x + b1.getOffset().x <= b2.getGameObject().transform.position.x + b2.getOffset().x + b2.getScale().width
        && b1.getGameObject().transform.position.y + b1.getOffset().y + b1.getScale().height >= b2.getGameObject().transform.position.y + b2.getOffset().y
        && b1.getGameObject().transform.position.y + b1.getOffset().y <= b2.getGameObject().transform.position.y + b2.getOffset().y + b2.getScale().height;
    }

}
