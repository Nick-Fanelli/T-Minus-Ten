package com.harmonygames.engine.display;

import com.harmonygames.engine.GameContext;
import com.harmonygames.engine.utils.ImageUtils;

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

    private static Dimension aspectRatio;
    private static Dimension scaledContext;

    private final GameContext gameContext;

    public Display(GameContext gameContext, String title) {
        this.gameContext = gameContext;

        aspectRatio = new Dimension(1280, 720);
        scaledContext = new Dimension(aspectRatio.width, aspectRatio.height);

        Dimension windowSize = new Dimension(scaledContext.width, scaledContext.height);

        image = new BufferedImage(windowSize.width, windowSize.height, BufferedImage.TYPE_INT_ARGB);

        canvas = new Canvas();
        canvas.setPreferredSize(windowSize);

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.setMinimumSize(new Dimension(scaledContext.width / 2, scaledContext.height / 2));

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

                scaledContext = getScaledDisplayContext(canvas.getSize());

                canvas.createBufferStrategy(2);
                bs = canvas.getBufferStrategy();
                g = bs.getDrawGraphics();
            }
        });
    }

    public void update() {
        try {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            g.drawImage(image, (canvas.getWidth() - scaledContext.width) / 2, (canvas.getHeight() - scaledContext.height) / 2,
                    scaledContext.width, scaledContext.height, null);
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

    public Dimension getScaledDisplayContext(Dimension frameSize) {
        Dimension scaledDimension = new Dimension();

        if(frameSize.getWidth() > frameSize.getHeight()) {
            scaledDimension.height = (int) frameSize.getHeight();
            scaledDimension.width = (int) ((aspectRatio.getWidth() / aspectRatio.getHeight()) * frameSize.getHeight());
        } else {
            scaledDimension.width = (int) frameSize.getWidth();
            scaledDimension.height = (int) ((aspectRatio.getHeight() / aspectRatio.getWidth()) * frameSize.getWidth());
        }

        return scaledDimension;
    }

    public Graphics2D getDrawGraphics() {
        return (Graphics2D) image.getGraphics();
    }

    public static JFrame getFrame() { return frame; }
    public static Canvas getCanvas() { return canvas; }
}
