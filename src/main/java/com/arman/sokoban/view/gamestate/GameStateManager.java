package com.arman.sokoban.view.gamestate;

import com.arman.sokoban.controller.Stopwatch;
import com.arman.sokoban.model.level.LevelManager;
import com.arman.sokoban.view.GamePanel;

import java.awt.*;
import java.util.Stack;

public class GameStateManager {

    public static final int MENU_STATE = 0;
    public static final int LEVEL_SELECT_STATE = 1;
    public static final int LEVEL_STATE = 2;
    public static final int COMPLETE_STATE = 3;
    public static final int OPTIONS_STATE = 4;
    private static final int NUM_GAME_STATES = 16;

    private GameState[] gameStates;
    private int currentState;
    private LevelManager levelManager;
    private GamePanel gamePanel;

    private Stack<Integer> stateStack;

    private PauseState pauseState;
    private boolean paused;

    public GameStateManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gameStates = new GameState[NUM_GAME_STATES];
        levelManager = new LevelManager();
        pauseState = new PauseState(this);
        paused = false;
        currentState = MENU_STATE;
        stateStack = new Stack<>();
        loadState(currentState);
        stateStack.push(currentState);
    }

    private void loadState(int state) {
        if (state == MENU_STATE) {
            gameStates[state] = new MenuState(this);
        } else if (state == LEVEL_SELECT_STATE) {
            gameStates[state] = new LevelSelectState(this);
        } else if (state == LEVEL_STATE) {
            gameStates[state] = new LevelState(this);
        } else if (state == COMPLETE_STATE) {
            gameStates[state] = new CompleteState(this);
        } else if (state == OPTIONS_STATE) {
            gameStates[state] = new OptionsState(this);
        }
    }

    private void removeState(int state) {
        gameStates[state] = null;
    }

    private void setState(int state) {
        removeState(currentState);
        currentState = state;
        loadState(currentState);
        stateStack.push(currentState);
    }

    public void toPreviousState() {
        levelManager.reset();
        stateStack.pop();
        int state = stateStack.peek();
        setState(state);
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void toMenuState() {
        setState(MENU_STATE);
    }

    public void toLevelSelectState() {
        setState(LEVEL_SELECT_STATE);
    }

    public void toLevelState() {
        setState(LEVEL_STATE);
    }

    public void toCompleteState() {
        setState(COMPLETE_STATE);
    }

    public void toOptionsState() {
        setState(OPTIONS_STATE);
    }

    private void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void pause() {
        setPaused(true);
        Stopwatch.pause();
    }

    public void unpause() {
        setPaused(false);
        pauseState = new PauseState(this);
        Stopwatch.resume();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void update() {
        if (paused) {
            pauseState.update();
        } else if (getCurrentGameState() != null) {
            getCurrentGameState().update();
        }
    }

    public GameState getCurrentGameState() {
        return gameStates[currentState];
    }

    public void draw(Graphics2D g) {
        if (paused) {
            pauseState.draw(g);
        } else if (gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
    }

}
