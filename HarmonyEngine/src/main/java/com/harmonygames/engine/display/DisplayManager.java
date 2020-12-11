package com.harmonygames.engine.display;

import com.harmonygames.engine.GameContext;

public class DisplayManager {

    public static void createDisplay(GameContext gameContext, String title) {
        Window.init(gameContext, title);
    }

    public static void updateDisplay() {
        Window context = Window.getContext();
        if(context != null) context.update();
    }

    public static void closeDisplay() {
        Window context = Window.getContext();
        if(context != null) context.close();
    }
}
