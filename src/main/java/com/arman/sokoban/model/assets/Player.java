package com.arman.sokoban.model.assets;

import com.arman.sokoban.model.level.Cell;
import com.arman.sokoban.model.level.LevelConstants;

import java.awt.*;

public class Player extends Asset {

    private int steps;
    private int currentIndex;

    public Player(Cell cell) {
        super(cell);
        moveable = true;
        steps = 0;
        currentIndex = 0;
    }

    @Override
    public void move(Direction direction) {
        super.move(direction);
        switch (direction) {
            case UP:
                if (currentIndex >= 3 && currentIndex < 6) {
                    currentIndex = ++currentIndex % 6;
                    if (currentIndex < 3) {
                        currentIndex = 3;
                    }
                } else {
                    currentIndex = 4 + (int) Math.ceil(Math.random());
                }
                break;
            case DOWN:
                if (currentIndex >= 0 && currentIndex < 3) {
                    currentIndex = ++currentIndex % 3;
                } else {
                    currentIndex = 1 + (int) Math.ceil(Math.random());
                }
                break;
            case LEFT:
                if (currentIndex >= 8 && currentIndex < 10) {
                    currentIndex = ++currentIndex % 10;
                    if (currentIndex < 8) {
                        currentIndex = 8;
                    }
                } else {
                    currentIndex = 9;
                }
                break;
            case RIGHT:
                if (currentIndex >= 6 && currentIndex < 8) {
                    currentIndex = ++currentIndex % 8;
                    if (currentIndex < 6) {
                        currentIndex = 6;
                    }
                } else {
                    currentIndex = 7;
                }
                break;
            default:
                break;
        }
    }

    public void incrementSteps() {
        steps++;
    }

    @Override
    public String toString() {
        return String.valueOf(LevelConstants.PLAYER);
    }

    public int getSteps() {
        return steps;
    }

    public void decrementSteps() {
        steps--;
    }

    public Image getFrame() {
        return Spritesheet.getPlayer(currentIndex);
    }

}
