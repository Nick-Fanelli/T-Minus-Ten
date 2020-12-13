package com.harmonygames.engine.gameobject;

import com.harmonygames.engine.gameobject.component.Component;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.utils.Transform;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameObject {

    private final String name;
    public Transform transform;

    private float rotation = 0.0f;
    private Scene scene = null;

    private final ArrayList<Component> components = new ArrayList<>();

    public GameObject(String name) {
        this(name, new Transform());
    }

    public GameObject(String name, Transform transform) {
        this.name = name;
        this.transform = transform;
    }

    public void addComponent(Component component) {
        component.setGameObject(this);
        this.components.add(component);
    }

    public <T extends Component> boolean containsComponentType(Class<T> componentClass) {
        return this.getComponent(componentClass) != null;
    }

    public void removeComponent(Component component) { components.remove(component); }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for(int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if(componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for(Component c : components) {
            if(componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return null;
    }

    public float getRotation() { return this.rotation; }
    public void setRotation(float rotation) { this.rotation = rotation; }

    public ArrayList<Component> getComponents() { return this.components; }

    public String getName() { return this.name; }

    public void onCreate() { }

    public void update(float deltaTime) {
        for(Component c : components) c.update(deltaTime);
    }

    public void draw(Graphics2D g) {
        for(Component c : components) c.draw(g);
    }

    public void onDestroy() { }

    public Scene getScene() { return this.scene; }
    public void setScene(Scene scene) { this.scene = scene; }

}
