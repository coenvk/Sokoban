package com.arman.sokoban.view.gamestate;

import com.arman.sokoban.controller.InputHandler;
import com.arman.sokoban.controller.audio.AudioPlayer;
import com.arman.sokoban.util.GraphicsUtils;
import com.arman.sokoban.view.GamePanel;

import java.awt.*;

public class MenuState extends GameState {

    private static final String START = "Start";
    private static final String HELP = "Help";
    private static final String OPTIONS = "Options";
    private static final String QUIT = "Quit";


    private int selectedItem;
    private String[] items;

    private Color titleColor;
    private Font titleFont;

    private Font font;
    private Color selectColor;
    private Color color;

    private Color gradientStart;
    private Color gradientEnd;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        selectedItem = 0;
        items = new String[]{START, HELP, OPTIONS, QUIT};
        titleColor = Color.RED;
        titleFont = new Font("Arial", Font.PLAIN, 56);
        font = new Font("Arial", Font.PLAIN, 28);
        color = Color.WHITE;
        selectColor = Color.RED;
        gradientStart = Color.BLUE;
        gradientEnd = Color.WHITE;
    }

    @Override
    public void update() {
        handleInput();
    }

    private void drawTitle(Graphics2D g) {
        g.setColor(titleColor);
        GraphicsUtils.drawCenteredString(g, "S O K O B A N", titleFont, 0, 0, GamePanel.WIDTH, 2 * GamePanel.HEIGHT / 3);
    }

    private void drawMenuItem(Graphics2D g, int index) {
        if (index < 0 || index >= items.length) {
            return;
        }
        int x = 0;
        int y = 0;
        y += index * TEXT_GAP;
        if (selectedItem == index) {
            g.setColor(selectColor);
        } else {
            g.setColor(color);
        }
        GraphicsUtils.drawCenteredString(g, items[index], font, x, y, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        GradientPaint gp = new GradientPaint(GamePanel.WIDTH / 2, 0, gradientStart, GamePanel.WIDTH / 2, GamePanel.HEIGHT, gradientEnd);
        g.setPaint(gp);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        drawTitle(g);
        for (int i = 0; i < items.length; i++) {
            drawMenuItem(g, i);
        }
    }

    private void select() {
        if (items[selectedItem].equals(START)) {
            gameStateManager.toLevelSelectState();
        } else if (items[selectedItem].equals(OPTIONS)) {
            gameStateManager.toOptionsState();
        } else if (items[selectedItem].equals(HELP)) {
            // TODO: to help page
        } else if (items[selectedItem].equals(QUIT)) {
            System.exit(0);
        }
    }

    @Override
    public void handleInput() {
        if (InputHandler.pressedEnter()) {
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

}
