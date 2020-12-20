package com.harmonygames.engine.gameobject;

import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SimilarObjectContainer<T extends GameObject> extends GameObject {

    private final ArrayList<T> gameObjects = new ArrayList<>();
    private final Scene scene;

    public SimilarObjectContainer(String name, Scene scene) {
        super(name);
        this.scene = scene;
    }

    public void addGameObject(T gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(T gameObject) {
        gameObjects.remove(gameObject);
    }

    public ArrayList<T> getGameObjects() { return this.gameObjects; }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(int i = 0; i < gameObjects.size(); i++) { if(gameObjects.get(i).isEnabled()) gameObjects.get(i).update(deltaTime); }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        for(int i = 0; i < gameObjects.size(); i++) {
            if(gameObjects.get(i).isEnabled()) {
                gameObjects.get(i).draw(g);
            }
        }
    }

    @Override
    public <E extends Component> GameObject[] getSelf(Class<E> component) {
        ArrayList<GameObject> selectedGameObjects = new ArrayList<>();

        for(int i = 0; i < gameObjects.size(); i++) {
            selectedGameObjects.addAll(Arrays.asList(gameObjects.get(i).getSelf(component)));
        }

        return selectedGameObjects.toArray(new GameObject[0]);
    }
}
