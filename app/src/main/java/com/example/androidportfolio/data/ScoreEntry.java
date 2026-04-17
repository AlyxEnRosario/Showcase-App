package com.example.androidportfolio.data;

import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

/**
 * Model class for a leaderboard score entry
 */
public class ScoreEntry {
    private String playerName;
    private int score;
    private boolean won;
    @ServerTimestamp
    private Date timestamp;
    
    // Default constructor for Firestore
    public ScoreEntry() {
    }
    
    public ScoreEntry(String playerName, int score, boolean won) {
        this.playerName = playerName;
        this.score = score;
        this.won = won;
        this.timestamp = new Date();
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public boolean isWon() {
        return won;
    }
    
    public void setWon(boolean won) {
        this.won = won;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
