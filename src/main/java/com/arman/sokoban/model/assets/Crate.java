package com.arman.sokoban.model.assets;

import com.arman.sokoban.model.level.Cell;
import com.arman.sokoban.model.level.LevelConstants;

import java.awt.*;

public class Crate extends Asset {

    public Crate(Cell cell) {
        super(cell);
        moveable = true;
    }

    @Override
    public String toString() {
        return String.valueOf(LevelConstants.CRATE);
    }

}
