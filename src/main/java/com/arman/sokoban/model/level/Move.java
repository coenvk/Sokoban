package com.arman.sokoban.model.level;

import com.arman.sokoban.model.assets.Asset;
import com.arman.sokoban.model.assets.Crate;
import com.arman.sokoban.model.assets.Player;

public class Move {

    private static final int NULL = -1;

    private Level level;
    private Asset.Direction direction;
    private boolean isCrateMove;
    private boolean executed;

    public Move(Level level, Asset.Direction direction) {
        this.level = level;
        this.direction = direction;
        this.isCrateMove = false;
        this.executed = false;
    }

    public void moveCrate(int index, Asset.Direction direction) {
        Cell cell = level.getCell(index);
        Crate crate = (Crate) cell.getObject();
        cell.release();
        crate.move(direction);
        index = level.getIndex(crate.getRow(), crate.getColumn());
        cell = level.getCell(index);
        cell.setObject(crate);
        isCrateMove = true;
    }

    public void execute() {
        Player player = level.getPlayer();
        int index = level.getIndex(player.getRow(), player.getColumn());
        Cell cell = level.getCell(index);
        cell.release();
        player.move(direction);
        index = level.getIndex(player.getRow(), player.getColumn());
        cell = level.getCell(index);
        if (cell.isOccupied() && cell.isCrate()) {
            moveCrate(index, direction);
        }
        cell.setObject(player);
        player.incrementSteps();
        executed = true;
    }

    public void undo() {
        if (!executed) {
            return;
        }
        Asset.Direction direction = this.direction.opposite();
        Player player = level.getPlayer();
        int index = level.getIndex(player.getRow(), player.getColumn());
        Cell cell = level.getCell(index);
        int crateIndex = index + level.toInt(this.direction);
        cell.release();
        player.move(direction);
        cell = level.getCell(level.getIndex(player.getRow(), player.getColumn()));
        cell.setObject(player);
        player.decrementSteps();
        if (level.isCell(crateIndex) && isCrateMove) {
            cell = level.getCell(crateIndex);
            if (cell.isCrate()) {
                Crate crate = (Crate) cell.getObject();
                cell.release();
                crate.move(direction);
                cell = level.getCell(level.getIndex(crate.getRow(), crate.getColumn()));
                cell.setObject(crate);
            }
        }
        executed = false;
    }

}
