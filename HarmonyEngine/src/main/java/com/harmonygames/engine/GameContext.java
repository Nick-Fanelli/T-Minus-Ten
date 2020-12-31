package com.harmonygames.engine;

import com.harmonygames.engine.data.DataUtils;
import com.harmonygames.engine.display.Display;
import com.harmonygames.engine.input.Input;
import com.harmonygames.engine.scene.Scene;
import com.harmonygames.engine.scene.SceneManager;

public class GameContext implements Runnable {

    public static final int FPS_CAP = 120;
    public static final double UPDATE_CAP = 1.0 / FPS_CAP;

    private final Thread contextThread;
    private final String gameTitle;

    private Display display;
    private SceneManager sceneManager;
    private Input input;

    private int currentFps = 0;

    private boolean isRunning = false;

    private Scene startScene;

    public GameContext(String gameTitle, Scene activeScene) {
        DataUtils.setSaveLocation(gameTitle.toLowerCase().replaceAll(" +", "-"));
        this.contextThread = new Thread(this, "__HarmonyEngine:GameContext__");
        this.gameTitle = gameTitle;
        this.startScene = activeScene;

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
        display = new Display(this, gameTitle);

        sceneManager = new SceneManager();

        input = new Input(Display.getFrame(), Display.getCanvas());

        SceneManager.setActiveScene(startScene);
    }

    @Override
    public void run() {
        this.initialize();
        this.isRunning = true;

        boolean shouldDraw;

        double currentUpdateTime;
        double lastUpdateTime = System.nanoTime() / 1000000000.0;
        double deltaTime = 0;

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
                display.update();
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

        this.input.update();
    }

    private synchronized void draw() {
        sceneManager.draw(display.getDrawGraphics());
    }

    public void stop() {
        this.isRunning = false;

        sceneManager.destroy();

        display.close();
        System.exit(0); // Close the java program
    }

    public boolean isRunning() { return this.isRunning; }
    public int getCurrentFps() { return this.currentFps; }
}
