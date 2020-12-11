package com.harmonygames.engine.display;

public class DisplayManager {

    public static void createDisplay(String title) {
        Window.init(title);
    }

    public static void updateDisplay() {
        Window context = Window.getContext();
        if(context != null) context.update();
    }

    public static void closeDisplay() {

    }
}
