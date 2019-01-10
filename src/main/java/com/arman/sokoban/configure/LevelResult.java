package com.arman.sokoban.configure;

public class LevelResult {

    private int levelNumber;
    private boolean done;
    private long steps;
    private long timeInMillis;

    public LevelResult(int levelNumber, boolean done, long steps, long timeInMillis) {
        this.levelNumber = levelNumber;
        this.done = done;
        this.steps = steps;
        this.timeInMillis = timeInMillis;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public long getSteps() {
        return steps;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public boolean isDone() {
        return done;
    }

}