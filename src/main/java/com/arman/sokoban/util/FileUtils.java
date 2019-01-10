package com.arman.sokoban.util;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class FileUtils {

    private FileUtils() {

    }

    public static File getFile(String filePath) {
        return new FileUtils().getFile(filePath, true);
    }

    public static InputStream getStream(String filePath) {
        return new FileUtils().getStream(filePath, true);
    }

    private File getFile(String filePath, boolean b) {
        try {
            URI url = getClass().getClassLoader().getResource(filePath).toURI();
            return new File(url);
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream getStream(String filePath, boolean b) {
        return getClass().getClassLoader().getResourceAsStream(filePath);
    }

}
