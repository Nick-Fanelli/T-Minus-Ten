package com.harmonygames.engine;

import com.harmonygames.engine.display.DisplayManager;

public class GameContext implements Runnable {

    public static final int TARGET_FPS = 60;
    public static final double UPDATE_CAP = 1.0 / TARGET_FPS;

    private final Thread contextThread;
    private final String gameTitle;

    private int currentFps = 0;

    private boolean isRunning = false;

    public GameContext(String gameTitle) {
        this.contextThread = new Thread(this, "__HarmonyEngine:GameContext__");
        this.gameTitle = gameTitle;
    }

    public void startGame() { this.contextThread.start(); }

    private void initialize() {
        DisplayManager.createDisplay(gameTitle);
    }

    @Override
    public void run() {
        this.initialize();
        this.isRunning = true;

        boolean shouldDraw;

        double currentUpdateTime;
        double lastUpdateTime = 0;
        double deltaTime;

        double firstTime, passedTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double updateTime = 0;

        while(isRunning) {

            shouldDraw = false;

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            updateTime += passedTime;

            while(updateTime >= GameContext.UPDATE_CAP) {
                updateTime -= GameContext.UPDATE_CAP;
                shouldDraw = true;

                // Figure the delta time
                currentUpdateTime = System.nanoTime() / 1000000000.0;
                deltaTime = currentUpdateTime - lastUpdateTime;
                lastUpdateTime = currentUpdateTime;

                this.update((float) deltaTime);

                currentFps = (int) (1.0 / deltaTime);
            }

            if(shouldDraw) {
                this.draw();
                DisplayManager.updateDisplay();
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private synchronized void update(float deltaTime) {

    }

    private synchronized void draw() {

    }

    public boolean isRunning() { return this.isRunning; }
    public int getCurrentFps() { return this.currentFps; }g
}
