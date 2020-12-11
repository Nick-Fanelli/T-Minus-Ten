package com.harmonygames.engine.display;

import com.harmonygames.engine.GameContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {

    private static Window context;

    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;

    private GameContext gameContext;

    public static void init(GameContext gameContext, String title) {
        if(context == null) {
            context = new Window(gameContext, title);
        }
    }

    public static Window getContext() { return context; }

    private Window(GameContext gameContext, String title) {
        if(context != null) return;
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
        frame.setFocusable(true);
        frame.setResizable(true);

        frame.getContentPane().setIgnoreRepaint(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();

        this.catchClose();

        frame.setVisible(true);
        frame.requestFocus();
    }

    private void catchClose() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameContext.stop();
            }
        });
    }

    public void update() {
        try {
            g.drawImage(image, 0, 0, frame.getWidth(), frame.getHeight(), null);
            bs.show();
        } catch (Exception e) {
            System.err.println("BLEH IN THE WINDOW CLASS");
        }
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
}
