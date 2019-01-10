package com.arman.sokoban.model.assets;

import com.arman.sokoban.configure.Props;
import com.arman.sokoban.util.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Spritesheet {

    private static final int NUM_DIR_PLAYERS = 3;

    private static Random random = new Random();

    private static Image[] areas;
    private static Image[] floors;
    private static Image[] crates;
    private static Image[] players;
    private static Image[] walls;

    static {
        populateAreas();
        populateFloors();
        populateCrates();
        populatePlayers();
        populateWalls();
    }

    private Spritesheet() {

    }

    private static void populateAreas() {
        int count = Props.NUM_AREAS;
        areas = new Image[count];
        try {
            for (int i = 0; i < areas.length; i++) {
                areas[i] = ImageIO.read(FileUtils.getStream("assets/areas/area" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void populateWalls() {
        int count = Props.NUM_WALLS;
        walls = new Image[count];
        try {
            for (int i = 0; i < walls.length; i++) {
                walls[i] = ImageIO.read(FileUtils.getStream("assets/walls/wall" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void populatePlayers() {
        int count = Props.NUM_PLAYERS;
        players = new Image[count];
        try {
            for (int i = 0; i < players.length; i++) {
                players[i] = ImageIO.read(FileUtils.getStream("assets/players/player" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void populateCrates() {
        int count = Props.NUM_CRATES;
        crates = new Image[count];
        try {
            for (int i = 0; i < crates.length; i++) {
                crates[i] = ImageIO.read(FileUtils.getStream("assets/crates/crate" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void populateFloors() {
        int count = Props.NUM_FLOORS;
        floors = new Image[count];
        try {
            for (int i = 0; i < floors.length; i++) {
                floors[i] = ImageIO.read(FileUtils.getStream("assets/floors/floor" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image[] getAreas() {
        return areas;
    }

    public static Image getArea(int index) {
        if (index < 0 || index >= areas.length) {
            return null;
        }
        return areas[index];
    }

    public static Image[] getFloors() {
        return floors;
    }

    public static Image getFloor(int index) {
        if (index < 0 || index >= floors.length) {
            return null;
        }
        return floors[index];
    }

    public static Image[] getWalls() {
        return walls;
    }

    public static Image getWall(int index) {
        if (index < 0 || index >= walls.length) {
            return null;
        }
        return walls[index];
    }

    public static Image[] getPlayers() {
        return players;
    }

    public static Image getPlayer(int index) {
        if (index < 0 || index >= players.length) {
            return null;
        }
        return players[index];
    }

    public static Image getPlayerLeft(int index) {
        if (index < 0 || index >= 2) {
            return null;
        }
        return players[8 + index];
    }

    public static Image getPlayerRight(int index) {
        if (index < 0 || index >= 2) {
            return null;
        }
        return players[6 + index];
    }

    public static Image getPlayerUp(int index) {
        if (index < 0 || index >= 3) {
            return null;
        }
        return players[3 + index];
    }

    public static Image getPlayerDown(int index) {
        if (index < 0 || index >= 3) {
            return null;
        }
        return players[index];
    }

    public static Image[] getCrates() {
        return crates;
    }

    public static Image getCrate(int index) {
        if (index < 0 || index >= crates.length) {
            return null;
        }
        return crates[index];
    }

    public static Image getRandomWall() {
        return walls[random.nextInt(walls.length)];
    }

    public static Image getRandomCrate() {
        return crates[random.nextInt(crates.length)];
    }

    public static Image getRandomFloor() {
        return floors[random.nextInt(floors.length)];
    }

    public static Image getRandomArea() {
        return areas[random.nextInt(areas.length)];
    }

}
