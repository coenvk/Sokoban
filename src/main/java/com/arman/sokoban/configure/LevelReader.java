package com.arman.sokoban.configure;

import com.arman.sokoban.model.level.LevelManager;
import com.arman.sokoban.util.FileUtils;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class LevelReader {

    private static LevelResult[] levelResults;

    static {
        levelResults = new LevelResult[LevelManager.NUM_LEVELS];
        readLevelResults();
    }

    private LevelReader() {

    }

    public static synchronized LevelResult getLevelResult(int levelNumber) {
        if (levelResults[levelNumber] == null) {
            return null;
        }
        if (levelResults[levelNumber].getLevelNumber() == levelNumber) {
            return levelResults[levelNumber];
        }
        for (int i = 0; i < levelResults.length; i++) {
            if (levelResults[i].getLevelNumber() == levelNumber) {
                return levelResults[i];
            }
        }
        return null;
    }

    public static synchronized void readLevelResults() {
        JSONParser parser = new JSONParser();
        Object obj;
        try {
            Gson gson = new Gson();
            obj = parser.parse(new InputStreamReader(FileUtils.getStream("config/levels.json"), "UTF-8"));
            JSONObject jsonRoot = (JSONObject) obj;
            Iterator it = jsonRoot.keySet().iterator();
            int count = 0;
            while (it.hasNext()) {
                count++;
                String key = (String) it.next();
                LevelResult levelResult = gson.fromJson(jsonRoot.get(key).toString(), LevelResult.class);
                levelResults[levelResult.getLevelNumber()] = levelResult;
            }
            if (count < LevelManager.NUM_LEVELS) {
                LevelWriter.addNewLevels();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void setLevelResult(LevelResult levelResult) {
        levelResults[levelResult.getLevelNumber()] = levelResult;
    }

}
