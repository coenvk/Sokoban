package com.arman.sokoban.model.assets;

import com.arman.sokoban.model.level.Cell;

public abstract class Asset {

    protected int row;
    protected int column;
    protected boolean moveable;

    public Asset(Cell cell) {
        this.row = cell.getRow();
        this.column = cell.getColumn();
        this.moveable = false;
    }

    public boolean collidesLeft(Asset object) {
        return (column - 1) == object.getColumn() && row == object.getRow();
    }

    public boolean collidesRight(Asset object) {
        return (column + 1) == object.getColumn() && row == object.getRow();
    }

    public boolean collidesBottom(Asset object) {
        return column == object.getColumn() && row + 1 == object.getRow();
    }

    public boolean collidesTop(Asset object) {
        return column == object.getColumn() && row - 1 == object.getRow();
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public boolean isPlayer() {
        return this instanceof Player;
    }

    public boolean isWall() {
        return this instanceof Wall;
    }

    public boolean isFloor() {
        return this instanceof Floor;
    }

    public boolean isCrate() {
        return this instanceof Crate;
    }

    public boolean isArea() {
        return this instanceof Area;
    }

    public void move(Direction direction) {
        if (moveable) {
            switch (direction) {
                case UP:
                    this.row--;
                    break;
                case DOWN:
                    this.row++;
                    break;
                case LEFT:
                    this.column--;
                    break;
                case RIGHT:
                    this.column++;
                    break;
                default:
                    break;
            }
        }
    }

    public enum Direction {

        UP(), DOWN(), LEFT(), RIGHT();

        Direction() {

        }

        public Direction opposite() {
            switch (this) {
                case UP:
                    return DOWN;
                case DOWN:
                    return UP;
                case LEFT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
                default:
                    return null;
            }
        }

    }

}
