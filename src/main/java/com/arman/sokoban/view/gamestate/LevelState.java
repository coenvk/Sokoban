package com.arman.sokoban.view.gamestate;

import com.arman.sokoban.configure.LevelReader;
import com.arman.sokoban.configure.LevelResult;
import com.arman.sokoban.configure.LevelWriter;
import com.arman.sokoban.controller.InputHandler;
import com.arman.sokoban.controller.Stopwatch;
import com.arman.sokoban.controller.audio.AudioPlayer;
import com.arman.sokoban.model.assets.Asset;
import com.arman.sokoban.model.level.Level;
import com.arman.sokoban.util.FileUtils;
import com.arman.sokoban.util.GraphicsUtils;
import com.arman.sokoban.view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class LevelState extends GameState {

    private static final int BOTTOM_OFFSET = 50;
    private static final int TOP_OFFSET = 50;

    private Level level;
    private Font headFont;
    private Color headColor;
    private Font font;
    private Color color;
    private Color hudColor;

    private Image background;

    public LevelState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        level = gameStateManager.getLevelManager().getCurrentLevel();
        font = new Font("Arial", Font.PLAIN, 28);
        color = Color.WHITE;
        headColor = Color.WHITE;
        headFont = new Font("Arial", Font.PLAIN, 36);
        hudColor = new Color(0, 0, 0, (int) (255 * 0.3));
        gameStateManager.getLevelManager().setFullMode();
        try {
            background = ImageIO.read(FileUtils.getStream("levelbackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stopwatch.start();
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        int ratio = background.getWidth(null) / background.getHeight(null);
        g.drawImage(background, 0, 0, GamePanel.WIDTH * ratio, GamePanel.HEIGHT * ratio, null);
        gameStateManager.getLevelManager().drawLevel(g);
        g.setColor(hudColor);
        g.fillRect(0, GamePanel.HEIGHT - BOTTOM_OFFSET, GamePanel.WIDTH, BOTTOM_OFFSET);
        g.fillRect(0, 0, GamePanel.WIDTH, TOP_OFFSET);
        g.setColor(headColor);
        synchronized (level) {
            GraphicsUtils.drawCenteredString(g, "Level " + (level.getNumber() + 1), headFont, 0, 0, GamePanel.WIDTH, TOP_OFFSET);
            g.setColor(color);
            GraphicsUtils.drawCenteredString(g, "Steps: " + level.getPlayer().getSteps(), font, 0, GamePanel.HEIGHT - BOTTOM_OFFSET, GamePanel.WIDTH / 3, BOTTOM_OFFSET);
            GraphicsUtils.drawCenteredString(g, "Time: " + Stopwatch.formatToMinutes(), font, GamePanel.WIDTH / 3, GamePanel.HEIGHT - BOTTOM_OFFSET, GamePanel.WIDTH / 3, BOTTOM_OFFSET);
            GraphicsUtils.drawCenteredString(g, "Crates Left: " + level.getCratesLeft(), font, 2 * GamePanel.WIDTH / 3, GamePanel.HEIGHT - BOTTOM_OFFSET, GamePanel.WIDTH / 3, BOTTOM_OFFSET);
            if (level.isCompleted()) {
                Stopwatch.pause();
                saveBestTime();
                AudioPlayer.play("completed.wav", 0);
                gameStateManager.toCompleteState();
            } else if (!gameStateManager.getGamePanel().windowHasFocus()) {
                gameStateManager.pause();
            }
        }
    }

    private void saveBestTime() {
        int levelNumber = level.getNumber();
        int steps = level.getPlayer().getSteps();
        long millis = Stopwatch.elapsedMillis();
        LevelResult curLevelResult = LevelReader.getLevelResult(levelNumber);
        if (curLevelResult.isDone() && curLevelResult.getTimeInMillis() < millis) {
            return;
        }
        if (curLevelResult.getTimeInMillis() == millis) {
            if (steps > curLevelResult.getSteps()) {
                return;
            }
        }
        LevelResult newLevelResult = new LevelResult(levelNumber, true, steps, millis);
        LevelWriter.write(newLevelResult);
    }

    @Override
    public void handleInput() {
        if (InputHandler.pressedEscape()) {
            gameStateManager.pause();
        } else if (InputHandler.pressedUp()) {
            level.movePlayer(Asset.Direction.UP);
        } else if (InputHandler.pressedDown()) {
            level.movePlayer(Asset.Direction.DOWN);
        } else if (InputHandler.pressedLeft()) {
            level.movePlayer(Asset.Direction.LEFT);
        } else if (InputHandler.pressedRight()) {
            level.movePlayer(Asset.Direction.RIGHT);
        } else if (InputHandler.pressedU()) {
            gameStateManager.getLevelManager().undoLastMove();
        } else if (InputHandler.pressedR()) {
            gameStateManager.getLevelManager().reset();
            gameStateManager.toLevelState();
        } else if (InputHandler.pressedZ()) {
            gameStateManager.getLevelManager().toggleZoom();
        } else if (InputHandler.holdShift()) {
            gameStateManager.getLevelManager().setShowUnderlyingObjects(true);
        } else if (InputHandler.releasedShift()) {
            gameStateManager.getLevelManager().setShowUnderlyingObjects(false);
        }
    }

}
