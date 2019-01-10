package com.arman.sokoban.model.level;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LevelLoader {

    private LevelLoader() {

    }

    // TODO: throw exception and don't load level if level has invalid integers / cells
    public static Level loadLevel(int levelIndex, InputStream stream) {
        Level level = null;
        List<Cell> cells = new ArrayList<>();
        int rows = 0;
        int columns = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            int curRow = 0;
            while ((line = br.readLine()) != null) {
                int curColumn = 0;
                rows++;
                curRow = rows;
                Scanner sc = new Scanner(line);
                while (sc.hasNextInt()) {
                    curColumn++;
                    columns = Math.max(columns, curColumn);
                    Cell cell = new Cell(curRow - 1, curColumn - 1, sc.nextInt());
                    cells.add(cell);
                }
            }
            Cell[] cellsArray = cells.toArray(new Cell[cells.size()]);
            level = new Level(levelIndex, rows, columns, cellsArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return level;
        }
    }

}
