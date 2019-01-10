package com.arman.sokoban.view;

import com.arman.sokoban.configure.LevelReader;
import com.arman.sokoban.controller.InputHandler;
import com.arman.sokoban.util.GraphicsUtils;
import com.arman.sokoban.view.gamestate.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements FocusListener {

    public static final int WIDTH = 960;
    public static final int HEIGHT = 640;

    private static final float OVERLAY_STRENGTH = 0.4f;

    private GameStateManager gameStateManager;
    private boolean running;

    private boolean windowFocused;

    private Color overlayColor;

    public GamePanel() {
        this.gameStateManager = new GameStateManager(this);
        this.overlayColor = new Color(255, 255, 255, (int) (255 * OVERLAY_STRENGTH));
        windowFocused = true;
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(InputHandler.getInstance());
        addMouseMotionListener(InputHandler.getInstance());
        addMouseWheelListener(InputHandler.getInstance());
        InputHandler.setComponent(this);
        running = true;
        Thread thread = new Thread(() -> run());
        thread.start();
    }

    public void run() {
        double time;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passed;
        double delta = 0;
        double rate = 0;
        int frames = 0;
        int fps = 0;
        while (running) {
            boolean render = false;
            time = System.nanoTime() / 1000000000.0;
            passed = time - lastTime;
            lastTime = time;
            delta += passed;
            rate += passed;
            double tickSpeed = 1.0 / 60.0;
            while (delta >= tickSpeed) {
                delta -= tickSpeed;
                render = true;
                update();
                if (rate >= 1.0) {
                    rate = 0;
                    fps = frames;
                    frames = 0;
                }
            }
            if (render) {
                repaint();
                frames++;
            } else {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        draw((Graphics2D) g);
    }

    private void draw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
        gameStateManager.draw(g);
        if (!windowHasFocus()) {
            g.setColor(overlayColor);
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }
    }

    private void update() {
        gameStateManager.update();
        InputHandler.update();
    }

    public boolean windowHasFocus() {
        return windowFocused;
    }

    @Override
    public void focusGained(FocusEvent e) {
        windowFocused = true;
    }

    @Override
    public void focusLost(FocusEvent e) {
        windowFocused = false;
    }

}
