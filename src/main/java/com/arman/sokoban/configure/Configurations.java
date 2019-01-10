package com.arman.sokoban.configure;

import com.arman.sokoban.util.FileUtils;

import java.io.*;
import java.util.Properties;

public class Configurations {

    private static String propFileName;

    public static boolean allowUndo;
    public static boolean enableSound;

    static {
        propFileName = "config/config.properties";
        readPropValues();
    }

    private Configurations() {

    }

    private static void readPropValues() {
        InputStream inputStream = null;
        try {
            Properties properties = new Properties();
            inputStream = FileUtils.getStream(propFileName);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException();
            }
            allowUndo = Boolean.parseBoolean(properties.getProperty(Props.ALLOW_UNDO));
            enableSound = Boolean.parseBoolean(properties.getProperty(Props.ENABLE_SOUND));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writePropValues() {
        OutputStream outputStream = null;
        try {
            Properties properties = new Properties();
            outputStream = new FileOutputStream(propFileName);
            properties.setProperty(Props.ALLOW_UNDO, String.valueOf(allowUndo));
            properties.setProperty(Props.ENABLE_SOUND, String.valueOf(enableSound));
            properties.store(outputStream, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
