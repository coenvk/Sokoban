package com.arman.sokoban.view.gamestate;

import com.arman.sokoban.configure.LevelReader;
import com.arman.sokoban.controller.InputHandler;
import com.arman.sokoban.controller.Stopwatch;
import com.arman.sokoban.controller.audio.AudioPlayer;
import com.arman.sokoban.model.level.LevelManager;
import com.arman.sokoban.util.GraphicsUtils;
import com.arman.sokoban.view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelSelectState extends GameState {

    private static final int COLUMNS = 8;
    private static final int ROWS = (int) Math.ceil(LevelManager.NUM_LEVELS / (float) COLUMNS);
    private static final int ROWS_PER_PAGE = 4;
    private static final int PAGES = (int) Math.ceil(ROWS / (float) ROWS_PER_PAGE);

    private int selectedLevel;
    private Color numberColor;
    private Color selectColor;
    private Color lockedColor;
    private Font numberFont;
    private Font stepsFont;
    private Font timeFont;
    private Font headFont;
    private Color headColor;
    private Font pageNumberFont;
    private Color pageNumberColor;
    private Color selectPageNumberColor;

    private Color gradientStart;
    private Color gradientEnd;

    private int selectedPage;

    public LevelSelectState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        selectedLevel = 0;
        lockedColor = Color.GRAY;
        numberColor = Color.WHITE;
        selectColor = Color.RED;
        stepsFont = new Font("Arial", Font.PLAIN, 20);
        numberFont = new Font("Arial", Font.PLAIN, 40);
        timeFont = new Font("Arial", Font.PLAIN, 14);
        headFont = new Font("Arial", Font.PLAIN, 56);
        pageNumberFont = new Font("Arial", Font.PLAIN, 28);
        pageNumberColor = Color.WHITE;
        selectPageNumberColor = Color.RED;
        headColor = Color.RED;
        gradientStart = Color.BLUE;
        gradientEnd = Color.WHITE;
        selectedPage = 0;
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
        GraphicsUtils.drawCenteredString(g, "SELECT LEVEL", headFont, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 4);
        int x, y = 0, side = 80;
        Image image = new BufferedImage((int) (COLUMNS * side + COLUMNS * side * 0.2), (int) (ROWS_PER_PAGE * side + ROWS_PER_PAGE * side * 0.2), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        boolean shouldBreak = false;
        for (int row = ROWS_PER_PAGE * selectedPage; row < ROWS_PER_PAGE * (1 + selectedPage); row++) {
            x = 0;
            for (int column = 0; column < COLUMNS; column++) {
                int i = column + row * COLUMNS;
                if (i >= LevelManager.NUM_LEVELS) {
                    shouldBreak = true;
                    break;
                }
                drawSelector(g2, x, y, side, i);
                x += side * 1.2;
            }
            if (shouldBreak) {
                break;
            }
            y += side * 1.2;
        }
        g2.dispose();
        if (image.getWidth(null) > GamePanel.WIDTH || image.getHeight(null) > GamePanel.HEIGHT) {
            image = GraphicsUtils.resizeImageWithRatio(image, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
        image = GraphicsUtils.filterImage(image, Color.BLACK);
        GraphicsUtils.drawCenteredImage(g, image, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        drawPageNumbering(g);
    }

    private void drawPageNumbering(Graphics2D g) {
        int width = 100;
        int height = 50;
        Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < PAGES; i++) {
            if (selectedPage == i) {
                g2.setColor(selectPageNumberColor);
            } else {
                g2.setColor(pageNumberColor);
            }
            GraphicsUtils.drawCenteredString(g2, String.valueOf(i + 1), pageNumberFont, i * width / PAGES, 0, width / PAGES, height);
        }
        image = GraphicsUtils.filterImage(image, Color.BLACK);
        GraphicsUtils.drawCenteredImage(g, image, 0, 3 * GamePanel.HEIGHT / 4, GamePanel.WIDTH, GamePanel.HEIGHT / 4);
    }

    private void drawSelector(Graphics2D g, int x, int y, int side, int i) {
        int rx = x;
        int ry = y;
        int rw = side;
        int rh = side;
        int ras = side / 5;
        g.setColor(new Color(1, 1, 1));
        g.fillRoundRect(rx, ry, rw, rh, ras, ras);
        if (isLocked(i)) {
            g.setColor(lockedColor);
        } else if (selectedLevel == i) {
            g.setColor(selectColor);
        } else {
            g.setColor(numberColor);
        }
        g.drawRoundRect(rx, ry, rw, rh, ras, ras);
        int number = i + 1;
        String numberString = "";
        if (number < 100 && number >= 10) {
            numberString += "0";
        } else if (number < 10) {
            numberString += "00";
        }
        numberString += String.valueOf(number);
        GraphicsUtils.drawCenteredString(g, numberString, numberFont, x, y + side / 12, side, side / 2);
        g.setColor(Color.YELLOW);
        if (isLocked(i)) {
            g.setColor(lockedColor);
        }
        boolean done = false;
        if (LevelReader.getLevelResult(i) != null) {
            done = LevelReader.getLevelResult(i).isDone();
        } else {
            LevelReader.readLevelResults();
        }
        String steps = (done) ? String.valueOf(LevelReader.getLevelResult(i).getSteps()) : "XXX";
        GraphicsUtils.drawCenteredString(g, steps, stepsFont, x, y + side / 2, side, side / 2);
        g.setColor(Color.GRAY);
        rx = x + side / 10;
        ry = y + side - side / 10;
        rw = side - side / 5;
        rh = side / 5;
        ras = side / 10;
        g.fillRoundRect(rx, ry, rw, rh, ras, ras);
        g.setColor(Color.WHITE);
        g.drawRoundRect(rx, ry, rw, rh, ras, ras);
        String time = (done) ? Stopwatch.formatToMinutes(LevelReader.getLevelResult(i).getTimeInMillis()) : "--:--:--";
        GraphicsUtils.drawCenteredString(g, time, timeFont, rx, ry, rw, rh);
    }

    @Override
    public void handleInput() {
        if (InputHandler.pressedEnter()) {
            select();
        } else if (InputHandler.pressedEscape()) {
            gameStateManager.toMenuState();
        } else if (InputHandler.pressedZ()) {
            if (selectedPage > 0) {
                selectedPage--;
                if (isOnCurrentPage(getLastUnlockedLevel())) {
                    selectedLevel = ROWS_PER_PAGE * COLUMNS * selectedPage;
                }
            }
        } else if (InputHandler.pressedX()) {
            if (selectedPage < PAGES - 1) {
                selectedPage++;
                if (isOnCurrentPage(getLastUnlockedLevel())) {
                    selectedLevel = ROWS_PER_PAGE * COLUMNS * selectedPage;
                }
            }
        } else {
            if (isOnCurrentPage()) {
                if (InputHandler.pressedUp()) {
                    if (selectedLevel == 0) {
                        return;
                    }
                    int prevSelectedLevel = selectedLevel;
                    selectedLevel -= COLUMNS;
                    if (selectedLevel < 0) {
                        selectedLevel = 0;
                    }
                    if (isLocked(selectedLevel)) {
                        selectedLevel = prevSelectedLevel;
                    }
                    if (selectedLevel != prevSelectedLevel) {
                        AudioPlayer.play("navigate.wav", 0);
                    }
                } else if (InputHandler.pressedDown()) {
                    if (selectedLevel == LevelManager.NUM_LEVELS - 1) {
                        return;
                    }
                    int prevSelectedLevel = selectedLevel;
                    selectedLevel += COLUMNS;
                    if (selectedLevel >= LevelManager.NUM_LEVELS) {
                        selectedLevel = LevelManager.NUM_LEVELS - 1;
                    }
                    if (isLocked(selectedLevel)) {
                        selectedLevel = getLastUnlockedLevel();
                    }
                    if (selectedLevel != prevSelectedLevel) {
                        AudioPlayer.play("navigate.wav", 0);
                    }
                } else if (InputHandler.pressedLeft()) {
                    if (selectedLevel > 0 && !isLocked(selectedLevel - 1)) {
                        selectedLevel--;
                        AudioPlayer.play("navigate.wav", 0);
                    }
                } else if (InputHandler.pressedRight()) {
                    if (selectedLevel < (LevelManager.NUM_LEVELS - 1) && !isLocked(selectedLevel + 1)) {
                        selectedLevel++;
                        AudioPlayer.play("navigate.wav", 0);
                    }
                }
            }
        }
    }

    private void select() {
        gameStateManager.getLevelManager().setCurrentLevel(selectedLevel);
        gameStateManager.toLevelState();
    }

    private boolean isOnCurrentPage() {
        return isOnCurrentPage(selectedLevel);
    }

    private boolean isOnCurrentPage(int levelNumber) {
        return (levelNumber >= ROWS_PER_PAGE * COLUMNS * selectedPage && levelNumber < ROWS_PER_PAGE * COLUMNS * (selectedPage + 1));
    }

    private boolean isLocked(int levelNumber) {
        if (levelNumber == 0) {
            return false;
        }
        if (LevelReader.getLevelResult(levelNumber - 1) == null) {
            LevelReader.readLevelResults();
            return true;
        }
        return !LevelReader.getLevelResult(levelNumber - 1).isDone();
    }

    private int getLastUnlockedLevel() {
        int lastUnlockedLevel = 0;
        for (int i = 0; i < LevelManager.NUM_LEVELS; i++) {
            if (i == 0) {
                continue;
            }
            if (LevelReader.getLevelResult(i - 1) == null) {
                LevelReader.readLevelResults();
                continue;
            }
            if (LevelReader.getLevelResult(i - 1).isDone()) {
                lastUnlockedLevel = i;
            }
        }
        return lastUnlockedLevel;
    }

}
