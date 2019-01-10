package com.arman.sokoban.model.level;

import com.arman.sokoban.configure.LevelResult;
import com.arman.sokoban.model.assets.Asset;
import com.arman.sokoban.model.assets.Player;

import java.util.Stack;

public class Level {

    private int number;
    private int rows;
    private int columns;
    private Cell[] cells;
    private Stack<Move> moveStack;
    private LevelResult levelResult;
    private Player player;

    public Level(int number, int rows, int columns, Cell[] cells) {
        this.number = number;
        this.rows = rows;
        this.columns = columns;
        this.cells = cells;
        this.moveStack = new Stack<>();
        this.levelResult = null;
        initializePlayer();
    }

    public Level(int number, int rows, int columns, Asset... objects) {
        this.number = number;
        this.cells = new Cell[objects.length];
        this.rows = rows;
        this.columns = columns;
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell(getRow(i), getColumn(i));
            cells[i].setObject(objects[i]);
        }
        this.moveStack = new Stack<>();
        this.levelResult = null;
        initializePlayer();
    }

    private void initializePlayer() {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].isOccupied()) {
                if (cells[i].isPlayer()) {
                    player = (Player) cells[i].getObject();
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setLevelResult(LevelResult levelResult) {
        this.levelResult = levelResult;
    }

    public boolean hasLevelResult() {
        return levelResult != null;
    }

    public LevelResult getLevelResult() {
        return levelResult;
    }

    public int getNumber() {
        return number;
    }

    public Cell[] getCells() {
        return cells;
    }

    public Cell getCell(int index) {
        return cells[index];
    }

    public Cell getCell(int row, int column) {
        return getCell(getIndex(row, column));
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getIndeces() {
        return rows * columns;
    }

    public int getRow(int index) {
        return index / columns;
    }

    public int getColumn(int index) {
        return index % columns;
    }

    public int getIndex(int row, int column) {
        return column + row * columns;
    }

    public boolean isCompleted() {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].hasUnderlyingObject() && cells[i].isArea() && !(cells[i].isOccupied() && cells[i].isCrate())) {
                return false;
            }
        }
        return true;
    }

    public void movePlayer(Asset.Direction direction) {
        if (canMove(player, direction)) {
            Move move = new Move(this, direction);
            moveStack.push(move);
            move.execute();
        }
    }

    public void undoLastMove() {
        if (!moveStack.isEmpty()) {
            Move move = moveStack.pop();
            move.undo();
        }
    }

    public boolean canMove(Asset object, Asset.Direction direction) {
        if (!object.isMoveable()) {
            return false;
        }
        int index = getIndex(object.getRow(), object.getColumn());
        index += toInt(direction);
        if (!getCell(index).isOccupied()) {
            return true;
        }
        Asset collisionObject = getCell(index).getObject();
        if (collisionObject == null) {
            return true;
        }
        if (object.isPlayer()) {
            if (collisionObject.isCrate()) {
                return canMove(collisionObject, direction);
            }
            return false;
        } else {
            return false;
        }
    }

    public int toInt(Asset.Direction direction) {
        switch (direction) {
            case LEFT:
                return -1;
            case RIGHT:
                return 1;
            case UP:
                return -columns;
            case DOWN:
                return columns;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        String res = "";
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Cell cell = cells[getIndex(y, x)];
                res += cell.toString() + " ";
            }
            res += "\n";
        }
        return res;
    }

    public boolean isCell(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean isCell(int index) {
        return index >= 0 && index < (rows * columns);
    }

    public int getCratesLeft() {
        int count = 0;
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].isOccupied() && cells[i].isCrate() && !(cells[i].hasUnderlyingObject() && cells[i].isArea())) {
                count++;
            }
        }
        return count;
    }

}
