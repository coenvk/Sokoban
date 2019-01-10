package com.arman.sokoban.view.gamestate;

import com.arman.sokoban.configure.Configurations;
import com.arman.sokoban.configure.LevelReader;
import com.arman.sokoban.configure.LevelWriter;
import com.arman.sokoban.controller.InputHandler;
import com.arman.sokoban.controller.audio.AudioPlayer;
import com.arman.sokoban.util.GraphicsUtils;
import com.arman.sokoban.view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OptionsState extends GameState {

    private static final String SAVE = "Save";
    private static final String ALLOW_UNDO = "Allow Undo";
    private static final String ENABLE_SOUND = "Enable Sound";
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String RESET = "Reset Game";

    private String[] items;
    private String[] choices;

    private int selectedItem;
    private int selectedUndoChoice;
    private int selectedSoundChoice;

    private Font headFont;
    private Color headColor;

    private Font font;
    private Color color;
    private Color selectColor;

    private Color gradientStart;
    private Color gradientEnd;

    public OptionsState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        items = new String[]{ALLOW_UNDO, ENABLE_SOUND, SAVE, RESET};
        choices = new String[]{YES, NO};
        selectedItem = 0;
        if (Configurations.allowUndo) {
            selectedUndoChoice = 0;
        } else {
            selectedUndoChoice = 1;
        }
        if (Configurations.enableSound) {
            selectedSoundChoice = 0;
        } else {
            selectedSoundChoice = 1;
        }
        headFont = new Font("Arial", Font.PLAIN, 56);
        headColor = Color.RED;
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

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        GradientPaint gp = new GradientPaint(GamePanel.WIDTH / 2, 0, gradientStart, GamePanel.WIDTH / 2, GamePanel.HEIGHT, gradientEnd);
        g.setPaint(gp);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(headColor);
        GraphicsUtils.drawCenteredString(g, "OPTIONS", headFont, 0, 0, GamePanel.WIDTH, 2 * GamePanel.HEIGHT / 3);
        int x = 0, y = 0;
        for (int i = 0; i < items.length; i++) {
            x = 0;
            if (selectedItem == i) {
                g.setColor(selectColor);
            } else {
                g.setColor(color);
            }
            if (items[i].equals(ENABLE_SOUND)) {
                drawPicksString(g, x, y, ENABLE_SOUND, selectedSoundChoice, choices);
            } else if (items[i].equals(ALLOW_UNDO)) {
                drawPicksString(g, x, y, ALLOW_UNDO, selectedUndoChoice, choices);
            } else {
                if (items[i].equals(SAVE)) {
                    y += TEXT_GAP;
                }
                GraphicsUtils.drawCenteredString(g, items[i], font, x, y, GamePanel.WIDTH, GamePanel.HEIGHT);
            }
            y += TEXT_GAP;
        }
    }

    private void drawPicksString(Graphics2D g, int x, int y, String head, int selectedIndex, String[] picks) {
        String newHead = head + ": ";
        String all = newHead;
        String tab = "    ";
        for (int i = 0; i < picks.length; i++) {
            all += picks[i];
            if (i < picks.length - 1) {
                all += tab;
            }
        }
        int width = g.getFontMetrics(font).stringWidth(all);
        int height = 50;
        Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setFont(font);
        if (items[selectedItem].equals(head)) {
            g2.setColor(selectColor);
        } else {
            g2.setColor(color);
        }
        g2.drawString(newHead, 0, height / 2);
        FontMetrics fontMetrics = g2.getFontMetrics(font);
        int offset = fontMetrics.stringWidth(newHead);
        for (int i = 0; i < picks.length; i++) {
            if (selectedIndex == i) {
                g2.setColor(selectColor);
            } else {
                g2.setColor(color);
            }
            g2.drawString(picks[i], offset, height / 2);
            offset += fontMetrics.stringWidth(picks[i] + tab);
        }
        g2.dispose();
        image = GraphicsUtils.filterImage(image, Color.BLACK);
        GraphicsUtils.drawCenteredImage(g, image, x, y, GamePanel.WIDTH, GamePanel.HEIGHT);
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
        } else if (InputHandler.pressedEscape()) {
            gameStateManager.toPreviousState();
        } else {
            handleAllowUndoInput();
            handleEnableSoundInput();
        }
    }

    private void handleAllowUndoInput() {
        if (InputHandler.pressedLeft()) {
            if (items[selectedItem].equals(ALLOW_UNDO)) {
                if (selectedUndoChoice > 0) {
                    selectedUndoChoice--;
                    AudioPlayer.play("navigate.wav", 0);
                }
            }
        } else if (InputHandler.pressedRight()) {
            if (items[selectedItem].equals(ALLOW_UNDO)) {
                if (selectedUndoChoice < choices.length - 1) {
                    selectedUndoChoice++;
                    AudioPlayer.play("navigate.wav", 0);
                }
            }
        }
    }

    private void handleEnableSoundInput() {
        if (InputHandler.pressedLeft()) {
            if (items[selectedItem].equals(ENABLE_SOUND)) {
                if (selectedSoundChoice > 0) {
                    selectedSoundChoice--;
                    AudioPlayer.play("navigate.wav", 0);
                }
            }
        } else if (InputHandler.pressedRight()) {
            if (items[selectedItem].equals(ENABLE_SOUND)) {
                if (selectedSoundChoice < choices.length - 1) {
                    selectedSoundChoice++;
                    AudioPlayer.play("navigate.wav", 0);
                }
            }
        }
    }

    private void save() {
        if (choices[selectedUndoChoice].equals(YES)) {
            Configurations.allowUndo = true;
        } else {
            Configurations.allowUndo = false;
        }
        if (choices[selectedSoundChoice].equals(YES)) {
            Configurations.enableSound = true;
        } else {
            Configurations.enableSound = false;
        }
        Configurations.writePropValues();
    }

    private void select() {
        if (items[selectedItem].equals(SAVE)) {
            save();
            gameStateManager.toPreviousState();
        } else if (items[selectedItem].equals(RESET)) {
            LevelWriter.renameLevels();
            LevelReader.readLevelResults();
            gameStateManager.toMenuState();
        }
    }

}
