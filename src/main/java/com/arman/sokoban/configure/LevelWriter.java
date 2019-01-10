package com.arman.sokoban.configure;

import com.arman.sokoban.model.level.LevelManager;
import com.arman.sokoban.util.FileUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class LevelWriter {

    private static final String JSON_FILE_PATH = "config/levels.json";

    static {
        addNewLevels();
    }

    private LevelWriter() {

    }

    public static synchronized void write(LevelResult levelResult) {
        JSONParser parser = new JSONParser();
        JSONObject jsonRoot = null;
        Object obj = null;
        BufferedWriter bw = null;
        try {
            obj = parser.parse(new FileReader(FileUtils.getFile(JSON_FILE_PATH)));
            jsonRoot = (JSONObject) obj;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser gsonParser = new JsonParser();
            String levelId = String.valueOf(levelResult.getLevelNumber());
            String jsonString = gsonParser.parse(gson.toJson(levelResult)).toString();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            jsonRoot.put(levelId, jsonObject);
            FileWriter fw = new FileWriter(FileUtils.getFile(JSON_FILE_PATH));
            bw = new BufferedWriter(fw);
            bw.write(jsonRoot.toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return;
        } finally {
            LevelReader.setLevelResult(levelResult);
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static synchronized void initializeLevelsFile() {
        PrintWriter pw = null;
        try {
            FileWriter fw = new FileWriter(FileUtils.getFile(JSON_FILE_PATH));
            pw = new PrintWriter(fw);
            pw.flush();
            pw.write("{}");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    public static synchronized void renameLevels() {
        for (int i = 0; i < LevelManager.NUM_LEVELS; i++) {
            File newFile = FileUtils.getFile("levels/" + (i + 1));
            int offset = 0;
            File levelFile = newFile;
            while (!levelFile.exists()) {
                offset++;
                levelFile = FileUtils.getFile("levels/" + (i + 1 + offset));
            }
            if (!newFile.exists()) {
                levelFile.renameTo(newFile);
            }
        }
        initializeLevelsFile();
    }

    public static synchronized void addNewLevels() {
        Object obj;
        JSONParser parser = new JSONParser();
        JSONObject jsonRoot = null;
        try {
            obj = parser.parse(new FileReader(FileUtils.getFile("config/levels.json")));
            jsonRoot = (JSONObject) obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (jsonRoot == null) {
            return;
        }
        for (int i = 0; i < LevelManager.NUM_LEVELS; i++) {
            if (!jsonRoot.keySet().contains(String.valueOf(i))) {
                write(new LevelResult(i, false, 0, 0));
            }
        }
    }

}