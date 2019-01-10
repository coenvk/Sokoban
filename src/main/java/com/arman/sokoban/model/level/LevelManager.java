package com.arman.sokoban.model.level;

import com.arman.sokoban.configure.Configurations;
import com.arman.sokoban.configure.Props;
import com.arman.sokoban.model.assets.Asset;
import com.arman.sokoban.model.assets.Player;
import com.arman.sokoban.model.assets.Spritesheet;
import com.arman.sokoban.util.FileUtils;
import com.arman.sokoban.util.GraphicsUtils;
import com.arman.sokoban.view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {

    public static final int NUM_LEVELS = Props.NUM_LEVELS;
    private static final int CELL_SIDE = GamePanel.WIDTH / 20;
    private static final int CELL_OFFSET = 5;
    private Level[] levels;
    private int currentLevel;

    private boolean showUnderlyingObjects;
    private Mode mode;

    private Image area, floor, wall, player, crate;

    public LevelManager() {
        this.levels = new Level[NUM_LEVELS];
        currentLevel = 0;
        mode = Mode.FULL;
        showUnderlyingObjects = false;
    }

    public Level getCurrentLevel() {
        synchronized (levels) {
            return levels[currentLevel];
        }
    }

    public void setCurrentLevel(int level) {
        synchronized (levels) {
            removeLevel(currentLevel);
            this.currentLevel = level;
            loadLevel(currentLevel);
            randomizeImages();
        }
    }

    public void undoLastMove() {
        if (Configurations.allowUndo) {
            getCurrentLevel().undoLastMove();
        }
    }

    private void randomizeImages() {
        area = Spritesheet.getRandomArea();
        floor = Spritesheet.getRandomFloor();
        wall = Spritesheet.getRandomWall();
        crate = Spritesheet.getRandomCrate();
        if (getCurrentLevel() == null) {
            player = Spritesheet.getPlayer(0);
        } else {
            player = getCurrentLevel().getPlayer().getFrame();
        }
    }

    private synchronized void drawFullLevel(Graphics2D g) {
        Level level = getCurrentLevel();
        Image image = new BufferedImage(CELL_SIDE * level.getColumns(), CELL_SIDE * level.getRows(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int x = 0, y = 0;
        for (int row = 0; row < level.getRows(); row++) {
            for (int column = 0; column < level.getColumns(); column++) {
                Cell cell = level.getCell(row, column);
                drawCell(g2, cell, x, y);
                x += CELL_SIDE;
            }
            x = 0;
            y += CELL_SIDE;
        }
        g2.dispose();
        if (image.getWidth(null) > GamePanel.WIDTH || image.getHeight(null) > GamePanel.HEIGHT) {
            image = GraphicsUtils.resizeImageWithRatio(image, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
        image = GraphicsUtils.filterImage(image, Color.BLACK);
        GraphicsUtils.drawCenteredImage(g, image, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    private synchronized void drawZoomedLevel(Graphics2D g) {
        Level level = getCurrentLevel();
        Image image = new BufferedImage(CELL_SIDE * (CELL_OFFSET * 2 + 1), CELL_SIDE * (CELL_OFFSET * 2 + 1), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int x = 0, y = 0;
        Player player = level.getPlayer();
        Cell playerCell = level.getCell(player.getRow(), player.getColumn());
        for (int row = playerCell.getRow() - CELL_OFFSET; row <= playerCell.getRow() + CELL_OFFSET; row++) {
            for (int column = playerCell.getColumn() - CELL_OFFSET; column <= playerCell.getColumn() + CELL_OFFSET; column++) {
                if (level.isCell(row, column)) {
                    Cell cell = level.getCell(row, column);
                    drawCell(g2, cell, x, y);
                }
                x += CELL_SIDE;
            }
            x = 0;
            y += CELL_SIDE;
        }
        g2.dispose();
        if (image.getWidth(null) > GamePanel.WIDTH || image.getHeight(null) > GamePanel.HEIGHT) {
            image = GraphicsUtils.resizeImageWithRatio(image, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
        image = GraphicsUtils.filterImage(image, Color.BLACK);
        GraphicsUtils.drawCenteredImage(g, image, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    private synchronized void drawCell(Graphics2D g, Cell cell, int x, int y) {
        if (cell.hasUnderlyingObject()) {
            if (cell.isArea()) {
                g.drawImage(floor, x, y, CELL_SIDE, CELL_SIDE, null);
            }
            g.drawImage(getImage(cell.getUnderlyingObject()), x, y, CELL_SIDE, CELL_SIDE, null);
        }
        if (cell.isOccupied()) {
            if (!showUnderlyingObjects || cell.isWall()) {
                g.drawImage(getImage(cell.getObject()), x, y, CELL_SIDE, CELL_SIDE, null);
            }
        }
    }

    public synchronized void drawLevel(Graphics2D g) {
        if (getCurrentLevel() == null) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
            return;
        } else {
            player = getCurrentLevel().getPlayer().getFrame();
        }
        if (isFullMode()) {
            drawFullLevel(g);
        } else if (isZoomedMode()) {
            drawZoomedLevel(g);
        }
    }

    private Image getImage(Asset asset) {
        if (asset.isCrate()) {
            return crate;
        } else if (asset.isFloor()) {
            return floor;
        } else if (asset.isPlayer()) {
            return player;
        } else if (asset.isWall()) {
            return wall;
        } else if (asset.isArea()) {
            return area;
        }
        return null;
    }

    public void reset() {
        setCurrentLevel(currentLevel);
    }

    private void loadLevel(int level) {
        levels[level] = LevelLoader.loadLevel(level, FileUtils.getStream("levels/" + (level + 1)));
    }

    public void nextLevel() {
        setCurrentLevel(getCurrentLevel().getNumber() + 1);
    }

    private void removeLevel(int level) {
        levels[level] = null;
    }

    public boolean hasNextLevel() {
        return currentLevel < (levels.length - 1);
    }

    public boolean isShowUnderlyingObjects() {
        return showUnderlyingObjects;
    }

    public void setShowUnderlyingObjects(boolean showUnderlyingObjects) {
        this.showUnderlyingObjects = showUnderlyingObjects;
    }

    public void setFullMode() {
        this.mode = Mode.FULL;
    }

    public void setZoomedMode() {
        this.mode = Mode.ZOOMED;
    }

    public boolean isFullMode() {
        return mode.isFull();
    }

    public boolean isZoomedMode() {
        return mode.isZoomed();
    }

    public void toggleZoom() {
        if (isFullMode()) {
            setZoomedMode();
        } else {
            setFullMode();
        }
    }

    public enum Mode {

        FULL(), ZOOMED();

        Mode() {

        }

        public boolean isFull() {
            return this == FULL;
        }

        public boolean isZoomed() {
            return this == ZOOMED;
        }

    }

}
