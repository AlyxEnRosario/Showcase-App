package com.example.androidportfolio.data;

import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Local leaderboard manager that mimics Firebase Firestore functionality.
 * Stores all scores locally in SharedPreferences as JSON.
 */
public class LocalLeaderboardManager {
    
    private static final String SCORES_KEY = "local_scores_json";
    private final SharedPreferences prefs;
    
    public LocalLeaderboardManager(SharedPreferences prefs) {
        this.prefs = prefs;
    }
    
    /**
     * Save a score to local storage
     */
    public void saveScore(String playerName, int score, boolean won) {
        try {
            List<Map<String, Object>> scores = getAllScores();
            
            Map<String, Object> newScore = new HashMap<>();
            newScore.put("playerName", playerName);
            newScore.put("score", score);
            newScore.put("timestamp", System.currentTimeMillis());
            newScore.put("won", won);
            
            scores.add(newScore);
            saveScoresToStorage(scores);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get top N scores, sorted by score descending
     */
    public List<Map<String, Object>> getTopScores(int limit) {
        try {
            List<Map<String, Object>> scores = getAllScores();
            
            // Sort by score descending
            scores.sort((s1, s2) -> {
                long score1 = ((Number) s1.get("score")).longValue();
                long score2 = ((Number) s2.get("score")).longValue();
                return Long.compare(score2, score1);
            });
            
            // Return top N
            return scores.subList(0, Math.min(limit, scores.size()));
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Get all scores from storage
     */
    private List<Map<String, Object>> getAllScores() throws JSONException {
        List<Map<String, Object>> scores = new ArrayList<>();
        String jsonString = prefs.getString(SCORES_KEY, "[]");
        
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("playerName", obj.getString("playerName"));
            scoreMap.put("score", obj.getLong("score"));
            scoreMap.put("timestamp", obj.getLong("timestamp"));
            scoreMap.put("won", obj.getBoolean("won"));
            scores.add(scoreMap);
        }
        
        return scores;
    }
    
    /**
     * Save scores back to SharedPreferences as JSON
     */
    private void saveScoresToStorage(List<Map<String, Object>> scores) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        
        for (Map<String, Object> score : scores) {
            JSONObject obj = new JSONObject();
            obj.put("playerName", score.get("playerName"));
            obj.put("score", score.get("score"));
            obj.put("timestamp", score.get("timestamp"));
            obj.put("won", score.get("won"));
            jsonArray.put(obj);
        }
        
        prefs.edit().putString(SCORES_KEY, jsonArray.toString()).apply();
    }
    
    /**
     * Clear all scores (for testing)
     */
    public void clearAllScores() {
        prefs.edit().remove(SCORES_KEY).apply();
    }
}
