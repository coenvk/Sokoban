package com.arman.sokoban.view.gamestate;

import com.arman.sokoban.controller.InputHandler;
import com.arman.sokoban.controller.Stopwatch;
import com.arman.sokoban.controller.audio.AudioPlayer;
import com.arman.sokoban.util.GraphicsUtils;
import com.arman.sokoban.view.GamePanel;

import java.awt.*;

public class PauseState extends GameState {

    private static final String MAIN_MENU = "Main Menu";
    private static final String LEVEL_SELECT = "Select Level";
    private static final String HELP = "Help";
    private static final String OPTIONS = "Options";

    private Font headFont;
    private Color headColor;
    private Font font;
    private String[] items;
    private int selectedItem;
    private Color color;
    private Color selectColor;

    private Color gradientStart;
    private Color gradientEnd;

    public PauseState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        headFont = new Font("Arial", Font.PLAIN, 56);
        font = new Font("Arial", Font.PLAIN, 28);
        selectedItem = 0;
        items = new String[]{MAIN_MENU, LEVEL_SELECT, HELP, OPTIONS};
        color = Color.WHITE;
        selectColor = Color.RED;
        headColor = Color.RED;
        gradientStart = Color.BLUE;
        gradientEnd = Color.WHITE;
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        drawMain(g);
    }

    private void drawMain(Graphics2D g) {
        GradientPaint gp = new GradientPaint(GamePanel.WIDTH / 2, 0, gradientStart, GamePanel.WIDTH / 2, GamePanel.HEIGHT, gradientEnd);
        g.setPaint(gp);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(headColor);
        GraphicsUtils.drawCenteredString(g, "PAUSED", headFont, 0, 0, GamePanel.WIDTH, 2 * GamePanel.HEIGHT / 3);
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
    }

    @Override
    public void handleInput() {
        if (InputHandler.pressedEscape()) {
            gameStateManager.unpause();
        } else if (InputHandler.pressedEnter()) {
            select();
        } else if (InputHandler.pressedUp()) {
            selectedItem--;
            selectedItem = (selectedItem < 0) ? items.length - 1 : selectedItem;
            AudioPlayer.play("navigate.wav", 0);
        } else if (InputHandler.pressedDown()) {
            selectedItem = ++selectedItem % items.length;
            AudioPlayer.play("navigate.wav", 0);
        }
    }

    private void select() {
        Stopwatch.stop();
        gameStateManager.unpause();
        if (items[selectedItem].equals(MAIN_MENU)) {
            gameStateManager.toMenuState();
        } else if (items[selectedItem].equals(LEVEL_SELECT)) {
            gameStateManager.toLevelSelectState();
        } else if (items[selectedItem].equals(HELP)) {
            // TODO
        } else if (items[selectedItem].equals(OPTIONS)) {
            gameStateManager.toOptionsState();
        }
    }

}
