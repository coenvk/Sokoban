package com.arman.sokoban.model.assets;

import com.arman.sokoban.model.level.Cell;
import com.arman.sokoban.model.level.LevelConstants;

import java.awt.*;

public class Floor extends Asset {

    public Floor(Cell cell) {
        super(cell);
    }

    @Override
    public String toString() {
        return String.valueOf(LevelConstants.FLOOR);
    }

}
