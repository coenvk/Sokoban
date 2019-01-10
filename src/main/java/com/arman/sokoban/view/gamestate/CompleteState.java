package com.arman.sokoban.view.gamestate;

import com.arman.sokoban.controller.InputHandler;
import com.arman.sokoban.controller.Stopwatch;
import com.arman.sokoban.controller.audio.AudioPlayer;
import com.arman.sokoban.model.level.LevelManager;
import com.arman.sokoban.util.GraphicsUtils;
import com.arman.sokoban.view.GamePanel;

import java.awt.*;

public class CompleteState extends GameState {

    private static final String MAIN_MENU = "Main Menu";
    private static final String LEVEL_SELECT = "Select Level";
    private static final String RESTART = "Restart";
    private static final String NEXT_LEVEL = "Next Level";

    private Font headFont;
    private Font font;
    private Color headColor;
    private Color color;
    private Color selectColor;

    private String[] items;
    private int selectedItem;

    private Color gradientStart;
    private Color gradientEnd;

    private double currentTimeInMillis;
    private double currentSteps;
    private long timeInMillis;
    private long steps;

    public CompleteState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        headFont = new Font("Arial", Font.PLAIN, 56);
        font = new Font("Arial", Font.PLAIN, 28);
        headColor = Color.RED;
        color = Color.WHITE;
        selectColor = Color.RED;
        if (gameStateManager.getLevelManager().getCurrentLevel().getNumber() < LevelManager.NUM_LEVELS - 1) {
            items = new String[]{MAIN_MENU, LEVEL_SELECT, RESTART, NEXT_LEVEL};
        } else {
            items = new String[]{MAIN_MENU, LEVEL_SELECT, RESTART};
        }
        selectedItem = 0;
        gradientStart = Color.BLUE;
        gradientEnd = Color.WHITE;
        timeInMillis = Stopwatch.elapsedMillis();
        steps = gameStateManager.getLevelManager().getCurrentLevel().getPlayer().getSteps();
        currentSteps = 0D;
        currentTimeInMillis = 0D;
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        GradientPaint gp = new GradientPaint(GamePanel.WIDTH / 2, 0, gradientStart, GamePanel.WIDTH / 2, GamePanel.HEIGHT, gradientEnd);
        g.setPaint(gp);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(headColor);
        GraphicsUtils.drawCenteredString(g, "Completed Level " + (gameStateManager.getLevelManager().getCurrentLevel().getNumber() + 1), headFont, 0, 0, GamePanel.WIDTH, 2 * GamePanel.HEIGHT / 3);
        GraphicsUtils.drawCenteredString(g, "Steps: " + ((int) currentSteps) + "    Time: " + Stopwatch.formatToMinutes((long) currentTimeInMillis), font, 0, 0, GamePanel.WIDTH, 4 * GamePanel.HEIGHT / 5);
        for (int i = 0; i < items.length; i++) {
            int x = 0;
            int y = i * TEXT_GAP;
            if (selectedItem == i) {
                g.setColor(selectColor);
            } else {
                g.setColor(color);
            }
            GraphicsUtils.drawCenteredString(g, items[i], font, x, y, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
        startCountUpThread();
    }

    private void startCountUpThread() {
        Thread thread = new Thread(() -> {
            while (currentTimeInMillis < timeInMillis || currentSteps < steps) {
                float duration = 3000f;
                if (currentTimeInMillis < timeInMillis) {
                    currentTimeInMillis += timeInMillis / duration;
                }
                if (currentSteps < steps) {
                    currentSteps += steps / duration;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void handleInput() {
        if (InputHandler.pressedUp()) {
            selectedItem--;
            selectedItem = (selectedItem < 0) ? items.length - 1 : selectedItem;
            AudioPlayer.play("navigate.wav", 0);
        } else if (InputHandler.pressedDown()) {
            selectedItem = ++selectedItem % items.length;
            AudioPlayer.play("navigate.wav", 0);
        } else if (InputHandler.pressedEnter()) {
            select();
        }
    }

    private void select() {
        if (items[selectedItem].equals(MAIN_MENU)) {
            gameStateManager.toMenuState();
        } else if (items[selectedItem].equals(LEVEL_SELECT)) {
            gameStateManager.toLevelSelectState();
        } else if (items[selectedItem].equals(RESTART)) {
            gameStateManager.getLevelManager().reset();
            gameStateManager.toLevelState();
        } else if (items[selectedItem].equals(NEXT_LEVEL)) {
            gameStateManager.getLevelManager().nextLevel();
            gameStateManager.toLevelState();
        }
    }

}
