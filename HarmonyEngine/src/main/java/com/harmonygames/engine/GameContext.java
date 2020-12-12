package com.harmonygames.engine;

import com.harmonygames.engine.display.DisplayManager;
import com.harmonygames.engine.display.Window;
import com.harmonygames.engine.scene.SceneManager;
import com.harmonygames.engine.utils.Input;

public class GameContext implements Runnable {

    public static final int TARGET_FPS = 60;
    public static final double UPDATE_CAP = 1.0 / TARGET_FPS;

    private final Thread contextThread;
    private final String gameTitle;

    private SceneManager sceneManager;
    private Input input;

    private int currentFps = 0;

    private boolean isRunning = false;

    public GameContext(String gameTitle) {
        this.contextThread = new Thread(this, "__HarmonyEngine:GameContext__");
        this.gameTitle = gameTitle;

        this.setSystemProperties();
    }

    private void setSystemProperties() {
        System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
    }

    public void start() {
        if(isRunning || this.contextThread.isAlive()) return;
        this.contextThread.start();
    }

    private void initialize() {
        sceneManager = new SceneManager();

        DisplayManager.createDisplay(this, gameTitle);

        input = new Input();
    }

    @Override
    public void run() {
        this.initialize();
        this.isRunning = true;

        boolean shouldDraw;

        double currentUpdateTime;
        double lastUpdateTime = 0;
        double deltaTime = 1;

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
        sceneManager.update(deltaTime);
        this.input.update(); // Must happen last
    }

    private synchronized void draw() {
        sceneManager.draw(Window.getContext().getDrawGraphics());
    }

    public void stop() {
        this.isRunning = false;

        sceneManager.destroy();

        DisplayManager.closeDisplay();
        System.exit(0); // Close the java program
    }

    public boolean isRunning() { return this.isRunning; }
    public int getCurrentFps() { return this.currentFps; }
}
