package com.arman.sokoban.view.gamestate;

import java.awt.*;

public abstract class GameState {

    protected static final int TEXT_GAP = 40;

    protected GameStateManager gameStateManager;

    public GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        init();
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw(Graphics2D g);

    public abstract void handleInput();

}
