package com.arman.sokoban.model.level;

import com.arman.sokoban.model.assets.*;

import static com.arman.sokoban.model.level.LevelConstants.*;

public class Cell {

    private Asset object;
    private Asset underlyingObject;
    private int row;
    private int column;

    public Cell(int row, int column) {
        this(row, column, null, null);
    }

    public Cell(int row, int column, Asset object, Asset underlyingObject) {
        this.row = row;
        this.column = column;
        this.object = object;
        this.underlyingObject = underlyingObject;
    }

    public Cell(int row, int column, int type) {
        this.row = row;
        this.column = column;
        switch (type) {
            case WALL:
                this.object = new Wall(this);
                this.underlyingObject = new Floor(this);
                break;
            case AREA:
                this.object = null;
                this.underlyingObject = new Area(this);
                break;
            case PLAYER:
                this.object = new Player(this);
                this.underlyingObject = new Floor(this);
                break;
            case CRATE:
                this.object = new Crate(this);
                this.underlyingObject = new Floor(this);
                break;
            case FLOOR:
                this.object = null;
                this.underlyingObject = new Floor(this);
                break;
            case CRATE_AREA:
                this.object = new Crate(this);
                this.underlyingObject = new Area(this);
                break;
            case PLAYER_AREA:
                this.object = new Player(this);
                this.underlyingObject = new Area(this);
                break;
            default:
                this.object = null;
                this.underlyingObject = null;
                break;
        }
    }

    public void release() {
        this.object = null;
    }

    public Asset getObject() {
        return object;
    }

    public void setObject(Asset object) {
        this.object = object;
    }

    public boolean isOccupied() {
        return object != null;
    }

    public boolean hasUnderlyingObject() {
        return underlyingObject != null;
    }

    public boolean isFloor() {
        return underlyingObject.isFloor();
    }

    public boolean isWall() {
        return object.isWall();
    }

    public boolean isPlayer() {
        return object.isPlayer();
    }

    public boolean isCrate() {
        return object.isCrate();
    }

    public boolean isArea() {
        return underlyingObject.isArea();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        if (!isOccupied()) {
            if (hasUnderlyingObject()) {
                return underlyingObject.toString();
            }
            return String.valueOf(LevelConstants.NONE);
        }
        return object.toString();
    }

    public Asset getUnderlyingObject() {
        return underlyingObject;
    }

}
