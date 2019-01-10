package com.arman.sokoban;

import com.arman.sokoban.util.FileUtils;
import com.arman.sokoban.view.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sokoban");
        try {
            frame.setIconImage(ImageIO.read(FileUtils.getStream("desktop_icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.addFocusListener(gamePanel);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.requestFocus();
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}