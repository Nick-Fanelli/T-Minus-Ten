package com.harmonygames.engine.display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {

    private static Window context;

    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;

    public static void init(String title) {
        if(context == null) {
            context = new Window(title);
        }
    }

    public static Window getContext() { return context; }

    private Window(String title) {
        if(context != null) return;

        Dimension windowSize = new Dimension(1280, 720);

        image = new BufferedImage(windowSize.width, windowSize.height, BufferedImage.TYPE_INT_ARGB);

        canvas = new Canvas();
        canvas.setPreferredSize(windowSize);

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        frame.setVisible(true);
        frame.requestFocus();
    }

    public void update() {
        try {
            g.drawImage(image, 0, 0, frame.getWidth(), frame.getHeight(), null);
            bs.show();
        } catch (Exception e) {
            System.err.println("BLEH IN THE WINDOW CLASS");
        }
    }

    public Graphics2D getDrawGraphics() {
        return (Graphics2D) image.getGraphics();
    }
}
