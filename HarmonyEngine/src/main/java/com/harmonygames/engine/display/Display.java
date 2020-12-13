package com.harmonygames.engine.display;

import com.harmonygames.engine.GameContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Display {

    private static JFrame frame;
    private static BufferedImage image;
    private static Canvas canvas;
    private static BufferStrategy bs;
    private static Graphics g;

    private final GameContext gameContext;

    public Display(GameContext gameContext, String title) {
        this.gameContext = gameContext;

        Dimension windowSize = new Dimension(1280, 720);

        image = new BufferedImage(windowSize.width, windowSize.height, BufferedImage.TYPE_INT_ARGB);

        canvas = new Canvas();
        canvas.setPreferredSize(windowSize);

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        frame.getContentPane().setIgnoreRepaint(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();

        this.catchClose();
        this.handleResize();

        frame.setVisible(true);
    }

    private void catchClose() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameContext.stop();
            }
        });
    }

    private void handleResize() {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(frame.getWidth() <= 0 || frame.getHeight() <= 0) return;

                canvas.createBufferStrategy(2);
                bs = canvas.getBufferStrategy();
                g = bs.getDrawGraphics();
            }
        });
    }

    public void update() {
        try {
            g.drawImage(image, 0, 0, frame.getWidth(), frame.getHeight(), null);
            bs.show();
        } catch (Exception ignored) {}

        // Clear the screen
        getDrawGraphics().fillRect(0, 0, frame.getWidth(), frame.getHeight());

        frame.requestFocus();
    }

    public void close() {
        frame.setVisible(false);
        image.flush();
        g.dispose();
        bs.dispose();
        frame.dispose();
    }

    public Graphics2D getDrawGraphics() {
        return (Graphics2D) image.getGraphics();
    }

    public static JFrame getFrame() { return frame; }
    public static Canvas getCanvas() { return canvas; }
}
